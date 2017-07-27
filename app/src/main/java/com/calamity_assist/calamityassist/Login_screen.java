package com.calamity_assist.calamityassist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class Login_screen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_screen);
    }

    public void LoginMethod(View view)
    {
            startActivity(new Intent(Login_screen.this,login_smart.class));
    }

    public void Sign_up(View view){ startActivity(new Intent(Login_screen.this,Sign_up_Screen.class));}

}
