package com.jorgebaralt.athlete_mindful_app.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.R;

public class EditCoachActivity extends AppCompatActivity {

    private Player currentPlayer;
    private Button okButton;
    private Button cancelButton;
    private Spinner newCoach;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_coach);

        //set buttons and spinners
        okButton = (Button) findViewById(R.id.editCoach_ok);
        cancelButton = (Button) findViewById(R.id.editCoach_cancel);
        newCoach = (Spinner) findViewById(R.id.editCoach_spinner);

        //get Player
        currentPlayer = (Player)EditCoachActivity.this.getIntent().getSerializableExtra("currentPlayer");

        //populate the spinner

    }

}
