package com.jorgebaralt.athlete_mindful_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

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
        //fill the VIEW.
        listView.setAdapter(adapter);

        //TODO: add the answer from the questions to the Database



        return rootView;
    }

}
