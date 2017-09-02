package com.jorgebaralt.athlete_mindful_app;

/**
 * Created by Jorge Baralt on 5/25/2017.
 */

public class Question {
    private String question;
    private String answer;
    int current = NONE;
    public static final int NONE = 10000;
    public static final int answer0 = 0;
    public static final int answer1 = 1;
    public static final int answer2 = 2;

    public Question(String question){
        this.question = question;

    }
    public String getQuestion(){
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


}
