package com.jorgebaralt.athlete_mindful_app.API;

/**
 * Created by jorgebaralt on 10/1/17.
 */

//bject that we sent to API in order to confirm login
public class Login {
    private String email;
    private String password;

    public Login(String email, String password){
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
