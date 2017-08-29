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

public class QuestionAdapterMultipleChoice extends ArrayAdapter<Question>{


    public static class ViewHolder{
        public TextView question;
        public EditText Radio;

    }

    //constructor
    public QuestionAdapterMultipleChoice(Activity context, ArrayList<Question> question){
        super(context,0,question);

;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        final Question currentQuestion =  getItem(position);

        ViewHolder holder;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.question_radio_item,parent,false);
            holder = new ViewHolder();
            holder.question = (TextView) listItemView.findViewById(R.id.txtQuestion);
            listItemView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.question.setText("TEST");



        return listItemView;
    }


}
