package com.jorgebaralt.athlete_mindful_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView registerHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ///This removes notifications, time, etc. ON TOP
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
               // WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_login);
        registerHere = (TextView) findViewById(R.id.textRegister);
        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister(v);
            }
        });


    }

    public void goToRegister(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    public void goToSurvey(View view){
        Intent intent = new Intent(this,SurveyActivity.class);
        startActivity(intent);
    }
    public void goToNavigation(View view){
        Intent intent = new Intent(this,NavigationDrawer.class);
        startActivity(intent);
    }
}
