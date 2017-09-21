package com.jorgebaralt.athlete_mindful_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmotionalFragment extends Fragment {

    ArrayList<Question> emotionalQuestionFreeAnswer = new ArrayList<>();
    ArrayList<Question> emotionalQuestionMultipleChoice = new ArrayList<>();

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

        emotionalQuestionFreeAnswer = getQuestions.GetFreeAnswer(emotionalQuestionFreeAnswer,"some url");


        return rootView;
    }

}
