package com.jorgebaralt.athlete_mindful_app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jorgebaraltq on 3/10/2017.
 */

public class LeaderboardFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflate the specific fragment into the navigation drawer
        View rootView = inflater.inflate(R.layout.leaderboard_list,container,false);

        ArrayList<Student> user = new ArrayList<>();
        //Testing users into the arraylist.
        //TODO : get the top 10 students with their score from database.
        user.add(new Student("jorgebaralt",250));
        user.add(new Student("jorgebaralt1",200));
        user.add(new Student("jorgebaralt2",190));
        user.add(new Student("jorgebaralt3",180));
        user.add(new Student("jorgebaralt4",170));
        user.add(new Student("jorgebaralt5",150));
        user.add(new Student("jorgebaralt6",100));
        user.add(new Student("jorgebaralt7",50));
        user.add(new Student("jorgebaralt8",30));
        user.add(new Student("jorgebaralt9",0));

        //create the adapter who deals with each user view. custom adapter since we are doing it custom.
        LeaderboardAdapter adapter = new LeaderboardAdapter(getActivity(),user);
        //get the VIEW that is going to be filled
        ListView listView = (ListView) rootView.findViewById(R.id.leaderboardList);
        //fill the VIEW.
        listView.setAdapter(adapter);

        return rootView;
    }
}
