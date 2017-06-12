package com.jorgebaralt.athlete_mindful_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MentalFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_list,container,false);

        ArrayList<Question> mentalQuestion = new ArrayList<>();
        mentalQuestion.add(new Question("Question 1"));
        mentalQuestion.add(new Question("Question 2"));
        mentalQuestion.add(new Question("Question 3"));
        mentalQuestion.add(new Question("Question 4"));
        mentalQuestion.add(new Question("Question 5"));
        mentalQuestion.add(new Question("Question 6"));
        mentalQuestion.add(new Question("Question 7"));
        mentalQuestion.add(new Question("Question 8"));
        mentalQuestion.add(new Question("Question 9"));
        mentalQuestion.add(new Question("Question 10"));

        QuestionAdapter adapter = new QuestionAdapter(getActivity(),mentalQuestion);
        //select the layout list to fill
        ListView listView = (ListView) rootView.findViewById(R.id.questionlist);
        //fill the VIEW.
        listView.setAdapter(adapter);


        return rootView;
    }

}
