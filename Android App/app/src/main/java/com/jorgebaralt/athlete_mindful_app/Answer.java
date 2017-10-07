package com.jorgebaralt.athlete_mindful_app;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 10/6/2017.
 */

public class Answer {
    private String answer;
    @SerializedName("player_id")
    private int playerId;
    @SerializedName("question_id")
    private int questionId;

    public Answer(String answer, int playerId, int questionId){
        this.answer = answer;
        this.playerId = playerId;
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getQuestionId() {
        return questionId;
    }
}
