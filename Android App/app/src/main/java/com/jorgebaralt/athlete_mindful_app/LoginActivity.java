package com.jorgebaralt.athlete_mindful_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView registerHere;
    EditText email;
    EditText password;
    Button btnLogin;

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
        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPassword);


        //ACTION FOR LOGIN BUTTON
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO confirm login
                //TODO : start authentication
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

    private void goToRegister(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void goToNavigation(View view){
        Intent intent = new Intent(this,NavigationDrawer.class);
        startActivity(intent);
    }
}
