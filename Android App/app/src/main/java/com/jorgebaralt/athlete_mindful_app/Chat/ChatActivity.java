package com.jorgebaralt.athlete_mindful_app.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jorgebaralt.athlete_mindful_app.NavigationDrawer;
import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.R;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChatClient;

import static android.content.ContentValues.TAG;

public class ChatActivity extends AppCompatActivity {

    private Player currentPlayer;

    //TODO set the server token url and the channel name
    final static String SERVER_TOKEN_URL = "";
    String DEFAULT_CHANNEL_NAME = "";

    private RecyclerView messagesRecyclerView;
    //private MessageAdapter messagesAdapter;
    //private ArrayList<Message> message = new ArrayList<>();

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


        getAccessTokenFromServer();





    }

    private void getAccessTokenFromServer(){
        String deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String tokenURL = SERVER_TOKEN_URL + "?devide=" + deviceID;

        ChatClient.Properties props = new ChatClient.Properties.Builder().createProperties();
        ChatClient.create(ChatActivity.this, accessToken, props, new CallbackListener<ChatClient>() {
            @Override
            public void onSuccess(ChatClient chatClient) {


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

}
