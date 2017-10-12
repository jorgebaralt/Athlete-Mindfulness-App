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

public class QuestionAdapter extends ArrayAdapter<Question>{


    public static class ViewHolder{
        public TextView question;
        public EditText answer;

    }

    //constructor
    public QuestionAdapter(Activity context, ArrayList<Question> question){
        super(context,0,question);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        final Question currentQuestion =  getItem(position);
        ViewHolder holder;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.question_item,parent,false);
            holder = new ViewHolder();
            holder.answer = (EditText) listItemView.findViewById(R.id.txtAnswer);
            holder.question = (TextView) listItemView.findViewById(R.id.txtQuestion);
            listItemView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
            listItemView.setTag(holder);
        }

        holder.question.setText(currentQuestion.getQuestion());
        holder.answer.setText(currentQuestion.getAnswer());


        holder.answer.setId(position);
        //EditText Change Listener so update the answer.
        holder.answer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    final EditText Caption = (EditText) v;
                    //save answer into current object
                    currentQuestion.setAnswer(Caption.getText().toString());
                }
            }
        });


        return listItemView;
    }


}
