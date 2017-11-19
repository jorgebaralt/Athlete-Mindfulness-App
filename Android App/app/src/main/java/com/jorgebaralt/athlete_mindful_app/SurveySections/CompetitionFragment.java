package com.jorgebaralt.athlete_mindful_app.SurveySections;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.API.ApiInterface;
import com.jorgebaralt.athlete_mindful_app.Adapters.QuestionAdapter;
import com.jorgebaralt.athlete_mindful_app.Adapters.QuestionAdapterMultipleChoice;
import com.jorgebaralt.athlete_mindful_app.Answer;
import com.jorgebaralt.athlete_mindful_app.Util.HideKeyboard;
import com.jorgebaralt.athlete_mindful_app.Player;
import com.jorgebaralt.athlete_mindful_app.Question;
import com.jorgebaralt.athlete_mindful_app.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompetitionFragment extends Fragment {

    private int playerId;
    private int questionId;
    private String answer;
    private int points;

    private final int FREE_ANSWER_TYPE = 1;
    private final int MULT_ANSWER_TYPE = 2;
    private final int COMPETITION_CATEGORY = 6;

    private ArrayList<Question> competitionQuestionFreeAnswer = new ArrayList<>();
    private ArrayList<Question> competitionQuestionMultipleChoice = new ArrayList<>();
    private ArrayList<Answer> answers = new ArrayList<>();

    ListView listView;
    Button submitAnswers;
    Button submitMultipleChoice;


    Player currentPlayer;
    Answer currentAnswer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.question_list, container, false);

        //Get object of the player that is currently logged in
        currentPlayer = (Player) getActivity().getIntent().getSerializableExtra("currentPlayer");

        //fill array list with free answer questions from database using RETROFIT
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ArrayList<Question>> call = apiInterface.getQuestion(FREE_ANSWER_TYPE, COMPETITION_CATEGORY);

        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {

                //get response from server and store into array list (response comes in form of ArrayList)
                competitionQuestionFreeAnswer = response.body();

                //create the custom adapter\
                QuestionAdapter adapter = new QuestionAdapter(getActivity(), competitionQuestionFreeAnswer);
                //select the layout list to fill
                listView = (ListView) rootView.findViewById(R.id.questionlist);
                //fill the view.
                listView.setAdapter(adapter);


                //Adding Submit Button, as a footer.
                submitAnswers = new Button(getContext());
                submitAnswers.setText("Submit");
                listView.addFooterView(submitAnswers);
                submitAnswers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //close the android keyboard first
                        HideKeyboard.hideSoftKeyboard(getActivity());

                        //remove current footer
                        listView.removeFooterView(submitAnswers);


                        //Store all FREE ANSWERS into DATABASE
                        pushAnswers(competitionQuestionFreeAnswer,FREE_ANSWER_TYPE);


                        //clear array list after storing into database.
                        competitionQuestionFreeAnswer.clear();

                        Log.d(TAG, "onClick: Transit to multiple choice question");
                        multipleChoiceQuestion();
                    }

                });
            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {
                Toast.makeText(getActivity(), "ERROR.. couldnt load questions", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }


    public void multipleChoiceQuestion() {
        //fill array list with the multiple choice questions from database

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ArrayList<Question>> call = apiInterface.getQuestion(MULT_ANSWER_TYPE,COMPETITION_CATEGORY);
        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                //get response from server and store in array list
                competitionQuestionMultipleChoice = response.body();

                //create custom adapter for multiple choice
                final QuestionAdapterMultipleChoice adapter = new
                        QuestionAdapterMultipleChoice(getActivity(), competitionQuestionMultipleChoice);
                //using same list view, that has been empty before, we fill it with the new adapter info.
                listView.setAdapter(adapter);

                //Footer Button for multiple choice
                submitMultipleChoice = new Button(getContext());
                submitMultipleChoice.setText("Submit");
                listView.addFooterView(submitMultipleChoice);
                submitMultipleChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: Saving answers into database");
                        //Store answers into database
                        pushAnswers(competitionQuestionMultipleChoice,MULT_ANSWER_TYPE);
                        answers.clear();

                        //clear the arraylist after storing?
                        //competitionQuestionMultipleChoice.clear();


                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {
                Log.e(TAG, "onFailure: Error retrofit getting mental questions", t);
            }
        });


    }

    //Add Answers to database, pass the array list question to get the answers from it.
    public void pushAnswers(ArrayList<Question> currentQuestions, int type) {
        answers.clear();

        if (currentPlayer != null) {
            for (int i = 0; i < currentQuestions.size(); i++) {
                playerId = currentPlayer.getId();
                questionId = currentQuestions.get(i).getId();
                answer = currentQuestions.get(i).getAnswer();
                Log.d(TAG, "pushAnswers: Answer = " + answer);
                //set points
                if (type == FREE_ANSWER_TYPE) {
                    points = 5;
                } else {
                    points = 3;
                }

                //Create the object
                if (answer != null && !answer.equals("")) {
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
                            Toast.makeText(getContext(), "Free Questions Answers Added...", Toast.LENGTH_SHORT).show();
                            //answers.clear();
                        } else {
                            Toast.makeText(getContext(), "Error.." + response.body(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Answer>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getContext(), "Error connecting to server..", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        }

    }

}
