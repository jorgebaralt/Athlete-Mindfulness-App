package com.jorgebaralt.athlete_mindful_app;


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
import com.jorgebaralt.athlete_mindful_app.API.GetQuestions;

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
public class MentalFragment extends Fragment {


    final static String BASE_URL = "http://project-env-4.us-east-1.elasticbeanstalk.com";
    ArrayList<Question> mentalQuestionFreeAnswer = new ArrayList<>();
    ArrayList<Question> mentalQuestionMultipleChoice = new ArrayList<>();
    private final int FREE_ANSWER_TYPE = 1;
    private final int MULT_ANSWER_TYPE = 2;

    final String mental_questions_url = "http://project-env-4.us-east-1.elasticbeanstalk.com";

    ListView listView;
    Button submitAnswers;
    Button submitMultipleChoice;

    GetQuestions getQuestions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.question_list,container,false);



        //fill array list with free answer questions from database

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            //TODO: make the api receive a parameter to only get the question_type 1 or 2 accordingly.
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            Call<ArrayList<Question>> call =  apiInterface.getMentalQuestions(FREE_ANSWER_TYPE);
            call.enqueue(new Callback<ArrayList<Question>>() {
                @Override
                public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                    //get response from server and store into array list
                    mentalQuestionFreeAnswer = response.body();
                    //create the custom adapter\
                    QuestionAdapter adapter = new QuestionAdapter(getActivity(), mentalQuestionFreeAnswer);
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
                            //remove current footer
                            listView.removeFooterView(submitAnswers);

                            Log.d(TAG, "onClick: adding answers data to database");

                            //TODO: store all the free question's answer into the database

                            //clear array list after storing into database.
                            mentalQuestionFreeAnswer.clear();
                            Log.d(TAG, "onClick: SIZE OF ARRAYLIST = " + mentalQuestionFreeAnswer.size());

                            Log.d(TAG, "onClick: Transit to multiple choice question");

                            multipleChoiceQuestion();
                        }

                    });
                }

                @Override
                public void onFailure(Call<ArrayList<Question>> call, Throwable t) {
                    Toast.makeText(getActivity(),"ERROR.. couldnt load questions", Toast.LENGTH_SHORT).show();
                }
            });

        return rootView;
    }


    public void multipleChoiceQuestion(){
        //fill array list with the multiple choice questions from database

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            //TODO: make the api receive a parameter to only get the question_type 1 or 2 accordingly.
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            Call<ArrayList<Question>> call =  apiInterface.getMentalQuestions(MULT_ANSWER_TYPE);
            call.enqueue(new Callback<ArrayList<Question>>() {
                @Override
                public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                    //get response from server and store in array list
                    mentalQuestionMultipleChoice = response.body();

                    //create custom adapter for multiple choice
                    final QuestionAdapterMultipleChoice adapter = new
                            QuestionAdapterMultipleChoice(getActivity(), mentalQuestionMultipleChoice);
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
                            //TODO: Store answers into database
                            //clear the arraylist after storing

                            //mentalQuestionMultipleChoice.clear();  ----------> MAKING APP CRASH
                            //Display message to user
                            Log.d(TAG, "onClick: Displaying final message");
                            Toast toast = Toast.makeText(getContext(),"All your answers have been submitted", Toast.LENGTH_LONG);
                            toast.show();

                        }
                    });
                }

                @Override
                public void onFailure(Call<ArrayList<Question>> call, Throwable t) {
                    Log.e(TAG, "onFailure: Error retrofit getting mental questions", t );
                }
            });





    }


}
