package com.calamity_assist.hospital;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.calamity_assist.hospital.Constant.SessionManager;

public class Splash_screen extends Activity {

    SessionManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sm = new SessionManager(this);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sm.getID().length() > 0){
                    startActivity(new Intent(Splash_screen.this,Home_hospital.class));

                    // Toast.makeText(Splash_Screen.this,"Welcome  " + sm.getImg(), Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(Splash_screen.this, login_hospital.class));
                }
                finish();
            }
        },3000);
    }
}
