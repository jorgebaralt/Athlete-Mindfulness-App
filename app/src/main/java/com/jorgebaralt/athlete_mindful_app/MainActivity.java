package com.jorgebaralt.athlete_mindful_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WelcomeMessage();
        Toast toast = Toast.makeText(getApplicationContext(),"test", Toast.LENGTH_SHORT);

    }
    void WelcomeMessage(){
        Toast toast = Toast.makeText(getApplicationContext(),"Welcome to your Profile",Toast.LENGTH_LONG);
    }

}
