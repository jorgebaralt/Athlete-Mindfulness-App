package com.jorgebaralt.athlete_mindful_app;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jorge Baralt on 5/25/2017.
 */

public class QuestionAdapter extends ArrayAdapter{
    //constructor
    public QuestionAdapter(Activity context, ArrayList<Question> question){
        super(context,0,question);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        //inflate the specific item
        //get the current question that we are loading
        Question currentQuestion = (Question) getItem(position);

        //decide whether the question is type 1 = free answer, or type 2 = radio button
        if(currentQuestion.getType()==1){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.question_item, parent, false);}
        else{
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.question_radio_item, parent, false);}




        //set the question string from arraylist, into display
        TextView questionTextView = (TextView) listItemView.findViewById(R.id.txtQuestion);
        questionTextView.setText(currentQuestion.getQuestion());


        return listItemView;
    }
}
