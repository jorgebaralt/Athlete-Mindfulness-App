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

public class EditAgeActivity extends AppCompatActivity {
    private String TAG;
    private final static String BASE_URL = "http://postgresql-env.8ts8eznn5d.us-east-1.elasticbeanstalk.com";

    private Player currentPlayer;
    private Button okButton;
    private Button cancelButton;
    private EditText newAgeEditText;
    private String newAge;
    private int ageRange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_age);

        //get current player
        currentPlayer = (Player)EditAgeActivity.this.getIntent().getSerializableExtra("currentPlayer");

        //set button and text
        okButton = (Button) findViewById(R.id.editAge_ok);
        cancelButton = (Button) findViewById(R.id.editAge_cancel);
        newAgeEditText = (EditText) findViewById(R.id.editAge_age);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAge = newAgeEditText.getText().toString();
                if(!newAge.equals("") && !newAge.equals(currentPlayer.getAge())){


                    int ageNum = Integer.parseInt(newAge);
                    if(ageNum<= 13){
                        ageRange = 1;
                    }else if(ageNum>=14 && ageNum<= 15){
                        ageRange = 2;
                    }else if(ageNum>=16 && ageNum<= 19){
                        ageRange = 3;
                    }else{
                        ageRange = 4;
                    }
                    //TODO:SEND ALSO AGE RANGE

                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();

                    ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                    Call<ResponseBody> call = apiInterface.updatePlayerAge(Integer.toString(currentPlayer.getId()),newAge);
                    
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(EditAgeActivity.this, "Age Updated", Toast.LENGTH_SHORT).show();
                                //go back to settings with profile updated
                                currentPlayer.setAge(Integer.parseInt(newAge));
                                Intent settingsIntent = new Intent(EditAgeActivity.this,SettingsActivity.class);
                                settingsIntent.putExtra("currentPlayer",currentPlayer);
                                finish();
                                startActivity(settingsIntent);

                            }else{
                                Toast.makeText(EditAgeActivity.this, "Error...", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    

                }else{
                    Toast.makeText(EditAgeActivity.this, "Please Enter Your Age", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
