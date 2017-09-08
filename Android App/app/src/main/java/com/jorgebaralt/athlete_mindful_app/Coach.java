package com.jorgebaralt.athlete_mindful_app;



/**
 * Created by jorge on 9/8/2017.
 */

public class Coach {
    private String name;
    private int id;
    public Coach(int id, String name){
        this.id = id;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
