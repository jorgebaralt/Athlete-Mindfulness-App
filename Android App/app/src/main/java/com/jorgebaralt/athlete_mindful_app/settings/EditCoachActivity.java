package com.jorgebaralt.athlete_mindful_app.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.API.ApiInterface;
import com.jorgebaralt.athlete_mindful_app.Coach;
import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.R;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditCoachActivity extends AppCompatActivity {
    private String TAG;
    private final static String BASE_URL = "http://postgresql-env.8ts8eznn5d.us-east-1.elasticbeanstalk.com";
    private ArrayList<Coach>  coachList = new ArrayList<>();

    private Player currentPlayer;
    private Button okButton;
    private Button cancelButton;
    private Spinner newCoachSpinner;
    private ArrayAdapter<String> spinnerAdapter;

    private String coachName;
    private int coachId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_coach);

        //set buttons and spinners
        okButton = (Button) findViewById(R.id.editCoach_ok);
        cancelButton = (Button) findViewById(R.id.editCoach_cancel);
        newCoachSpinner = (Spinner) findViewById(R.id.editCoach_spinner);



        //get Player
        currentPlayer = (Player) EditCoachActivity.this.getIntent().getSerializableExtra("currentPlayer");


        //get coaches
        getCoaches();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the id of the selected coach
                coachName = newCoachSpinner.getSelectedItem().toString();
                for(int i = 0; i < coachList.size(); i ++ ){
                    if(coachList.get(i).getName().equals(coachName)){
                        coachId = coachList.get(i).getId();
                        break;
                    }
                }

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();

                ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                Call<ResponseBody> call = apiInterface.updatePlayerCoach(Integer.toString(currentPlayer.getId()) ,Integer.toString(coachId),coachName);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(EditCoachActivity.this, "Your Coach Has Been Updated...", Toast.LENGTH_SHORT).show();
                            //go back to settings with updated players info.
                            Intent settingsIntent = new Intent(EditCoachActivity.this,SettingsActivity.class);
                            currentPlayer.setCoachName(coachName);
                            settingsIntent.putExtra("currentPlayer",currentPlayer);
                            finish();
                            startActivity(settingsIntent);
                        }else{
                            Toast.makeText(EditCoachActivity.this, "Error...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to settings activity
                finish();
            }
        });

    }

    public void getCoaches(){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ArrayList<Coach>> call = apiInterface.getCoaches();
        call.enqueue(new Callback<ArrayList<Coach>>() {
            @Override
            public void onResponse(Call<ArrayList<Coach>> call, retrofit2.Response<ArrayList<Coach>> response) {
                if(response.isSuccessful()) {
                    coachList = response.body();

                    //create spinner adapter that display names of the coaches to select.
                    String[] coaches = new String[coachList.size()];
                    for (int i = 0; i < coachList.size(); i++) {
                        coaches[i] = coachList.get(i).getName();
                    }
                    spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, coaches);
                    //set the spinners adapter to the previously created one.
                    newCoachSpinner.setAdapter(spinnerAdapter);
                }
                else{
                    Toast.makeText(EditCoachActivity.this, "Error Getting coaches", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Coach>> call, Throwable t) {
                Toast.makeText(EditCoachActivity.this, "Error getting coaches", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Error loading coaches",t );

            }
        });

    }

}

