package com.calamity_assist.calamityassist.police;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.calamity_assist.calamityassist.Framgment_drawn.getUserDetails;
import com.calamity_assist.calamityassist.R;
import com.calamity_assist.calamityassist.hopital.Particuler_detail;

import java.util.ArrayList;

/**
 * Created by AshwinChauhan on 3/30/2017.
 */

public class PoliceCard_adapter extends RecyclerView.Adapter<PoliceCard_adapter.viewHolder> {


    ArrayList<getUserDetails> arrayList;

    Context context;

    public PoliceCard_adapter(ArrayList<getUserDetails> arrayList){

        this.arrayList=arrayList;
    }


    @Override
    public PoliceCard_adapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.policecard,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PoliceCard_adapter.viewHolder holder, int i) {
        holder.c_id.setText(arrayList.get(i).getc_id());
        holder.c_name.setText(arrayList.get(i).getc_name());
        holder.c_email.setText(arrayList.get(i).getc_email());
        holder.c_add.setText(arrayList.get(i).getc_add());
        holder.c_website.setText(arrayList.get(i).getc_website());
        holder.c_phone.setText(arrayList.get(i).getc_phone());

        holder.c_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity=(AppCompatActivity)v.getContext();
                policesatation_details particuler_detail=new policesatation_details();
                Bundle bundle=new Bundle();
                bundle.putString("police_id",holder.c_id.getText().toString());
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
            c_id=(TextView) itemView.findViewById(R.id.police_id);
            c_name=(TextView) itemView.findViewById(R.id.p_name);
            c_email=(TextView)itemView.findViewById(R.id.p_email);
            c_add=(TextView)itemView.findViewById(R.id.p_add);
            c_phone=(TextView)itemView.findViewById(R.id.p_phone);
            c_website=(TextView)itemView.findViewById(R.id.p_website);

        }
    }
}
