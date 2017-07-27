package com.calamity_assist.calamityassist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Change_Password extends Activity {

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_submit)
    public void tryChangePassword()
    {
        startActivity(new Intent(this,login_smart.class));
    }


}
