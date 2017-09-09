package com.jorgebaralt.athlete_mindful_app;

import android.support.annotation.NonNull;

/**
 * Created by jorgebaraltq on 5/24/2017.
 */

//create object for users.
public class Players implements Comparable<Players> {
    private String username;
    private int score;


    public Players(String username, int score){
        this.username = username;
        this.score = score;
    }

    public String getUsername(){
        return username;
    }
    public int getScore(){
        return score;
    }

    @Override
    public int compareTo(@NonNull Players comparePlayer) {
        int compareScore =  comparePlayer.getScore();
        //descending order.
        return compareScore - this.score;
    }

}
