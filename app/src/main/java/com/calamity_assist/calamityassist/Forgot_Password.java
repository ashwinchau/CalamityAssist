package com.calamity_assist.calamityassist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Forgot_Password extends Activity {

    @BindView(R.id.btn_Password)
    Button btn_Forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_Password)
    public void tryForgot()
    {
        startActivity(new Intent(this,otp_verify.class))

        ;}




}
