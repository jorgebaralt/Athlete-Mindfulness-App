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

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MentalFragment extends Fragment {
    ArrayList<Question> mentalQuestionFreeAnswer = new ArrayList<>();
    ArrayList<Question> mentalQuestionMultipleChoice = new ArrayList<>();
    ListView listView;
    Button submitAnswers;
    Button submitMultipleChoice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_list,container,false);

        //get questions to display for the user From Database
        getFreeAnswerQuestion();
        getMultipleChoiceQuestion();


        //create the custom adapter
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

                Log.d(TAG, "onClick: Transit to multiple choice question");

                multipleChoiceQuestion();


            }


        });


        return rootView;
    }


    public void multipleChoiceQuestion(){
        //create custom adapter
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
                //Display message to user
                Log.d(TAG, "onClick: Displaying final message");
                Toast toast = Toast.makeText(getContext(),"All your answers have been submitted", Toast.LENGTH_LONG);
                toast.show();

            }
        });

    }

    public void getFreeAnswerQuestion(){
        //TODO: pull from database using JSON
        //Get all the Free Answer
        mentalQuestionFreeAnswer.add(new Question("Question 1" ));
        mentalQuestionFreeAnswer.add(new Question("Question 2"));
        mentalQuestionFreeAnswer.add(new Question("Question 3"));
        mentalQuestionFreeAnswer.add(new Question("Question 4"));
        mentalQuestionFreeAnswer.add(new Question("Question 5"));
        mentalQuestionFreeAnswer.add(new Question("Question 6"));
        mentalQuestionFreeAnswer.add(new Question("Question 7"));
        mentalQuestionFreeAnswer.add(new Question("Question 8"));
        mentalQuestionFreeAnswer.add(new Question("Question 9"));
        mentalQuestionFreeAnswer.add(new Question("Question 10"));


    }

    public void getMultipleChoiceQuestion() {
        //TODO: pull from database using JSON
        //Get all the Multiple choice
        mentalQuestionMultipleChoice.add(new Question("Question 1"));
        mentalQuestionMultipleChoice.add(new Question("Question 2"));
        mentalQuestionMultipleChoice.add(new Question("Question 3"));
        mentalQuestionMultipleChoice.add(new Question("Question 4"));
        mentalQuestionMultipleChoice.add(new Question("Question 5"));
        mentalQuestionMultipleChoice.add(new Question("Question 6"));
        mentalQuestionMultipleChoice.add(new Question("Question 7"));
        mentalQuestionMultipleChoice.add(new Question("Question 8"));
        mentalQuestionMultipleChoice.add(new Question("Question 9"));
        mentalQuestionMultipleChoice.add(new Question("Question 10"));
    }
}
