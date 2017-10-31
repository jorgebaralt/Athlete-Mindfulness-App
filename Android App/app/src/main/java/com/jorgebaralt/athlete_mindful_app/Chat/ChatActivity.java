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

import com.jorgebaralt.athlete_mindful_app.NavigationDrawer;
import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.R;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChannelListener;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Member;
import com.twilio.chat.Message;
import com.twilio.chat.StatusListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ChatActivity extends AppCompatActivity {

    private Player currentPlayer;

    //TODO set the server token url and the channel name
    final static String SERVER_TOKEN_URL = "";
    String CHANNEL_NAME = "";

    private RecyclerView messagesRecyclerView;
    private MessagesAdapter messagesAdapter;
    private ArrayList<Message> messages = new ArrayList<>();

    private Button sendMessageBtn;
    private EditText messageEditText;

    private ChatClient chatClient;
    private Channel generalChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Get player that is currently logged in
        currentPlayer = (Player) ChatActivity.this.getIntent().getSerializableExtra("currentPlayer");
        if(currentPlayer != null) {
            Log.d(TAG, "ChatActivity, Player: " + currentPlayer.getName() + "Entered the Chat");

        }

        messageEditText = (EditText) findViewById(R.id.txtMessage);
        sendMessageBtn = (Button) findViewById(R.id.btnSendMessage);

        messagesRecyclerView = (RecyclerView) findViewById(R.id.recyclerMessages);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //since is chat, we need to show latest message at bottom and oldest on top
        layoutManager.setStackFromEnd(true);
        messagesRecyclerView.setLayoutManager(layoutManager);

        messagesAdapter = new MessagesAdapter();
        messagesRecyclerView.setAdapter(messagesAdapter);

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(generalChannel != null){
                    String messageBody = messageEditText.getText().toString();



                }
            }
        });




        getAccessTokenFromServer();





    }

    private void getAccessTokenFromServer(){
        String deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String tokenURL = SERVER_TOKEN_URL + "?device=" + deviceID;

        ChatClient.Properties props = new ChatClient.Properties.Builder().createProperties();
        /*
        In particular, searching your log for 4xx and 5xx errors such as 401 can be very helpful in diagnosing issues.
        Generally speaking, a 401 error will indicate permissions issue - either for the particular object you are
         interacting with or your entire session if the 401 is related to your access token.
         */
        ChatClient.setLogLevel(android.util.Log.DEBUG);
        //TODO : GET ACCESS TOKEN FROM SERVER
        String accessToken = "";
        ChatClient.create(ChatActivity.this, accessToken , props, new CallbackListener<ChatClient>() {
            @Override
            public void onSuccess(final ChatClient client) {
                chatClient = client;
                loadChannels();
                Log.d(TAG, "onSuccess: Success creating Twilio Chat Client");
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                Log.e(TAG, "onError: Error Creating Channel" + errorInfo.getMessage() );
                super.onError(errorInfo);
            }
        });


    }
    private void loadChannels(){
       chatClient.getChannels().getChannel(CHANNEL_NAME, new CallbackListener<Channel>() {
           @Override
           public void onSuccess(Channel channel) {
               if(channel != null){
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
                generalChannel.addListener(new ChannelListener() {
                    @Override
                    public void onMessageAdded(final Message message) {
                        Log.d(TAG, "onMessageAdded: message added =  " + message);
                        ChatActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messages.add(message);
                                messagesAdapter.notifyDataSetChanged();
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
        public MessagesAdapter(){

        }
        @Override
        public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView messageTextView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.message_text_view,parent,false);
            return new ViewHolder(messageTextView);
        }

        @Override
        public void onBindViewHolder(MessagesAdapter.ViewHolder holder, int position) {
        Message message = messages.get(position);
        String messageText = String.format("%s: %s", message.getAuthor(),message.getMessageBody());
        holder.messageTextView.setText(messageText);

        }

        @Override
        public int getItemCount() {
            return messages.size();
        }


    }

}
