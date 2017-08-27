package com.jorgebaralt.athlete_mindful_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MentalFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_list,container,false);

        //TODO: pull from database using JSON and get question string and type (1 or 2)

        ArrayList<Question> mentalQuestion = new ArrayList<>();
        mentalQuestion.add(new Question("Question 1",1, ""));
        mentalQuestion.add(new Question("Question 2",2, ""));
        mentalQuestion.add(new Question("Question 3",1, ""));
        mentalQuestion.add(new Question("Question 4",2, ""));
        mentalQuestion.add(new Question("Question 5",1, ""));
        mentalQuestion.add(new Question("Question 6",2, ""));
        mentalQuestion.add(new Question("Question 7",1, ""));
        mentalQuestion.add(new Question("Question 8",2, ""));
        mentalQuestion.add(new Question("Question 9",2, ""));
        mentalQuestion.add(new Question("Question 10",1, ""));

        QuestionAdapter adapter = new QuestionAdapter(getActivity(),mentalQuestion);
        //select the layout list to fill
        ListView listView = (ListView) rootView.findViewById(R.id.questionlist);
        //fill the view.
        listView.setAdapter(adapter);

        //Adding Submit Button, as a footer.
        Button submitAnswers = new Button(getContext());
        submitAnswers.setText("Submit");
        submitAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: store all the free question's answer into the database
                //TODO: transit into the multple choice question
                Log.d(TAG, "onClick: adding answers data to database");
            }
        });
        listView.addFooterView(submitAnswers);





        return rootView;
    }

}
