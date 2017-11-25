package com.jorgebaralt.athlete_mindful_app.SurveySections;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.API.ApiInterface;
import com.jorgebaralt.athlete_mindful_app.Adapters.QuestionAdapter;
import com.jorgebaralt.athlete_mindful_app.Adapters.QuestionAdapterMultipleChoice;
import com.jorgebaralt.athlete_mindful_app.Answer;
import com.jorgebaralt.athlete_mindful_app.LoginActivity;
import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.Question;
import com.jorgebaralt.athlete_mindful_app.R;
import com.jorgebaralt.athlete_mindful_app.Util.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OneTimeQuestion extends AppCompatActivity {

    private int playerId;
    private int questionId;
    private String answer;
    private int points;


    private final int FREE_ANSWER_TYPE = 1;
    private final int MULT_ANSWER_TYPE = 2;
    private final int ONETIME_CATEGORY = 7;

    private ArrayList<Question> oneTimeQuestionFreeAnswer = new ArrayList<>();
    private ArrayList<Question> oneTimeQuestionMultipleChoice = new ArrayList<>();
    private ArrayList<Answer> answers = new ArrayList<>();

    final String TAG = "OneTimeAnswer";

    Button submitAnswers;
    Button submitMultipleChoice;

    Player currentPlayer;
    Answer currentAnswer;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list);
        //get current player
        currentPlayer = (Player) this.getIntent().getSerializableExtra("currentPlayer");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        //Create api.
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        //API call
        Call<ArrayList<Question>> call = apiInterface.getQuestion(FREE_ANSWER_TYPE, ONETIME_CATEGORY);
        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                oneTimeQuestionFreeAnswer = response.body();
                Log.d(TAG, "onResponse: ");
                QuestionAdapter adapter = new QuestionAdapter(OneTimeQuestion.this, oneTimeQuestionFreeAnswer);
                listView = (ListView) findViewById(R.id.questionlist);
                listView.setAdapter(adapter);

                //add new button
                submitAnswers = new Button(getApplicationContext());
                submitAnswers.setText("Submit");
                listView.addFooterView(submitAnswers);

                //button pressed.
                submitAnswers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //close the android keyboard first
                        Util.hideSoftKeyboard(OneTimeQuestion.this);
                        //remove current footer
                        listView.removeFooterView(submitAnswers);



                        //Store all FREE ANSWERS into DATABASE
                        pushAnswers(oneTimeQuestionFreeAnswer,FREE_ANSWER_TYPE);

                        //clear array list after storing into database.
                        oneTimeQuestionFreeAnswer.clear();

                        Log.d(TAG, "onClick: Transit to multiple choice question");

                        multipleChoiceQuestion();
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {
                Toast.makeText(OneTimeQuestion.this, "ERROR.. couldnt load questions", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void multipleChoiceQuestion() {
        //fill array list with the multiple choice questions from database

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ArrayList<Question>> call = apiInterface.getQuestion(MULT_ANSWER_TYPE,ONETIME_CATEGORY);
        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                //get response from server and store in array list
                oneTimeQuestionMultipleChoice = response.body();


                //create custom adapter for multiple choice
                final QuestionAdapterMultipleChoice adapter = new
                        QuestionAdapterMultipleChoice(OneTimeQuestion.this, oneTimeQuestionMultipleChoice);
                //using same list view, that has been empty before, we fill it with the new adapter info.
                listView.setAdapter(adapter);

                //Footer Button for multiple choice
                submitMultipleChoice = new Button(OneTimeQuestion.this);
                submitMultipleChoice.setText("Submit");
                listView.addFooterView(submitMultipleChoice);
                submitMultipleChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: Saving answers into database, mult");
                        //Store answers into database
                        pushAnswers(oneTimeQuestionFreeAnswer, MULT_ANSWER_TYPE);

                        //go back to login
                        Intent intent = new Intent(OneTimeQuestion.this, LoginActivity.class);
                        startActivity(intent);


                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {
                Log.e(TAG, "onFailure: Error retrofit getting mental questions", t);
            }
        });


    }

    //Add Answers to database, pass the arraylist question to get the answers from it.
    //takes care of any type of question (Free answer, or Multiple Choice)
    public void pushAnswers(ArrayList<Question> currentQuestions, int type) {
        Log.d(TAG, "pushAnswers: Getting answers");
        answers.clear();

        if (currentPlayer != null) {
            for (int i = 0; i < currentQuestions.size(); i++) {
                playerId = currentPlayer.getId();
                questionId = currentQuestions.get(i).getId();
                answer = currentQuestions.get(i).getAnswer();

                //set points
                if (type == FREE_ANSWER_TYPE) {
                    points = 5;
                } else {
                    points = 3;
                }

                //Create the object
                if (answer != null && !answer.equals("")) {
                    Log.d(TAG, "Create Answer: Creating Answer = " + answer);
                    currentAnswer = new Answer(answer, playerId, questionId, points);
                    answers.add(currentAnswer);

                } else {
                    //TODO: COUNT HOW MANY WE MISS FOR FUTURE REFERENCE
                    //Maybe push how many and which one we missed for notifications?
                }

            }
            //after we add all the asnwer to our arraylist
            //send it to the server.
            if (answers.size() > 0) {
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(ApiInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();

                ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                Call<ArrayList<Answer>> call = apiInterface.pushAnswers(answers);
                call.enqueue(new Callback<ArrayList<Answer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Answer>> call, Response<ArrayList<Answer>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: answers pushed!");
                            Toast.makeText(OneTimeQuestion.this, "Free Questions Answers Added...", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(OneTimeQuestion.this, "Error.." + response.body(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Answer>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(OneTimeQuestion.this, "Error connecting to server..", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        }

    }
}
