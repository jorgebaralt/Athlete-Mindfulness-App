package com.jorgebaralt.athlete_mindful_app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jorgebaraltq on 3/10/2017.
 */

public class LeaderboardFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflate the fragment into the navigation drawer
        View rootView = inflater.inflate(R.layout.leaderboard_list,container,false);

        ArrayList<Users> user = new ArrayList<>();
        user.add(new Users("jorgebaralt",250));
        user.add(new Users("jorgebaralt1",200));
        user.add(new Users("jorgebaralt2",190));
        user.add(new Users("jorgebaralt3",180));
        user.add(new Users("jorgebaralt4",170));
        user.add(new Users("jorgebaralt5",150));
        user.add(new Users("jorgebaralt6",100));
        user.add(new Users("jorgebaralt7",50));
        user.add(new Users("jorgebaralt8",30));
        user.add(new Users("jorgebaralt9",0));

        //create the adapter who deals with each user view.
        LeaderboardAdapter adapter = new LeaderboardAdapter(getActivity(),user);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        return rootView;
    }
}
