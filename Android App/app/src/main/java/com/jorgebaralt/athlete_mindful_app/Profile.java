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

public class Profile extends Fragment{

    Player currentPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.profile_layout,container,false);


        return rootView;

    }
}
