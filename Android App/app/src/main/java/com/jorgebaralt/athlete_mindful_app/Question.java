package com.jorgebaralt.athlete_mindful_app;

/**
 * Created by Jorge Baralt on 5/25/2017.
 */

public class Question {
    private String question;
    private int type;
    private String answer;

    public Question(String question, int type, String answer){
        this.question = question;
        this.type = type;
        this.answer = answer;
    }
    public String getQuestion(){
        return question;
    }
    public int getType(){
       return type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
