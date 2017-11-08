package com.jorgebaralt.athlete_mindful_app.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.NavigationDrawer;
import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.R;

public class SettingsActivity extends AppCompatActivity {

    private Player currentPlayer;
    private TextView name;
    private TextView email;
    private TextView coach;
    private TextView age;
    private TextView editEmail;
    private TextView editCoach;
    private TextView editAge;
    private String TAG;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //get textviews from layout
        name = (TextView) findViewById(R.id.settings_name);
        email = (TextView) findViewById(R.id.settings_email);
        coach = (TextView) findViewById(R.id.settings_coach);
        age = (TextView) findViewById(R.id.settings_age);
        editEmail = (TextView) findViewById(R.id.settings_edit_email);
        editCoach = (TextView) findViewById(R.id.settings_edit_coach);
        editAge = (TextView) findViewById(R.id.settings_edit_age);


        currentPlayer = (Player) SettingsActivity.this.getIntent().getSerializableExtra("currentPlayer");
        if(currentPlayer != null){
            Log.d(TAG, "onCreate: " + currentPlayer.getCoachName());

            //display the current player information
            name.setText(currentPlayer.getName());
            email.setText(currentPlayer.getEmail());
            coach.setText(currentPlayer.getCoachName());
            age.setText("Age: " + Integer.toString(currentPlayer.getAge()));

        }else{
            Toast.makeText(this, "Player did not load correctly", Toast.LENGTH_SHORT).show();
        }

        //Edit buttons
        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Edit Email Pressed");
                Intent editEmailIntent = new Intent(SettingsActivity.this,EditEmailActivity.class);
                editEmailIntent.putExtra("currentPlayer",currentPlayer);
                startActivity(editEmailIntent);
            }


        });

        editCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Edit Coach Pressed");
                Intent editCoachIntent = new Intent(SettingsActivity.this,EditCoachActivity.class);
                editCoachIntent.putExtra("currentPlayer",currentPlayer);
                startActivity(editCoachIntent);

            }
        });

        editAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Edit Age Pressed");

            }
        });


    }
    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = new Intent(this,NavigationDrawer.class);
        intent.putExtra("currentPlayer", currentPlayer);
        return intent;
    }


}
