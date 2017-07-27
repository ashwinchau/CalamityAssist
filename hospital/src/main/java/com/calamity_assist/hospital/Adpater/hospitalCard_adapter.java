package com.calamity_assist.hospital.Adpater;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.calamity_assist.hospital.Constant.Constant;
import com.calamity_assist.hospital.Details_user;
import com.calamity_assist.hospital.Fragment.Details;
import com.calamity_assist.hospital.Home_hospital;
import com.calamity_assist.hospital.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.calamity_assist.hospital.R.id.imageView;

/**
 * Created by AshwinChauhan on 3/30/2017.
 */

public class hospitalCard_adapter extends RecyclerView.Adapter<hospitalCard_adapter.viewHolder> {


    ArrayList<getUserDetails> arrayList;

    Context context;

    getUserDetails user;
    CircleImageView imageView;
    public hospitalCard_adapter( Context context,ArrayList<getUserDetails> arrayList){

        this.arrayList=arrayList;
        this.context=context;


    }



    @Override
    public hospitalCard_adapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(final hospitalCard_adapter.viewHolder holder, final int i) {

//        Toast.makeText(context, holder.c_id.getText().toString(), Toast.LENGTH_SHORT).show();
      //  holder.c_id.setText(arrayList.get(i).getc_id());
        holder.c_name.setText(arrayList.get(i).getc_name());
        holder.c_add.setText(arrayList.get(i).getc_add());
        holder.c_email.setText(arrayList.get(i).getc_email());

        Picasso.with(context).load(Constant.ImagePath+arrayList.get(i).getc_img()).into(holder.imageView);

        holder.c_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /* AppCompatActivity appCompatActivity=(AppCompatActivity)v.getContext();
                Details details=new Details();
                Bundle bundle=new Bundle();
                bundle.putString("Name",holder.c_name.getText().toString());
                bundle.putString("phone",holder.c_add.getText().toString());
                bundle.putString("address",holder.c_email.getText().toString());
                details.setArguments(bundle);
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.activity_main,details).addToBackStack(null).commit();
*/
                Intent intent=new Intent(context, Details_user.class);
                intent.putExtra("name",arrayList.get(i).getc_name());
                intent.putExtra("add",arrayList.get(i).getc_add());
               intent.putExtra("phone", arrayList.get(i).getc_phone());
                intent.putExtra("Refence",arrayList.get(i).getc_email());
                intent.putExtra("photo",arrayList.get(i).getc_img());
                /*intent.putExtra("email",arrayList.get(i).getc_email());
                intent.putExtra("image",arrayList.get(i).getc_email());
*/
                //intent.putExtra("auto",arrayList.get(i).getc_name());
                AppCompatActivity appCompatActivity=(AppCompatActivity)v.getContext();
                appCompatActivity.startActivity(intent);
            }
        });
           }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder
    {

        TextView c_id,c_email,c_name,c_phone,c_website,c_add;

        CircleImageView imageView;

        public viewHolder(View itemView) {

              super(itemView);
        //    c_id=(TextView)itemView.findViewById(R.id.c_id);
            c_name=(TextView) itemView.findViewById(R.id.c_name);
           c_email=(TextView)itemView.findViewById(R.id.c_email);
            c_add=(TextView)itemView.findViewById(R.id.c_add);
           // c_phone=(TextView)itemView.findViewById(R.id.c_phone);
            //c_website=(TextView)itemView.findViewById(R.id.c_website);
           imageView=(CircleImageView)itemView.findViewById(R.id.imageView_drawer1);
        }
    }
}
