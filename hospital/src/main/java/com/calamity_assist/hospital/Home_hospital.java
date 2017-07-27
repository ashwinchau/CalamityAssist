package com.calamity_assist.hospital;

import com.calamity_assist.hospital.Adpater.viewPagerAdpater;
import com.calamity_assist.hospital.Constant.SessionManager;
import com.calamity_assist.hospital.Fragment.Appointment;
import com.calamity_assist.hospital.Fragment.Update_Details;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.widget.Toast;


public class Home_hospital extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    viewPagerAdpater viewPagerAdpater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_hospital);

       // toolbar=(Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
       // toolbar.setTitle("Hospital");

        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPagerAdpater=new viewPagerAdpater(getSupportFragmentManager());
        viewPagerAdpater.addFragment(new Appointment(),"Appointment");
        viewPagerAdpater.addFragment(new Update_Details(),"Profile");


        viewPager.setAdapter(viewPagerAdpater);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(Home_hospital.this, Home_hospital.class);  //your class
        startActivity(i);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logout_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.logout)
        {
            SessionManager.getInstance().setID("");
            startActivity(new Intent(Home_hospital.this,login_hospital.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

            backButtonHandler();
    }

    private void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home_hospital.this);
        // Setting Dialog Title
        alertDialog.setTitle(R.string.app_name);
        // Setting Dialog Message
        alertDialog.setMessage("Are You Want To Quit?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.logo);
        final Intent i=new Intent(Home_hospital.this,login_hospital.class);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //System.exit(0);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("Exit me", true);
                        finish();
                        startActivity(i);
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();

    }

}
