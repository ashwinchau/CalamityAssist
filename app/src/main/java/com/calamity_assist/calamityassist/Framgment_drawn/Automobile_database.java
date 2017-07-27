package com.calamity_assist.calamityassist.Framgment_drawn;


import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.calamity_assist.calamityassist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Automobile_database extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList arrayList;

    public String Url="http://192.168.43.40/calamity/auto_card.php";


    public Automobile_database() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_automobile_database, container, false);

        recyclerView=(RecyclerView)v.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        new autoMobile().execute();

        return v;
    }

    public class autoMobile extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            StringRequest stringRequest=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);


                        if(jsonObject.getString("Status").equalsIgnoreCase("True")){

                            JSONArray jsonArray =jsonObject.optJSONArray("response");

                            arrayList=new ArrayList<>();

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                getUserDetails userData=new getUserDetails();

                                userData.setc_id(object.getString("auto_id"));
                                userData.setc_name(object.getString("auto_c_name"));
                                userData.setc_email(object.getString("auto_c_email_id"));
                                userData.setc_add(object.getString("auto_c_address"));
                                userData.setc_phone(object.getString("auto_c_phone"));
                                userData.setc_website(object.getString("auto_c_website"));
                                arrayList.add(userData);
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapter=new AutomobileCard_adapter(arrayList);
                    recyclerView.setAdapter(adapter);

    }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            )
                    ;

            RequestQueue rq= Volley.newRequestQueue(getActivity());
            rq.add(stringRequest);

            return null;
        }
    }

}
