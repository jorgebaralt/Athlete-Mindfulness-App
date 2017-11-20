package com.jorgebaralt.athlete_mindful_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jorgebaralt.athlete_mindful_app.API.ApiInterface;
import com.jorgebaralt.athlete_mindful_app.Adapters.NotificationsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class NotificationsFragment extends Fragment {

    Player currentPlayer;
    ArrayList<Notifications> notificationList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notifications_list,container,false);
        Log.d(TAG, "onCreateView: Entering Notifications");

        //get current player
        Player currentPlayer = (Player) getArguments().getSerializable("currentPlayer");
        Log.d(TAG, "onCreateView: current player is =  " + currentPlayer.getName() );

        //create retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ArrayList<Notifications>> call = apiInterface.getPlayerNotifications(currentPlayer.getId());
        call.enqueue(new Callback<ArrayList<Notifications>>() {
            @Override
            public void onResponse(Call<ArrayList<Notifications>> call, Response<ArrayList<Notifications>> response) {
                if(response.isSuccessful()){
                    notificationList = response.body();
                    NotificationsAdapter notificationsAdapter = new NotificationsAdapter(getContext(),notificationList);
                    ListView listView = (ListView) getActivity().findViewById(R.id.notification_listview);
                    listView.setAdapter(notificationsAdapter);


                }
                else{
                    Log.e(TAG, "onResponse: error getting notifications" );
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Notifications>> call, Throwable t) {
                Log.e(TAG, "onFailure: Error connecting to notificaitons");
            }
        });

        return rootView;
    }
}
