package com.jorgebaralt.athlete_mindful_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.API.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private ArrayList<Coach>  coachList = new ArrayList<>();

    static final String coaches_url = "http://project-env-4.us-east-1.elasticbeanstalk.com/coaches";

    private final static String BASE_URL = "http://postgresql-env.8ts8eznn5d.us-east-1.elasticbeanstalk.com";

    AlertDialog.Builder builder;

    private String TAG;
    private ArrayAdapter<String> spinnerAdapter;

    //Layout views
    private Spinner spinner;
    private EditText textName;
    private EditText textLastname;
    private EditText textEmail;
    private EditText textPassword1;
    private EditText textPassword2;
    private EditText textPhone;
    private EditText textAge;
    private RadioGroup radioGender;
    private RadioButton radioSelectedGender;
    private Button submit;
    private int BASE_POINTS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///This removes notifications, time, etc. ON TOP of screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        //link views to layout views
        spinner = (Spinner)findViewById(R.id.coach_spinner);
        textName = (EditText)findViewById(R.id.txtName);
        textLastname = (EditText)findViewById(R.id.txtLastname);
        textEmail = (EditText) findViewById(R.id.txtEmail);
        textPassword1 = (EditText) findViewById(R.id.txtPassword1);
        textPassword2 = (EditText) findViewById(R.id.txtPassword2);
        textPhone = (EditText) findViewById(R.id.txtPhone);
        textAge = (EditText) findViewById(R.id.txtAge);
        radioGender = (RadioGroup) findViewById(R.id.radioGender);
        submit = (Button) findViewById(R.id.btnSubmit);


        //Get the coach list from database
        getCoaches();
        //getCoaches();

        //Submit Button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check that EditText are not empty
                if(textName.getText().length() > 0 && textLastname.getText().length()>0 && textEmail.getText().length() > 0
                        && textPhone.getText().length() > 0 && textPassword1.getText().length() > 0 &&
                        textPassword2.getText().length() > 0){
                    //check that passwords match
                    if(textPassword1.getText().toString().equals(textPassword2.getText().toString())) {
                        Log.d(TAG, "onClick: PASSWORD MATCH, CONTINUE");
                        //check password length
                        if(textPassword1.getText().toString().length() >= 6){
                            Log.d(TAG, "onClick: Password length is fine ,continue");
                            //inserting users into database
                            insertPlayer();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Password is too short (Minimum is 6 characters", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Make sure passwords match", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onClick: Password is not matching" );
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onClick: Fill all the fields");
                }

            }
        });

    }


    public  void getCoaches(){

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
                   spinner.setAdapter(spinnerAdapter);
               }
               else{
                   Toast.makeText(RegisterActivity.this, "Error Getting coaches", Toast.LENGTH_SHORT).show();
               }


            }

            @Override
            public void onFailure(Call<ArrayList<Coach>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error getting coaches", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Error loading coaches",t );

            }
        });


    }

    //JSON REQUEST TO GET EXISTING COACHES


    //METHOD to insert user into database
    private void insertPlayer() {
        Log.d(TAG, "insertPlayer: Inserting user into database");

        final String firstName = textName.getText().toString();
        final String lastName = textLastname.getText().toString();
        final String email = textEmail.getText().toString();
        final String password = textPassword1.getText().toString();
        final String passwordConfirmation = textPassword2.getText().toString();
        final String phone = textPhone.getText().toString();
        final String age = textAge.getText().toString();
        int coachId = -1;

        //get coach id
        String selectedCoach = spinner.getSelectedItem().toString();
        for(int i = 0; i < coachList.size(); i ++ ){
            if(coachList.get(i).getName().equals(selectedCoach)){
                coachId = coachList.get(i).getId();
                Log.d(TAG, "insertPlayer: FOUND COACH ID : " + coachId);
                break;
            }
        }


        //get ageRange
        int ageRange;
        int ageNum = Integer.parseInt(age);
        if(ageNum<= 13){
            ageRange = 1;
        }else if(ageNum>=14 && ageNum<= 15){
            ageRange = 2;
        }else if(ageNum>=16 && ageNum<= 19){
            ageRange = 3;
        }else{
            ageRange = 4;
        }


        //get gender from radio
        int gender;
        //get the id of the selected radio button.
        int selectedId = radioGender.getCheckedRadioButtonId();
        radioSelectedGender = (RadioButton) findViewById(selectedId);
        if(radioSelectedGender.getText().toString().equals("Male")){
            gender = 1;
        }
        else {
            gender = 2;
        }

        //create new Player to send to POST
        Player newPlayer = new Player(firstName,lastName,BASE_POINTS);
        newPlayer.setAge(ageNum);
        newPlayer.setAgeRange(ageRange);
        newPlayer.setCoachId(coachId);
        newPlayer.setEmail(email);
        newPlayer.setPassword(password);
        newPlayer.setPasswordConfirmation(passwordConfirmation);
        newPlayer.setPhone(phone);
        newPlayer.setGender(gender);

        //Create the alert dialog.
        builder = new AlertDialog.Builder(RegisterActivity.this);

        //Retrofit request
        Retrofit.Builder retroBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = retroBuilder.build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Player> call = apiInterface.createPlayer(newPlayer);
        call.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, retrofit2.Response<Player> response) {
                if(response.isSuccessful()) {
                    builder.setTitle("Server Response");
                    Log.d(TAG, "onResponse: User Added : " + response);
                    builder.setMessage("Response : User has been created");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            goToLogin();
                        }
                    });
                    //create the dialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Error creating Player", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: Error creating player = " + response );
                }

            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error Creating user", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ",t );
            }
        });
    }

    private void goToLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void goToNavigation(){
        Intent intent = new Intent(this,NavigationDrawer.class);
        startActivity(intent);
    }

}
