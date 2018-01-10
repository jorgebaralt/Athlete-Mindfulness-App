package com.jorgebaralt.athlete_mindful_app.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.API.ApiInterface;
import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditEmailActivity extends AppCompatActivity {

    private Button okButton;
    private Button cancelButton;
    private EditText editTextEmail;
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
        editTextEmail = (EditText) findViewById(R.id.editEmail_editText);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String newEmail = editTextEmail.getText().toString();

                //PUT request to change email
                if(!newEmail.equals("") && !newEmail.equals(currentPlayer.getEmail())){
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(ApiInterface.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();

                    ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                    Call<ResponseBody> call = apiInterface.updatePlayerEmail(Integer.toString(currentPlayer.getId()),newEmail);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(EditEmailActivity.this, "Your Email Has Been Updated...", Toast.LENGTH_SHORT).show();
                            //going back to settings, with info updated.
                            Intent settingsIntent = new Intent(EditEmailActivity.this,SettingsActivity.class);
                            currentPlayer.setEmail(newEmail);
                            settingsIntent.putExtra("currentPlayer",currentPlayer);
                            finish();
                            startActivity(settingsIntent);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

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
