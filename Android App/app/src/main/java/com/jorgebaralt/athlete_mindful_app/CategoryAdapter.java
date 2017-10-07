package com.jorgebaralt.athlete_mindful_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jorgebaraltq on 5/23/2017.
 */

public class CategoryAdapter extends FragmentPagerAdapter {


    public CategoryAdapter(FragmentManager fm) {
        super(fm);

    }
    //The different layouts that we can swipe.
    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new MentalFragment();
        else if(position==1)
            return new EmotionalFragment();
        else if(position==2)
            return new PhysicalFragment();
        else if(position==3)
            return new SocialFragment();
        else if(position==4)
            return new TrainingFragment();
        else
            return new CompetitionFragment();

    }

    //change name of each tab.
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Mental";
        else if(position==1)
            return "Emotional";
        else if(position==2)
            return "Physical";
        else if(position==3)
            return "Social";
        else if(position==4)
            return "Training";
        else
            return "Competition";
        }

    //number of tabs on the survey layout.
    @Override
    public int getCount() {
        return 6;
    }

}
