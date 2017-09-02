package com.jorgebaralt.athlete_mindful_app;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jorge Baralt on 5/25/2017.
 */

public class QuestionAdapterMultipleChoice extends ArrayAdapter<Question>{



    public static class ViewHolder{
        public TextView question;
        public RadioGroup radioGroup;
        public RadioButton radio1;
        public RadioButton radio2;
        public RadioButton radio3;

    }

    //constructor
    public QuestionAdapterMultipleChoice(Activity context, ArrayList<Question> question){
        super(context,0,question);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        final Question currentQuestion =  getItem(position);

        ViewHolder holder;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.question_radio_item,parent,false);
            holder = new ViewHolder();
            holder.question = (TextView) listItemView.findViewById(R.id.txtQuestion);
            holder.radioGroup = (RadioGroup) listItemView.findViewById(R.id.radiogroup);

            listItemView.setTag(holder);
            holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    //to identify the Question Object I get from the radiogroup with getTag
                    //the integer represent the position
                    Integer pos = (Integer) group.getTag();
                    Question currentPosition =  getItem(pos);
                    switch (checkedId){
                        case R.id.radioBtn1:
                            currentPosition.current = Question.answer0;
                            break;
                        case R.id.radioBtn2:
                            currentPosition.current = Question.answer1;
                            break;
                        case R.id.radioBtn3:
                            currentPosition.current = Question.answer2;
                            break;
                        default:
                            currentPosition.current = Question.NONE;

                    }

                }
            });


        }else{
            holder = (ViewHolder) convertView.getTag();
            listItemView.setTag(holder);
        }
        //set the question
        holder.question.setText(currentQuestion.getQuestion());
        //pass current position as tag
        holder.radioGroup.setTag(new Integer(position));

        if(currentQuestion.current != Question.NONE){
            //select the one that has been marked
            RadioButton r = (RadioButton) holder.radioGroup.getChildAt(currentQuestion.current);
            //mark it checked.
            r.setChecked(true);
        }else{
            holder.radioGroup.clearCheck();
        }





        return listItemView;
    }


}
