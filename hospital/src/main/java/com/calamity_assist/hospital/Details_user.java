package com.calamity_assist.hospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Details_user extends AppCompatActivity {

    String name,add,mobile,Refer,img;
   TextView uname,uadd,amobile,uref,uimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_user);



       uname=(TextView) findViewById(R.id.printunam);
        uadd=(TextView) findViewById(R.id.printaddres);
        amobile=(TextView) findViewById(R.id.printphon);
        uref=(TextView) findViewById(R.id.printureferanc);

        
        name=getIntent().getStringExtra("name");
        add=getIntent().getStringExtra("add");
        mobile=getIntent().getStringExtra("phone");

        Refer=getIntent().getStringExtra("Refence");


        uname.setText(name);
        uadd.setText(add);
        amobile.setText(mobile);
        uref.setText(Refer);

        img=getIntent().getStringExtra("photo");
        //Toast.makeText(this, name+"add"+add+"mobile"+mobile+"refe"+Refer+"photo"+img, Toast.LENGTH_SHORT).show();
    }
}
