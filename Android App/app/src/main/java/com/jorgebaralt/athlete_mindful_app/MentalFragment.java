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

    GetQuestions getQuestions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_list,container,false);

        //instantiate class that gets our questions
        getQuestions = new GetQuestions();

        //fill array list with free answer questions
        mentalQuestionFreeAnswer = getQuestions.GetFreeAnswer(mentalQuestionFreeAnswer,"current url");


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
                //clear array list after storing into database.
                mentalQuestionFreeAnswer.clear();

                Log.d(TAG, "onClick: Transit to multiple choice question");

                multipleChoiceQuestion();

            }

        });

        return rootView;
    }


    public void multipleChoiceQuestion(){
        //fill array list with the multiple choice questions
        mentalQuestionMultipleChoice = getQuestions.GetMultipleChoice(mentalQuestionMultipleChoice,"url");

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
                mentalQuestionMultipleChoice.clear();
                //Display message to user
                Log.d(TAG, "onClick: Displaying final message");
                Toast toast = Toast.makeText(getContext(),"All your answers have been submitted", Toast.LENGTH_LONG);
                toast.show();

            }
        });

    }


}
