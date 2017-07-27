package com.calamity_assist.calamityassist.hopital;


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
import com.calamity_assist.calamityassist.Framgment_drawn.getUserDetails;
import com.calamity_assist.calamityassist.R;
import com.calamity_assist.calamityassist.police.PoliceCard_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class hospital_recycle extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList arrayList;


    public String Url="http://192.168.43.40/calamity/hospitalcar.php";


    public hospital_recycle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hospital_recycle, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_hopital);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        new hospitals_recycle().execute();


        return view;
    }


    public class hospitals_recycle extends AsyncTask<String,String,String> {

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

                                userData.setc_id(object.getString("hospital_id"));
                                userData.setc_name(object.getString("hospital_name"));
                                userData.setc_email(object.getString("hospital_email"));
                                userData.setc_add(object.getString("hospital_address"));
                                userData.setc_phone(object.getString("hospital_phone"));
                                userData.setc_website(object.getString("hospital_website"));
                                arrayList.add(userData);
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapter=new hospitalCard_adapter(getActivity(),arrayList);
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
