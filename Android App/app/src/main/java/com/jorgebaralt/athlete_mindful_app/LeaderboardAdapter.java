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
 * Created by jorgebaraltq on 5/24/2017.
 */

public class LeaderboardAdapter extends ArrayAdapter {

    public LeaderboardAdapter(Activity context, ArrayList<Players> users){
        super(context,0,users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        // ** REMEMBER TO INFLATE THE SPECIFIC LAYOUT FOR EACH VIEW (LEADERBOARD_ITEM)**
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.leaderboard_item, parent, false);
        }

        //get the current user that we are loading
        Players currentUser = (Players) getItem(position);
        //display the username of the current user
        TextView usernameTextView = (TextView) listItemView.findViewById(R.id.txtUsername);
        usernameTextView.setText(currentUser.getName());
        //display the score of the current user.
        TextView scoreTextView = (TextView) listItemView.findViewById(R.id.txtScore);
        scoreTextView.setText("Current Score:" + Integer.toString(currentUser.getScore()));
        //TODO change image of each user. NUMBER OR PROFILE PIC?

        return listItemView;
    }
}
