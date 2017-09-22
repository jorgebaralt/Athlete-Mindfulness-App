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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    final ArrayList<Coach>  coachList = new ArrayList<>();

    //Variables
    static final String coaches_url = "http://project-env-4.us-east-1.elasticbeanstalk.com/coaches";
    static final String insert_players_url = "http://project-env-4.us-east-1.elasticbeanstalk.com/players";
    AlertDialog.Builder builder;

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
    EditText textAge;
    RadioGroup radioGender;
    RadioButton radioSelectedGender;
    Button submit;

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

    //JSON REQUEST TO GET EXISTING COACHES
    public void getCoaches(){
        coachList.add(new Coach (0,"Select Coach"));

        Log.d(TAG, "getCoaches: STARTING JSON ARRAY REQUEST FOR COACHES");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, coaches_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for( int i = 0 ; i < response.length(); i++){
                    try {
                        JSONObject currentCoach = response.getJSONObject(i);
                        int id = currentCoach.getInt("id");
                        String firstname = currentCoach.getString("first_name");
                        String lastname = currentCoach.getString("last_name");
                        String coachName = firstname + " " + lastname;

                        //create coach object
                        coachList.add((new Coach(id,coachName)));

                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: Error Doing inside try");
                        e.printStackTrace();
                    }
                }

                //SPINNER CODE**
                //store the name of the coaches on a string
                String[] coaches = new String[coachList.size()];
                for(int i = 0 ; i < coachList.size();i++){
                    coaches[i] = coachList.get(i).getName();
                }

                //create spinner adapter that display names of the coaches to select.
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
        int id = -1;

        //get coach id
        String selectedCoach = spinner.getSelectedItem().toString();
        for(int i = 0; i < coachList.size(); i ++ ){
            if(coachList.get(i).getName().equals(selectedCoach)){
                id = coachList.get(i).getId();
                Log.d(TAG, "insertPlayer: FOUND COACH ID : " + id);
                break;
            }
        }
        final String coachId = Integer.toString(id);

        //get ageRange
        int ageGroup;
        int ageNum = Integer.parseInt(age);
        if(ageNum<= 13){
            ageGroup = 1;
        }else if(ageNum>=14 && ageNum<= 15){
            ageGroup = 2;
        }else if(ageNum>=16 && ageNum<= 19){
            ageGroup = 3;
        }else{
            ageGroup = 4;
        }
        final String ageRange = Integer.toString(ageGroup);

        //get gender from radio
        String genderId;
        //get the id of the selected radio button.
        int selectedId = radioGender.getCheckedRadioButtonId();
        radioSelectedGender = (RadioButton) findViewById(selectedId);
        if(radioSelectedGender.getText().toString().equals("Male")){
            genderId = "1";
        }
        else {
            genderId = "2";
        }
        final String gender = genderId;
        //Create the alert dialog.
        builder = new AlertDialog.Builder(RegisterActivity.this);

        //start string request to create users.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, insert_players_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                builder.setTitle("Server Response");
                Log.d(TAG, "onResponse: User Added : " + response);
                builder.setMessage("Response : User has been created" );
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        goToNavigation();
                    }
                });
                //create the dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Error..." + error, Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("first_name",firstName);
                params.put("last_name",lastName);
                params.put("email",email);
                params.put("password",password);
                params.put("password_confirmation",passwordConfirmation);
                params.put("phone",phone);
                params.put("gender",gender);
                params.put("points","0");
                params.put("coach_id",coachId);
                params.put("age",age);
                params.put("age_range",ageRange);
                return params;
            }
        };
        //send request to the queue.
        MySingleton.getInstance(RegisterActivity.this).addToRequestQueue(stringRequest);

    }



    public void goToSurvey(View view){
        Intent intent = new Intent(this,SurveyActivity.class);
        startActivity(intent);
    }
    public void goToNavigation(){
        Intent intent = new Intent(this,NavigationDrawer.class);
        startActivity(intent);
    }

}
