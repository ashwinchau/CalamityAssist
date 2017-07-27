package com.calamity_assist.calamityassist.hopital;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.calamity_assist.calamityassist.Constant.SessionManager;
import com.calamity_assist.calamityassist.Framgment_drawn.getUserDetails;
import com.calamity_assist.calamityassist.MainActivity;
import com.calamity_assist.calamityassist.R;

import java.util.ArrayList;

/**
 * Created by AshwinChauhan on 3/30/2017.
 */

public class hospitalCard_adapter extends RecyclerView.Adapter<hospitalCard_adapter.viewHolder> {


    ArrayList<getUserDetails> arrayList;

    Context context;

    Appointment appointment;

    SessionManager sm;

    getUserDetails user;

    public hospitalCard_adapter(Context context,ArrayList<getUserDetails> arrayList){

        this.arrayList=arrayList;
        this.context=context;


    }


    @Override
    public hospitalCard_adapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.hospitalcard,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(final hospitalCard_adapter.viewHolder holder, int i) {

//        Toast.makeText(context, holder.c_id.getText().toString(), Toast.LENGTH_SHORT).show();
        holder.c_id.setText(arrayList.get(i).getc_id());
        holder.c_name.setText(arrayList.get(i).getc_name());
        holder.c_email.setText(arrayList.get(i).getc_email());
        holder.c_add.setText(arrayList.get(i).getc_add());
        holder.c_website.setText(arrayList.get(i).getc_website());
        holder.c_phone.setText(arrayList.get(i).getc_phone());


        holder.c_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // user.getc_id();
              //  Toast.makeText(context,holder.c_id.getText().toString(), Toast.LENGTH_SHORT).show();
                AppCompatActivity appCompatActivity=(AppCompatActivity)v.getContext();
                Particuler_detail particuler_detail=new Particuler_detail();
                Bundle bundle=new Bundle();
                bundle.putString("h_id",holder.c_id.getText().toString());
                particuler_detail.setArguments(bundle);
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main,particuler_detail).addToBackStack(null).commit();



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

        public viewHolder(View itemView) {

              super(itemView);
            c_id=(TextView) itemView.findViewById(R.id.h_id);
            c_name=(TextView) itemView.findViewById(R.id.h_name);
            c_email=(TextView)itemView.findViewById(R.id.h_email);
            c_add=(TextView)itemView.findViewById(R.id.h_add);
            c_phone=(TextView)itemView.findViewById(R.id.h_phone);
            c_website=(TextView)itemView.findViewById(R.id.h_website);

        }
    }
}
