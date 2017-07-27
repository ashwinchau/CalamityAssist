package com.calamity_assist.calamityassist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class otp_verify extends Activity {

    @BindView(R.id.btn_verify)
    Button btn_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_verify)
    public void tryVerify()
    {
        startActivity(new Intent(this,Change_Password.class));
    }
}
