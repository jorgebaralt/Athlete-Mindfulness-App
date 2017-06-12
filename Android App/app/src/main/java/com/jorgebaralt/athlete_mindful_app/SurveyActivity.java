package com.jorgebaralt.athlete_mindful_app;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

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


    }

}
