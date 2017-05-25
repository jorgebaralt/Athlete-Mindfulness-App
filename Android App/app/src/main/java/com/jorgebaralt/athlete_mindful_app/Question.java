package com.jorgebaralt.athlete_mindful_app;

/**
 * Created by Jorge Baralt on 5/25/2017.
 */

public class Question {
    private String question;
    private String answer;

    public Question(String question, String answer){
        this.question = question;
        this.answer = answer;
    }
    public String getQuestion(){
        return question;
    }
    public String getAnswer(){
        return answer;
    }
}
