package com.jorgebaralt.athlete_mindful_app;


import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Jorge Baralt on 9/18/2017.
 */

public class GetQuestions {


    private String url;


    public ArrayList<Question> GetFreeAnswer( ArrayList<Question> questions,String url){
        this.url = url;
        if(questions.isEmpty()){
            questions.add(new Question("Question 1"));
            questions.add(new Question("Question 2"));
            questions.add(new Question("Question 3"));
            questions.add(new Question("Question 4"));
            questions.add(new Question("Question 5"));
            questions.add(new Question("Question 6"));
            questions.add(new Question("Question 7"));
            questions.add(new Question("Question 8"));
            questions.add(new Question("Question 9"));
            questions.add(new Question("Question 10"));
        }
        else{
            Log.d(TAG, "GetFreeAnswer: Questions are already filled");
        }
        return questions;
    }
    public ArrayList<Question> GetMultipleChoice( ArrayList<Question> questions,String url){
        this.url = url;
        if(questions.isEmpty()){
            questions.add(new Question("Question 1"));
            questions.add(new Question("Question 2"));
            questions.add(new Question("Question 3"));
            questions.add(new Question("Question 4"));
            questions.add(new Question("Question 5"));
            questions.add(new Question("Question 6"));
            questions.add(new Question("Question 7"));
            questions.add(new Question("Question 8"));
            questions.add(new Question("Question 9"));
            questions.add(new Question("Question 10"));
        }
        else{
            Log.d(TAG, "GetFreeAnswer: Questions are already filled");
        }
        return questions;
    }

}
