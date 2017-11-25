package com.jorgebaralt.athlete_mindful_app.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.API.ApiInterface;
import com.jorgebaralt.athlete_mindful_app.NavigationDrawer;
import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.R;
import com.jorgebaralt.athlete_mindful_app.Util.Util;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChannelListener;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Member;
import com.twilio.chat.Message;
import com.twilio.chat.StatusListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class ChatActivity extends AppCompatActivity {

    private Player currentPlayer;

    String CHANNEL_NAME = "";

    private RecyclerView messagesRecyclerView;
    private MessagesAdapter messagesAdapter;
    private ArrayList<Message> messages = new ArrayList<>();

    private Button sendMessageBtn;
    private EditText messageEditText;

    private ChatClient chatClient = null;
    private Channel generalChannel;
    private String accessToken = "";
    private String name;

    Token token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);




        //Get player that is currently logged in
        currentPlayer = (Player) ChatActivity.this.getIntent().getSerializableExtra("currentPlayer");
        if(currentPlayer != null) {
            Log.d(TAG, "ChatActivity, Player: " + currentPlayer.getName() + " Entered the Chat");
            CHANNEL_NAME = Integer.toString(currentPlayer.getId());

        }


        Log.d(TAG, "onCreate: Channel name is  = "  + CHANNEL_NAME);

        messageEditText = (EditText) findViewById(R.id.txtMessage);
        sendMessageBtn = (Button) findViewById(R.id.btnSendMessage);

        messagesRecyclerView = (RecyclerView) findViewById(R.id.recyclerMessages);
        //recycler view listens for keyboard appear and moves
        messagesRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                messagesRecyclerView.scrollToPosition(messages.size()+1);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //since is chat, we need to show latest message at bottom and oldest on top
        //layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);


        messagesRecyclerView.setLayoutManager(layoutManager);


        messagesAdapter = new MessagesAdapter();
        messagesRecyclerView.setAdapter(messagesAdapter);

        getAccessTokenFromServer();


        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(generalChannel != null){
                    Util.hideSoftKeyboard(ChatActivity.this);
                    String messageBody = messageEditText.getText().toString();
                    Message.Options message = Message.options().withBody(messageBody);
                    Log.d(TAG, " Message Created ");

                    generalChannel.getMessages().sendMessage(message, new CallbackListener<Message>() {
                        @Override
                        public void onSuccess(Message message) {
                            Log.d(TAG, "onSuccess: Message Sent");
                            ChatActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    messageEditText.setText("");

                                }
                            });

                        }

                        @Override
                        public void onError(ErrorInfo errorInfo) {
                            super.onError(errorInfo);
                        }
                    });

                }else{
                    Toast.makeText(ChatActivity.this, "Error Loading Chat Channel", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onClick: Error loading Chat Channel" );
                }
            }
        });









    }

    private void getAccessTokenFromServer(){
        String deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String tokenURL = ApiInterface.BASE_URL + "?device=" + deviceID;
        name = currentPlayer.getName();
        final ChatClient.Properties props = new ChatClient.Properties.Builder().createProperties();
        /*
        In particular, searching your log for 4xx and 5xx errors such as 401 can be very helpful in diagnosing issues.
        Generally speaking, a 401 error will indicate permissions issue - either for the particular object you are
         interacting with or your entire session if the 401 is related to your access token.
         */
        ChatClient.setLogLevel(android.util.Log.DEBUG);

        //request the AcessToken to the API
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<Token> call = api.getToken(name);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()) {
                    token = response.body();
                    accessToken = token.getToken();
                    Log.d(TAG, "onResponse TokenFromServer: accessToken = " + accessToken);

                    ChatClient.create(ChatActivity.this, accessToken, props, new CallbackListener<ChatClient>() {
                        @Override
                        public void onSuccess(final ChatClient client) {
                            Log.d(TAG, "onSuccess: Access Token Worked, Chat Client created");
                            chatClient = client;
                            loadChannels();
                        }

                        @Override
                        public void onError(ErrorInfo errorInfo) {
                            Log.e(TAG, "onError: Error Creating ChatClient = " + errorInfo.getMessage());
                            super.onError(errorInfo);
                        }
                    });



                }
                else{
                    Log.e(TAG, "onResponse: Error getting response");
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Fail Getting Token for Chat", Toast.LENGTH_SHORT).show();

            }
        });





    }
    private void loadChannels(){
        Log.d(TAG, "loadChannels: Loading channel");

       chatClient.getChannels().getChannel(CHANNEL_NAME, new CallbackListener<Channel>() {
           @Override
           public void onSuccess(Channel channel) {
               if(channel != null){
                   Log.d(TAG, "onSuccess: Channel loaded");
                   joinChannel(channel);
               }else{
                   //channel does not exist, we can create it here, but since our api always
                   //create a channel at registration, it means there is a problem in the back end.
                   Log.e(TAG, "Channel Name = " + CHANNEL_NAME + " Does not exist **CHECK BACK END** ");
               }
           }

           @Override
           public void onError(ErrorInfo errorInfo) {
               Log.e(TAG, "onError: Error getting channel" + errorInfo.getMessage() );
               super.onError(errorInfo);
           }
       });
    }

    private void joinChannel(final Channel channel){
        Log.d(TAG, "joiningChannel: " + channel.getUniqueName());
        channel.join(new StatusListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: Joined Channel Successfully");
                generalChannel = channel;

                //Load Previous Messages
                generalChannel.getMessages().getLastMessages(50, new CallbackListener<List<Message>>() {
                    @Override
                    public void onSuccess(List<Message> oldMessages) {
                        for( int i = 0; i < oldMessages.size(); i++){
                            messages.add(oldMessages.get(i));
                        }
                        messagesAdapter.notifyDataSetChanged();
                        messagesRecyclerView.scrollToPosition(messages.size()-1);
                    }
                });

                //this is called everytime we send a message, so here is where we add them to the messages arraylist
                generalChannel.addListener(new ChannelListener() {
                    @Override
                    public void onMessageAdded(final Message message) {
                        Log.d(TAG, "onMessageAdded: message added =  " + message);
                        ChatActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messages.add(message);
                                messagesAdapter.notifyDataSetChanged();
                                messagesRecyclerView.scrollToPosition(messages.size()-1);
                            }
                        });

                    }

                    @Override
                    public void onMessageUpdated(Message message, Message.UpdateReason updateReason) {
                        Log.d(TAG, "onMessageUpdated: " + message);
                    }

                    @Override
                    public void onMessageDeleted(Message message) {
                        Log.d(TAG, "onMessageDeleted: " + message);
                    }

                    @Override
                    public void onMemberAdded(Member member) {
                        Log.d(TAG, "onMemberAdded: "+ member.getIdentity());
                    }

                    @Override
                    public void onMemberUpdated(Member member, Member.UpdateReason updateReason) {
                        Log.d(TAG, "onMemberUpdated: "+ member.getIdentity());
                    }

                    @Override
                    public void onMemberDeleted(Member member) {
                        Log.d(TAG, "onMemberDeleted: "+ member.getIdentity());
                    }

                    @Override
                    public void onTypingStarted(Member member) {
                        Log.d(TAG, "onTypingStarted: "+ member.getIdentity());
                    }

                    @Override
                    public void onTypingEnded(Member member) {
                        Log.d(TAG, "onTypingEnded: " + member.getIdentity());
                    }

                    @Override
                    public void onSynchronizationChanged(Channel channel) {
                        Log.d(TAG, "onSynchronizationChanged: ");
                    }
                });
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                Log.e(TAG, "onError: Error joining Channel: "+ errorInfo.getMessage() );
                super.onError(errorInfo);
            }
        });

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = new Intent(this,NavigationDrawer.class);
        intent.putExtra("currentPlayer", currentPlayer);
        setResult(RESULT_OK,intent);
        return intent;
    }




    class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView messageTextView;
            public ViewHolder(TextView textView) {
                super(textView);
                messageTextView = textView;

            }
        }

        @Override
        public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView messageTextView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.message_text_view,parent,false);
            return new ViewHolder(messageTextView);
        }

        @Override
        public void onBindViewHolder(MessagesAdapter.ViewHolder holder, int position) {
        Message message = messages.get(position);
        String messageText = String.format(" %s: \t \t %s",message.getAuthor(),message.getMessageBody());
        holder.messageTextView.setText(messageText);

        }

        @Override
        public int getItemCount() {
            return messages.size();
        }




    }



}
