package com.jorgebaralt.athlete_mindful_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jorgebaralt.athlete_mindful_app.API.ApiInterface;
import com.jorgebaralt.athlete_mindful_app.Chat.ChatActivity;
import com.jorgebaralt.athlete_mindful_app.settings.SettingsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //to store player coming from login
    private Player currentPlayer;
    final static String BASE_URL = "http://postgresql-env.8ts8eznn5d.us-east-1.elasticbeanstalk.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //set name of the user on the navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView navName = (TextView) header.findViewById(R.id.navigationName);
        TextView navEmail = (TextView) header.findViewById(R.id.navigationEmail);

        //get player from login
        currentPlayer = (Player) NavigationDrawer.this.getIntent().getSerializableExtra("currentPlayer");
        if(currentPlayer != null) {
            Log.d(TAG, "NavigationDrawer, Player Logged: " + currentPlayer.getName());
            //set navigation drawer name and email

            navName.setText(currentPlayer.getName());
            navEmail.setText(currentPlayer.getEmail());


        }
        //when they are logged in, go directly to their profile fragment.
        displaySelectedScreen(R.id.nav_profile);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.menu_settings:
                Intent settingsIntent = new Intent(this,SettingsActivity.class);
                settingsIntent.putExtra("currentPlayer",currentPlayer);
                startActivity(settingsIntent);
                break;
            case R.id.menu_logout:
                Log.d(TAG, "onOptionsItemSelected: " + currentPlayer.getName() + " logging out");
                //send token to logout on backend
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();

                ApiInterface apiInterface = retrofit.create(ApiInterface.class);

                Call<Player> logoutCall = apiInterface.logout(currentPlayer.getToken());
                logoutCall.enqueue(new Callback<Player>() {
                    @Override
                    public void onResponse(Call<Player> call, Response<Player> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(NavigationDrawer.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                            //go to login activity
                            Intent loginIntent = new Intent(NavigationDrawer.this,LoginActivity.class);
                            finish();
                            startActivity(loginIntent);
                        }else{
                            Toast.makeText(NavigationDrawer.this, "Error Logging Out...", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: Error Logging Out... = " + response);
                        }
                    }

                    @Override
                    public void onFailure(Call<Player> call, Throwable t) {

                    }
                });





                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        //Select the ID clicked from Nav-Draw.
        int id = item.getItemId();

        //Function that changes between fragments
        displaySelectedScreen(id);

        return true;
    }
    private void displaySelectedScreen(int id){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Fragment fragment = null;
        switch(id){
            case R.id.nav_survey:
                //starting new activity
                Intent surveyIntent = new Intent(this,SurveyActivity.class);
                surveyIntent.putExtra("currentPlayer", currentPlayer);
                startActivity(surveyIntent);
                break;
            case R.id.nav_profile:
                toolbar.setTitle("Profile");
                fragment = new Profile();
                break;
            case R.id.nav_leaderboard:
                toolbar.setTitle("LeaderBoard");
                fragment = new LeaderboardFragment();
                break;
            case R.id.nav_chat:
                toolbar.setTitle("Chat with Coach");
                //start chat activity
                Intent chatIntent = new Intent(this, ChatActivity.class);
                chatIntent.putExtra("currentPlayer",currentPlayer);
                startActivity(chatIntent);
                break;
            case R.id.nav_notification:
                toolbar.setTitle("Notifications");
                fragment = new Fragment();
        }
        if(fragment!=null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    //get Player from child Activity


}
