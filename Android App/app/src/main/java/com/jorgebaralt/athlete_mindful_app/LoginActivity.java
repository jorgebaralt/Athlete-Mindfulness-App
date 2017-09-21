package com.jorgebaralt.athlete_mindful_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView registerHere;
    EditText Email;
    EditText Password;
    Button btnLogin;

    String email;
    String password;
    AlertDialog.Builder builder;

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
            public void onClick(View v) {
                email = Email.getText().toString();
                password = Password.getText().toString();

                if(email.length() < 1 || password.length()<1){
                    builder.setTitle("Something Went Wrong...");
                    builder.setMessage("Please fill in all the fields");
                    displayAlert("input_error");
                }


                goToNavigation(v);

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
