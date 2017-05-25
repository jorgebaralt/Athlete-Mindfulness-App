package com.jorgebaralt.athlete_mindful_app;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jorge Baralt on 5/25/2017.
 */

public class QuestionAdapter extends ArrayAdapter{
    //constructor
    public QuestionAdapter(Activity context, ArrayList<Users> users){
        super(context,0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.question_item, parent, false);
        }
        //get the current user that we are loading
        Question currentQuestion = (Question) getItem(position);
        //display the username of the current user
        TextView questionTextView = (TextView) listItemView.findViewById(R.id.txtQuestion);
        questionTextView.setText(currentQuestion.getQuestion());
        //display the score of the current user.
        EditText answerTextView= (EditText) listItemView.findViewById(R.id.txtAnswer);
        answerTextView.setText(currentQuestion.getAnswer());

        return listItemView;
    }
}
