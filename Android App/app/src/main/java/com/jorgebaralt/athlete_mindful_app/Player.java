package com.jorgebaralt.athlete_mindful_app;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


//create object for users.
public class Player implements Comparable<Player>, Serializable{
    private int id;

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("points")
    private int points;
    @SerializedName("coach_id")
    private int coachId;
    @SerializedName("age_range")
    private int ageRange;
    @SerializedName("password_confirmation")
    private String passwordConfirmation;
    @SerializedName("auth_token")
    private String token;

    private int gender;
    private String email;
    private int age;
    private String password;
    private String phone;





    public Player(String firstName, String lastName, int points){
        this.firstName = firstName;
        this.points = points;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return firstName.substring(0,1).toUpperCase() + firstName.substring(1) + " " + lastName.substring(0,1).toUpperCase() + lastName.substring(1);
    }
    public int getPoints(){
        return points;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public int getCoachId() {
        return coachId;
    }

    public int getAgeRange() {
        return ageRange;
    }


    public String getToken() { return token;}

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public void setAgeRange(int ageRange) {
        this.ageRange = ageRange;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public int compareTo(@NonNull Player comparePlayer) {
        int compareScore =  comparePlayer.getPoints();
        //descending order.
        return compareScore - this.points;
    }

}
