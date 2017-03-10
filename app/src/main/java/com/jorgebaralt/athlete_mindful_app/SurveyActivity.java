package com.jorgebaralt.athlete_mindful_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        WelcomeMessage();

    }
    void WelcomeMessage(){
        Toast toast = Toast.makeText(getApplicationContext(),"Welcome, Fill the Survey",Toast.LENGTH_LONG);
    }

}
