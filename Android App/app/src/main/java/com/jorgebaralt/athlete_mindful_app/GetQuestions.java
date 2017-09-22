package com.jorgebaralt.athlete_mindful_app;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Jorge Baralt on 9/18/2017.
 */

public class GetQuestions {

    Context context;

    public GetQuestions(Context context){
        this.context = context;
    }


    public ArrayList<Question> GetFreeAnswer(final ArrayList<Question> questions, String url){
        //if the array list passed is empty we fill it, otherwise we do not.
             JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    Log.d(TAG, "onResponse: *****************" + response);

                   for(int i = 0; i < response.length(); i ++ ){
                       try {
                           //get each question information
                           JSONObject currentQuestion = response.getJSONObject(i);
                           //make sure we only get type 1 questions ( free answer )
                           if(currentQuestion.getInt("question_type") == 1) {

                               int id = currentQuestion.getInt("id");
                               String question = currentQuestion.getString("question_text");
                               Log.d(TAG, "onResponse: QUESTION ===== " + question);

                               questions.add(new Question(question));

                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error.. " + error, Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                }
            });

        Log.d(TAG, "GetFreeAnswer: THE QUESTIONS ARE HERE" + questions);

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        //TODO onRepsonse is asynchronous so we need to find a way to make method wait for response to finish.
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
