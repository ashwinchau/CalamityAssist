package com.calamity_assist.automobilecompany.Fragment;


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
import com.calamity_assist.automobilecompany.Adpater.AutoCard_adapter;
import com.calamity_assist.automobilecompany.Adpater.getUserDetails;
import com.calamity_assist.automobilecompany.Constant.SessionManager;
import com.calamity_assist.automobilecompany.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Complant extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList arrayList;
    SessionManager sm;
    String userId;

    CircleImageView imageView;

    public String url="http://192.168.43.40/calamity/AutoMobile/AutoMobile.php";


    public Complant() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_complant, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        sm = new SessionManager(getActivity());
        userId= sm.getID();
        new autoMobile().execute();
        return view;
    }

    public class autoMobile extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {

            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject=new JSONObject(response);

                        if(jsonObject.getString("Status").equalsIgnoreCase("True")){

                            JSONArray jsonArray =jsonObject.optJSONArray("response");

                            arrayList=new ArrayList<>();
                           Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                           for(int i=0;i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                getUserDetails userData=new getUserDetails();
                                userData.setc_name(object.getString("user_name"));
                                userData.setc_model(object.getString("vehicle_model"));
                                userData.setc_style(object.getString("vehicle_style"));
                                userData.setc_color(object.getString("vehicle_color"));
                               userData.setc_plat(object.getString("vehicle_plat_no"));
                               userData.setc_img(object.getString("profile_photo"));
                               userData.setc_email(object.getString("u_email_id"));
                               userData.setc_phone(object.getString("u_email_id"));

                               arrayList.add(userData);
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapter=new AutoCard_adapter(getActivity(),arrayList);
                    recyclerView.setAdapter(adapter);

                }
            },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("auto_id",userId);
                    return params;
                }
            }                    ;

            RequestQueue rq= Volley.newRequestQueue(getActivity());
            rq.add(stringRequest);

            return null;
        }
    }

}
