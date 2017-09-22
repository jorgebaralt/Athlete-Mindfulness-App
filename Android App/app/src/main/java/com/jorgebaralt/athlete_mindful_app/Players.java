package com.jorgebaralt.athlete_mindful_app;

import android.support.annotation.NonNull;

/**
 * Created by jorgebaraltq on 5/24/2017.
 */

//create object for users.
public class Players implements Comparable<Players> {
    private String name;
    private int score;
    private String email;

    public Players(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return name;
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
