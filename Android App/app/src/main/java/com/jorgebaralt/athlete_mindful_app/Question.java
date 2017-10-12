package com.jorgebaralt.athlete_mindful_app;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jorge Baralt on 5/25/2017.
 */

public class Question {
    private int id;
    @SerializedName("question_text")
    private String question;

    private String answer;
    private int category;

    @SerializedName("age_range")
    private int ageRange;

    private String options;
    public int current;
    public static final int NONE = -1;
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

    public String[] getOptions() {
        return options.split(",");
    }

    public int getId() {
        return id;
    }
}
