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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;

/**
 * Created by jorgebaraltq on 3/10/2017.
 */

public class LeaderboardFragment extends Fragment{

    //variables
    static final String players_url= "http://project-env-4.us-east-1.elasticbeanstalk.com/players";
    ArrayList<Players> player = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflate the specific fragment into the navigation drawer
        View rootView = inflater.inflate(R.layout.leaderboard_list,container,false);


        Log.d(TAG, "getTop10Player: STARTING JSON REQUEST FOR PLAYERS");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, players_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length();i++){
                    try {
                        //get player data
                        JSONObject currentPlayer = response.getJSONObject(i);
                        String firstname = currentPlayer.getString("first_name");
                        String lastname = currentPlayer.getString("last_name");
                        String playerName = firstname + " " + lastname;
                        int playerScore = currentPlayer.getInt("points");

                        //store player data into our array list
                        player.add(new Players(playerName,playerScore));

                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: Eror...");
                        e.printStackTrace();

                    }
                }
            //All work if the json worked:

                //sort players by score
                //TODO: do sorting on backend ?
                Collections.sort(player);


                //create the adapter who deals with each user view. custom adapter since we are doing it custom.
                LeaderboardAdapter adapter = new LeaderboardAdapter(getActivity(),player);
                //get the VIEW that is going to be filled
                ListView listView = (ListView) getActivity().findViewById(R.id.leaderboardList);
                //fill the VIEW.
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error fetching data", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e(TAG, "onErrorResponse: ",error );
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);



        return rootView;
    }


}
