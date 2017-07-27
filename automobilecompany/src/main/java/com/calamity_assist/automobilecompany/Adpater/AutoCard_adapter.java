package com.calamity_assist.automobilecompany.Adpater;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.calamity_assist.automobilecompany.Constant.Constant;
import com.calamity_assist.automobilecompany.R;

import com.calamity_assist.automobilecompany.UserDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by AshwinChauhan on 3/30/2017.
 */

public class AutoCard_adapter extends RecyclerView.Adapter<AutoCard_adapter.viewHolder> {


    ArrayList<getUserDetails> arrayList;

    Context context;

    getUserDetails user;

    public AutoCard_adapter(Context context, ArrayList<getUserDetails> arrayList){

        this.arrayList=arrayList;
        this.context=context;


    }



    @Override
    public AutoCard_adapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AutoCard_adapter.viewHolder holder,final int i) {

//        Toast.makeText(context, holder.c_id.getText().toString(), Toast.LENGTH_SHORT).show();
      //  holder.c_id.setText(arrayList.get(i).getc_id());
        holder.c_name.setText(arrayList.get(i).getc_name());

       // Toast.makeText(context,Constant.ImagePath , Toast.LENGTH_SHORT).show();

        arrayList.get(i).getc_model();
        arrayList.get(i).getc_style();
        arrayList.get(i).getc_color();
        arrayList.get(i).getc_email();
        arrayList.get(i).getc_phone();
        arrayList.get(i).getc_plat();

        Toast.makeText(context,Constant.ImagePath+arrayList.get(i).getc_img() , Toast.LENGTH_SHORT).show();
        Picasso.with(context).load(Constant.ImagePath+arrayList.get(i).getc_img()).into(holder.imageView);


        holder.c_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, UserDetails.class);
                intent.putExtra("name",arrayList.get(i).getc_name());
                intent.putExtra("model", arrayList.get(i).getc_model());
                intent.putExtra("style", arrayList.get(i).getc_style());
                intent.putExtra("color", arrayList.get(i).getc_color());
                intent.putExtra("emial", arrayList.get(i).getc_email());
                intent.putExtra("plat", arrayList.get(i).getc_plat());
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
         /*  c_email=(TextView)itemView.findViewById(R.id.c_email);
            c_add=(TextView)itemView.findViewById(R.id.c_add);
           // c_phone=(TextView)itemView.findViewById(R.id.c_phone);
            //c_website=(TextView)itemView.findViewById(R.id.c_website);*/
           imageView=(CircleImageView)itemView.findViewById(R.id.imageView_drawer2);
        }
    }
}
