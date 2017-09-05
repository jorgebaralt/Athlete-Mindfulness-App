package com.jorgebaralt.athlete_mindful_app;

import android.content.Intent;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    //Variables
    static final String json_coaches_url = "http://project-env-4.us-east-1.elasticbeanstalk.com/coaches";
    String[] coaches;
    private String TAG;
    ArrayAdapter<String> spinnerAdapter;
    //Layout views
    Spinner spinner;
    EditText textName;
    EditText textLastname;
    EditText textEmail;
    EditText textPassword1;
    EditText textPassword2;
    EditText textPhone;
    RadioGroup radioGender;
    RadioGroup radioAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///This removes notifications, time, etc. ON TOP
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        //link views to layout views
        spinner = (Spinner)findViewById(R.id.coach_spinner);
        textName = (EditText)findViewById(R.id.textName);
        textLastname = (EditText)findViewById(R.id.textLastname);
        textEmail = (EditText) findViewById(R.id.textEmail);
        textPassword1 = (EditText) findViewById(R.id.textPassword1);
        textPassword2 = (EditText) findViewById(R.id.textPassword2);
        textPhone = (EditText) findViewById(R.id.textPhone);
        radioGender = (RadioGroup) findViewById(R.id.radioGender);
        radioAge = (RadioGroup) findViewById(R.id.radioAge);


        //Get the coach list from database
        getCoaches();


        //submit button
        Button submit = (Button) findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNavigation(v);
            }
        });

    }

    //JSON REQUEST TO GET EXISTING COACHES
    public void getCoaches(){
        final ArrayList<String> coachList = new ArrayList<>();
        coachList.add("Select Coach");

        Log.d(TAG, "getCoaches: STARTING JSON ARRAY REQUEST FOR COACHES");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_coaches_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for( int i = 0 ; i < response.length(); i++){
                    try {
                        JSONObject currentCoach = response.getJSONObject(i);
                        String name = currentCoach.getString("first_name");
                        String lastname = currentCoach.getString("last_name");
                        String coachName = name + " " + lastname;
                        coachList.add(coachName);
                        Log.d(TAG, "onResponse: LIST" + coachList);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                coaches = coachList.toArray(new String[coachList.size()]);

                spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, coaches);
                //set the spinners adapter to the previously created one.
                spinner.setAdapter(spinnerAdapter);
                spinner.setPrompt("Select Coach");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,"Error fetching data", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e(TAG, "onErrorResponse: ",error );

            }
        });
        //send the request queue
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);



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
