package com.calamity_assist.calamityassist.police;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.calamity_assist.calamityassist.Framgment_drawn.AutomobileCard_adapter;
import com.calamity_assist.calamityassist.Framgment_drawn.getUserDetails;
import com.calamity_assist.calamityassist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Police_station_card_recycle extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList arrayList;
    Button btn;


    public String Url="http://192.168.43.40/calamity/police_card.php";


    public Police_station_card_recycle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_police_station_card_recycle, container, false);
        View v=inflater.inflate(R.layout.policecard,null);

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_police);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        new police_recycle().execute();

        return view;
    }

    public class police_recycle extends AsyncTask<String,String,String> {

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
                                userData.setc_id(object.getString("police_id"));
                                userData.setc_name(object.getString("police_name"));
                                userData.setc_email(object.getString("police_email"));
                                userData.setc_add(object.getString("police_address"));
                                userData.setc_phone(object.getString("police_phone"));
                                userData.setc_website(object.getString("police_website"));
                                arrayList.add(userData);
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapter=new PoliceCard_adapter(arrayList);
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
