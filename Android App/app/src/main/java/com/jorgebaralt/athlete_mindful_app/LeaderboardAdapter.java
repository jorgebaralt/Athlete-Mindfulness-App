package com.jorgebaralt.athlete_mindful_app;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by jorgebaraltq on 5/24/2017.
 */

public class LeaderboardAdapter extends ArrayAdapter {
    private int score;
    public LeaderboardAdapter(Activity context, ArrayList<Users> users){
        super(context,0,users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.leaderboard_item, parent, false);
        }
        //get the current user that we are loading
        Users currentUser = (Users) getItem(position);
        //display the username of the current user
        TextView usernameTextView = (TextView) listItemView.findViewById(R.id.txtUsername);
        usernameTextView.setText(currentUser.getUsername());
        //display the score of the current user.
        TextView scoreTextView = (TextView) listItemView.findViewById(R.id.txtScore);
        scoreTextView.setText("Current Score:" + Integer.toString(currentUser.getScore()));

        return listItemView;
    }
}
