package com.jorgebaralt.athlete_mindful_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.API.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by jorgebaraltq on 3/10/2017.
 */

public class LeaderboardFragment extends Fragment{

    //variables
    static final String BASE_URL = "http://project-env-4.us-east-1.elasticbeanstalk.com";
    ArrayList<Player> player = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflate the specific fragment into the navigation drawer
        View rootView = inflater.inflate(R.layout.leaderboard_list,container,false);


        Log.d(TAG, "getTop10Player: STARTING JSON REQUEST FOR PLAYERS");

        //create retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ArrayList<Player>> call = apiInterface.getLeaderboard();
        call.enqueue(new Callback<ArrayList<Player>>() {
            @Override
            public void onResponse(Call<ArrayList<Player>> call, Response<ArrayList<Player>> response) {
                player = response.body();
                //TODO: sort on backend?
                LeaderboardAdapter adapter = new LeaderboardAdapter(getActivity(),player);
                //get the VIEW that is going to be filled
                ListView listView = (ListView) getActivity().findViewById(R.id.leaderboardList);
                //fill the VIEW.
                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Player>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error displaying players", Toast.LENGTH_SHORT).show();

            }
        });


        return rootView;
    }


}
