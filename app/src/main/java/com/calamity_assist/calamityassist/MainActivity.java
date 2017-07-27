package com.calamity_assist.calamityassist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintDocumentAdapter;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.calamity_assist.calamityassist.Constant.Constant;
import com.calamity_assist.calamityassist.Constant.SessionManager;
import com.calamity_assist.calamityassist.Framgment_drawn.AutoMobile_fragment;
import com.calamity_assist.calamityassist.Framgment_drawn.Automobile_database;
import com.calamity_assist.calamityassist.hopital.Appointment;
import com.calamity_assist.calamityassist.hopital.hospital_recycle;
import com.calamity_assist.calamityassist.police.Police_station_card_recycle;
import com.calamity_assist.calamityassist.police.police_home;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
SupportMapFragment supportMapFragment;
TextView email_nav,user_nav;
CircleImageView imageView;
Fragment fragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment=new GmapFragment();

        if(fragment!=null)
        {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SessionManager sm=new SessionManager(this);
        View hView =  navigationView.getHeaderView(0);
        user_nav=(TextView)hView.findViewById(R.id.user_name_navigation);
        email_nav=(TextView)hView.findViewById(R.id.email_navigation);
        imageView=(CircleImageView)hView.findViewById(R.id.imageView_drawer);

        email_nav.setText(sm.getEmail());
        user_nav.setText(sm.getNames());

        String url=sm.getImg();
        Picasso.with(this).load(Constant.ImagePath+url).into(imageView);

   }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else
        {
            backButtonHandler();
        }

    }

    private void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle(R.string.app_name);
        // Setting Dialog Message
        alertDialog.setMessage("Are You Want To Quit?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.logo);
        final Intent i=new Intent(MainActivity.this,login_smart.class);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if(id==R.id.home_id){
            startActivity(new Intent(this,MainActivity.class));
        }
        else if (id == R.id.auto_mobile) {
            fragment=new Automobile_database();

        } else if (id == R.id.police_staion) {
            fragment=new Police_station_card_recycle();

        } else if (id == R.id.hospital_root) {
            fragment=new hospital_recycle();
           // fragment=new Appointment();

        } else if (id == R.id.blood_bank) {

        }else if(id == R.id.feedback){
            fragment=new FeedBack();

        }else if(id==R.id.nav_report){

        }else if(id==R.id.Logout)
        {
           SessionManager.getInstance().setID("");
            startActivity(new Intent(MainActivity.this,login_smart.class));
        }else if(id==R.id.profile)
        {
            fragment=new Profile();
        }
if(fragment!=null)
{
    FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.content_main,fragment);
    ft.commit();
}
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
