package com.jorgebaralt.athlete_mindful_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.API.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
            //points.setText(Integer.toString(currentPlayer.getPoints()));

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(ApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            Call<Player> call = apiInterface.getPlayer((currentPlayer.getId()));
            call.enqueue(new Callback<Player>() {
                @Override
                public void onResponse(Call<Player> call, Response<Player> response) {
                    if(response.isSuccessful()){
                        points.setText(Integer.toString(response.body().getPoints()));
                    }
                    else{
                        Log.e(TAG, "onResponse: Error getting current player");

                    }
                }

                @Override
                public void onFailure(Call<Player> call, Throwable t) {
                    Toast.makeText(getContext(), "Failure loading player" + t, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{

        }





        return rootView;

    }
}
