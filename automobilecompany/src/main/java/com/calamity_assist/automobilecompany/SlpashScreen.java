package com.calamity_assist.automobilecompany;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.calamity_assist.automobilecompany.Constant.SessionManager;

public class SlpashScreen extends Activity {

    SessionManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_slpash_screen);

        sm = new SessionManager(this);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sm.getID().length() > 0){
                    startActivity(new Intent(SlpashScreen.this,automobile_home.class));

                    // Toast.makeText(Splash_Screen.this,"Welcome  " + sm.getImg(), Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(SlpashScreen.this, login.class));
                }
                finish();
            }
        },3000);
    }
}
