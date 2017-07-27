package com.calamity_assist.policestation;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.calamity_assist.policestation.Constant.SessionManager;

public class Splash_Screen extends Activity {
    SessionManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        sm = new SessionManager(this);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sm.getID().length() > 0){
                    startActivity(new Intent(Splash_Screen.this,Home_police.class));

                     Toast.makeText(Splash_Screen.this,"Welcome  " + sm.getID(), Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(Splash_Screen.this, login_police.class));
                }
                finish();
            }
        },3000);
    }
}
