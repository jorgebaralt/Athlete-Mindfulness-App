package com.jorgebaralt.athlete_mindful_app.API;

import com.jorgebaralt.athlete_mindful_app.Answer;
import com.jorgebaralt.athlete_mindful_app.Chat.Token;
import com.jorgebaralt.athlete_mindful_app.Coach;
import com.jorgebaralt.athlete_mindful_app.Notifications;
import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.Question;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 9/22/2017.
 */

public interface ApiInterface {

    String BASE_URL = "http://postgresql-env-clone.us-east-1.elasticbeanstalk.com/";

    @GET("/coaches")
    Call<ArrayList<Coach>> getCoaches();

    @GET("/leaderboard")
    Call<ArrayList<Player>> getLeaderboard();

    //GET with parameters
    @GET("/questions")
    Call<ArrayList<Question>> getQuestion(
            //parameters to query
            @Query("question_type") int questionType,
            @Query("category") int category
        );

    //get specific player
    @GET("/players/{id}")
    Call<Player> getPlayer(@Path("id")int id);

    //Get notifications for players
    @GET("/players_notifications/")
    Call<ArrayList<Notifications>> getPlayerNotifications(
            @Query("user_id") int user_id
    );


    @FormUrlEncoded
    @POST("/players/sign_out/")
    Call<Player> logout (@Field("auth_token") String token);

    //Send device Id to get AccessToken to start a chat.
    @FormUrlEncoded
    @POST("/tokens")
    Call<Token> getToken(@Field("name") String name);

    //Player Registration
    @POST("/players")
    Call<Player> createPlayer(@Body Player player);

    //Authentication API
    @POST("/players/sign_in")
    Call<Player> login (@Body Login login);

    //push answers to database
    @POST("/multiple_answers")
    Call<ArrayList<Answer>> pushAnswers (@Body ArrayList<Answer> answer);

    @FormUrlEncoded
    @PUT("/players/{id}")
    Call<ResponseBody> updatePlayerEmail(@Path("id") String id,
                                         @Field("email") String email);

    @FormUrlEncoded
    @PUT("/players/{id}")
    Call<ResponseBody> updatePlayerCoach(@Path("id") String id,
                                         @Field("coach_id") String coachId,
                                         @Field("coach_name") String coachName);

    @FormUrlEncoded
    @PUT("/players/{id}")
    Call<ResponseBody> updatePlayerAge(@Path("id") String id,
                                       @Field("age") String age,
                                        @Field("age_range")String ageRange);


}
