package com.jorgebaralt.athlete_mindful_app;

/**
 * Created by jorgebaraltq on 5/24/2017.
 */

//create object for users.
public class Users {
    private String username;
    private int score;

    public Users (String username, int score){
        this.username = username;
        this.score = score;
    }

    public String getUsername(){
        return username;
    }
    public int getScore(){
        return score;
    }

}
