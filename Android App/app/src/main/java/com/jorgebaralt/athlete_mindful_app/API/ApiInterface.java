package com.jorgebaralt.athlete_mindful_app.API;

import com.jorgebaralt.athlete_mindful_app.Coach;
import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.Question;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by User on 9/22/2017.
 */

public interface ApiInterface {
    @GET("/coaches")
    Call<ArrayList<Coach>> getCoaches();

    @GET("/leaderboard")
    Call<ArrayList<Player>> getLeaderboard();

    //GET with parameters
    @GET("/mental_questions")
    Call<ArrayList<Question>> getMentalQuestions(
            //parameters to query
            @Query("question_type") int questionType
        );


    @POST("/players")
    Call<Player> createPlayer(@Body Player player);





}
