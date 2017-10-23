package com.jorgebaralt.athlete_mindful_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jorgebaralt.athlete_mindful_app.Adapters.CategoryAdapter;

import static android.content.ContentValues.TAG;

public class SurveyActivity extends AppCompatActivity  {

    private Player currentPlayer;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        currentPlayer = (Player) SurveyActivity.this.getIntent().getSerializableExtra("currentPlayer");
        if(currentPlayer != null) {
            Log.d(TAG, "NavigationDrawer, Player Logged: " + currentPlayer.getName());

        }

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page
        // HERE IS WHERE WE DECIDE HOW MANY TABS.
        CategoryAdapter adapter = new CategoryAdapter(getSupportFragmentManager());
        // Set the adapter onto the view pager
        // so it sets up the swapping
        viewPager.setAdapter(adapter);
        // Find the tab layout that shows the tabs.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        Intent intent = new Intent();
        intent.putExtra("currentPlayer",currentPlayer);
        setResult(RESULT_OK,intent);



    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = new Intent(this,NavigationDrawer.class);
        intent.putExtra("currentPlayer", currentPlayer);
        setResult(RESULT_OK,intent);
        return intent;
    }

}
