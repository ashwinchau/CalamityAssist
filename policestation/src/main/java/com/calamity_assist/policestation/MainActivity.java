package com.calamity_assist.policestation;

/**
 * Created by AshwinChauhan on 4/7/2017.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.calamity_assist.policestation.Adpater.*;
import com.calamity_assist.policestation.Fragment.Appointment;
import com.calamity_assist.policestation.Fragment.Update_Details;

public class MainActivity extends AppCompatActivity{

   /* Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    viewPagerAdpater viewPagerAdpater;

    */protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar=(Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // toolbar.setTitle("Hospital");

        /*tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPagerAdpater=new viewPagerAdpater(getSupportFragmentManager());
        viewPagerAdpater.addFragment(new Appointment(),"Appointment");
        viewPagerAdpater.addFragment(new Update_Details(),"Profile");


        viewPager.setAdapter(viewPagerAdpater);
        tabLayout.setupWithViewPager(viewPager);
*/
    }

}
