package com.jorgebaralt.athlete_mindful_app.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.R;

public class EditEmailActivity extends AppCompatActivity {

    private Button okButton;
    private Button cancelButton;
    private EditText newEmail;
    private Player currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);
        //Get player
        currentPlayer = (Player)EditEmailActivity.this.getIntent().getSerializableExtra("currentPlayer");

        //set buttons and text
        okButton = (Button) findViewById(R.id.editEmail_ok);
        cancelButton = (Button) findViewById(R.id.editEmail_cancel);
        newEmail = (EditText) findViewById(R.id.editEmail_editText);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PUT request to change email
                if(!newEmail.getText().toString().equals("")){
                    //TODO: Retrofit PUT REQUEST

                }else{
                    Toast.makeText(EditEmailActivity.this, "Please Insert a New Email", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to Settings activity
               finish();
            }
        });

    }


}
