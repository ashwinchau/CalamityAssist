package com.calamity_assist.calamityassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.calamity_assist.calamityassist.Constant.SessionManager;

public class Splash_Screen extends Activity {

    SessionManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sm = new SessionManager(this);

        setContentView(R.layout.activity_splash__screen);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sm.getID().length() > 0){
                    startActivity(new Intent(Splash_Screen.this,MainActivity.class));

                   // Toast.makeText(Splash_Screen.this,"Welcome  " + sm.getImg(), Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(Splash_Screen.this, Introsilder.class));
                }
                finish();
            }
        },3000);
    }
}
