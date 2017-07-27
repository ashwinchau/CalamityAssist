package com.calamity_assist.policestation.Adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by AshwinChauhan on 3/4/2017.
 */

public class viewPagerAdpater extends FragmentPagerAdapter {


    //Array creat by Fragment
    ArrayList<Fragment> fragments=new ArrayList<>();

    //Array create by String to Store by the Tab view
    ArrayList<String> tabTitle=new ArrayList<>();

    public void addFragment(Fragment fragments,String titles)
    {
        this.fragments.add(fragments);
        this.tabTitle.add(titles);

    }
    public viewPagerAdpater(FragmentManager fm)
    {

        super(fm);
    }
    @Override
    public Fragment getItem(int position) {


        return fragments.get(position);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position);
    }
}
