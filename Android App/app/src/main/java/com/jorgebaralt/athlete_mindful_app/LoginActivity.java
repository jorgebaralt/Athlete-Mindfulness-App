package com.jorgebaralt.athlete_mindful_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.API.ApiInterface;
import com.jorgebaralt.athlete_mindful_app.API.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {
    TextView registerHere;
    EditText Email;
    EditText Password;
    Button btnLogin;
    String token;
    Player currentPlayer;


    String email;
    String password;
    AlertDialog.Builder builder;
    private static String BASE_URL = "http://project-env-4.us-east-1.elasticbeanstalk.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///This removes notifications, time, etc. ON TOP
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        //declaration
        btnLogin = (Button) findViewById(R.id.btnLogin);
        registerHere = (TextView) findViewById(R.id.textRegister);
        Email = (EditText) findViewById(R.id.txtEmail);
        Password = (EditText) findViewById(R.id.txtPassword);

        builder  = new AlertDialog.Builder(this);


        //ACTION FOR LOGIN BUTTON
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //get email and user
                email = Email.getText().toString();
                password = Password.getText().toString();

                if(email.length() < 1 || password.length()<1){
                    builder.setTitle("Something Went Wrong...");
                    builder.setMessage("Enter Valid Email and Password...");
                    displayAlert("input_error");
                    //TODO: ERASE goToNavigation after testing is done.
                    goToNavigation(v); //FOR TESTING ISSUES!                   ERASE AFTER TESTING IS DONE!
                }else{
                    //User to authenticate
                    final Login userLogin = new Login(email,password);


                    //Start Retrofit
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create());

                    Retrofit retrofit = builder.build();

                    ApiInterface api = retrofit.create(ApiInterface.class);
                    Call<Player> call = api.login(userLogin);
                    call.enqueue(new Callback<Player>() {
                        @Override
                        public void onResponse(Call<Player> call, retrofit2.Response<Player> response) {

                            Log.d(TAG, "onResponse: Response from server at login" + response.body());

                            if(response.isSuccessful()){
                                Toast.makeText(LoginActivity.this, response.body().getEmail() + ", token = " + response.body().getToken(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: RESPONSE WORKED, LOGIN WORKED!!!" + response.body().getToken());
                                token = response.body().getToken();
                                currentPlayer = response.body();

                                // *** Go to Navigation Drawer Activity ***
                                Intent intent = new Intent(LoginActivity.this,NavigationDrawer.class);
                                intent.putExtra("currentPlayer",currentPlayer);
                                startActivity(intent);


                            }else{
                                Toast.makeText(LoginActivity.this, "Make sure Email and Password Match", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onResponse: Error login not successful , response  =  " + response.errorBody() );
                            }
                        }

                        @Override
                        public void onFailure(Call<Player> call, Throwable t) {

                            Toast.makeText(LoginActivity.this, "ERROR LOGGING TO SERVER", Toast.LENGTH_SHORT).show();

                        }
                    });


                }


            }
        });

        //ACTION FOR REGISTER BUTTON
        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister(v);

            }
        });


    }

    public void displayAlert(final String error){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(error.equals("input_error")) {
                    Password.setText("");
                    Email.setText("");

                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void goToRegister(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void goToNavigation(View view){
        Intent intent = new Intent(this,NavigationDrawer.class);
        startActivity(intent);
    }
}
