package com.jorgebaralt.athlete_mindful_app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jorgebaraltq on 3/10/2017.
 */

public class Leaderboard extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflate the fragment into the navigation drawer
        return inflater.inflate(R.layout.leaderboard_layout,container,false);
    }
}