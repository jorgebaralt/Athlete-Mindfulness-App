package com.jorgebaralt.athlete_mindful_app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

/**
 * Created by jorgebaraltq on 3/10/2017.
 */

public class Profile extends Fragment{

    Player currentPlayer;
    TextView name;
    TextView points;
    TextView email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.profile_layout,container,false);

        //start by getting the player from the activity.
        currentPlayer = (Player) getActivity().getIntent().getSerializableExtra("currentPlayer");
        //initialize views
        name = (TextView) rootView.findViewById(R.id.profileName);
        email = (TextView) rootView.findViewById(R.id.profileEmail);
        points = (TextView) rootView.findViewById(R.id.profilePoints);

        if(currentPlayer != null) {
            //set views to current player info.
            name.setText(currentPlayer.getName());
            email.setText(currentPlayer.getEmail());
            points.setText(Integer.toString(currentPlayer.getPoints()));
        }



        return rootView;

    }
}
