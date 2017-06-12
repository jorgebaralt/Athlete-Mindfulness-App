package com.jorgebaralt.athlete_mindful_app;

/**
 * Created by Jorge Baralt on 5/25/2017.
 */

public class Question {
    private String question;
    private int type;

    public Question(String question, int type){
        this.question = question;
        this.type = type;
    }
    public String getQuestion(){
        return question;
    }
    public int getType(){
       return type;
    }
}
