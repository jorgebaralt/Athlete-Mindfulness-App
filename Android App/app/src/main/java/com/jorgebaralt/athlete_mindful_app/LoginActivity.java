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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jorgebaralt.athlete_mindful_app.API.MySingleton;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {
    TextView registerHere;
    EditText Email;
    EditText Password;
    Button btnLogin;


    String email;
    String password;
    AlertDialog.Builder builder;
    private static String sign_in_url = "http://project-env-4.us-east-1.elasticbeanstalk.com/players/sign_in";

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
                email = Email.getText().toString();
                password = Password.getText().toString();

                if(email.length() < 1 || password.length()<1){
                    builder.setTitle("Something Went Wrong...");
                    builder.setMessage("Enter Valid Email and Password...");
                    displayAlert("input_error");

                    goToNavigation(v); //FOR TESTING ISSUES!
                }else{

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, sign_in_url, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            //User exist, continue and
                            //TODO: API SHOULD RETURN EITHER login worked or failed
                            Log.d(TAG, "onResponse: LOGIN WORKED!!!!!!!!!!!!!" + response +  " <======" );

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            params.put("email",email);
                            params.put("password",password);
                            return params;
                        }
                    };

                    MySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
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
