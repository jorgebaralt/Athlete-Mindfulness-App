package com.jorgebaralt.athlete_mindful_app.API;


import android.content.Context;
import android.util.Log;

import com.jorgebaralt.athlete_mindful_app.Question;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by Jorge Baralt on 9/18/2017.
 */

public class GetQuestions {

    ArrayList<Question>  questions = new ArrayList<>();
    Context context;

    public GetQuestions(Context context){
        this.context = context;
    }


    public ArrayList<Question> GetFreeAnswer(String BASE_URL){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
       // Call<ArrayList<Question>> call =  apiInterface.getMentalFreeQuestions(1);

        return questions;
    }




    public ArrayList<Question> GetMultipleChoice( ArrayList<Question> questions,String url){
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
