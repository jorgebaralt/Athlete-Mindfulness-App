package com.jorgebaralt.athlete_mindful_app;


import com.google.gson.annotations.SerializedName;

/**
 * Created by jorge on 9/8/2017.
 */

public class Coach {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("id")
    private int id;

    public Coach(int id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " +  lastName;

    }

    public int getId() {
        return id;
    }
}
