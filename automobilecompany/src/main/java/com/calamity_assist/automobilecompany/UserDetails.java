package com.calamity_assist.automobilecompany;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserDetails extends AppCompatActivity {

    TextView name,model,style,color,email,palt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        name=(TextView)findViewById(R.id.printuname);
        model=(TextView)findViewById(R.id.printmodel);
        style=(TextView)findViewById(R.id.printstyle);
        color=(TextView)findViewById(R.id.printcolor);
        email=(TextView)findViewById(R.id.printemail);
        palt=(TextView)findViewById(R.id.printpalt);

        name.setText(getIntent().getStringExtra("name"));
        model.setText(getIntent().getStringExtra("model"));
        style.setText(getIntent().getStringExtra("style"));
        color.setText(getIntent().getStringExtra("color"));
        email.setText(getIntent().getStringExtra("emial"));
        palt.setText(getIntent().getStringExtra("plat"));
    }
}
