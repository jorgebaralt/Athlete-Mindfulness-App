package com.jorgebaralt.athlete_mindful_app.Adapters;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jorgebaralt.athlete_mindful_app.Question;
import com.jorgebaralt.athlete_mindful_app.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Jorge Baralt on 5/25/2017.
 */

public class QuestionAdapterMultipleChoice extends ArrayAdapter<Question>{

    private final int NONE = -1;
    private int size;

    public static class ViewHolder{
        public TextView question;
        public RadioGroup radioGroup;
        public RadioButton radioButton1;
        public RadioButton radioButton2;
        public RadioButton radioButton3;
    }

    //constructor
    public QuestionAdapterMultipleChoice(Activity context, ArrayList<Question> question){
        super(context,0,question);
        size = question.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        final Question currentQuestion =  getItem(position);
        currentQuestion.current = NONE;
        final ViewHolder holder;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.question_radio_item,parent,false);
            holder = new ViewHolder();
            holder.question = (TextView) listItemView.findViewById(R.id.txtQuestion);
            holder.radioGroup = (RadioGroup) listItemView.findViewById(R.id.radiogroup);
            holder.radioButton1 = (RadioButton) listItemView.findViewById(R.id.radioBtn1);
            holder.radioButton2 = (RadioButton) listItemView.findViewById(R.id.radioBtn2);
            holder.radioButton3 = (RadioButton) listItemView.findViewById(R.id.radioBtn3);

            listItemView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
            listItemView.setTag(holder);
        }

        //set the question

        holder.question.setText(currentQuestion.getQuestion());
        holder.radioButton1.setText(currentQuestion.getOptions()[0]);
        holder.radioButton2.setText(currentQuestion.getOptions()[1]);
        holder.radioButton3.setText(currentQuestion.getOptions()[2]);

        //pass current position as tag
        holder.radioGroup.setTag(new Integer(position));

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //to identify the Question Object I get from the radio group with getTag
                //the integer represent the position
                Integer pos = (Integer) group.getTag();
                Question currentPosition =  getItem(pos);
                //set the Model to hold the answer the user picked
                switch (checkedId){
                    case R.id.radioBtn1:
                        currentPosition.current = Question.answer0;
                        currentQuestion.setAnswer(currentQuestion.getOptions()[0]);
                        currentQuestion.setPosition(1);
                        Log.d(TAG, "onCheckedChanged: " + currentQuestion.getAnswer());
                        break;
                    case R.id.radioBtn2:
                        currentPosition.current = Question.answer1;
                        currentQuestion.setAnswer(currentQuestion.getOptions()[1]);
                        currentQuestion.setPosition(2);
                        Log.d(TAG, "onCheckedChanged: " + currentQuestion.getAnswer());

                        break;
                    case R.id.radioBtn3:
                        currentPosition.current = Question.answer2;
                        currentQuestion.setAnswer(currentQuestion.getOptions()[2]);
                        currentQuestion.setPosition(3);
                        Log.d(TAG, "onCheckedChanged: " + currentQuestion.getAnswer());
                        break;
                    default:
                        currentPosition.current = Question.NONE;
                }
                if(currentQuestion.current != Question.NONE){
                    //select the one that has been marked
                    RadioButton r = (RadioButton) holder.radioGroup.getChildAt(currentQuestion.current);
                    //mark it checked.
                    r.setChecked(true);
                    //add answer to object
                    currentQuestion.setAnswer(r.getText().toString());

                }else{
                    holder.radioGroup.clearCheck();
                }

            }
        });



        return listItemView;
    }

    @Override
    public int getViewTypeCount() {
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}