package com.calamity_assist.calamityassist.Framgment_drawn;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.calamity_assist.calamityassist.R;
import com.calamity_assist.calamityassist.hopital.Appointment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class auto_mobile_particular_details extends Fragment {


    public String Url="http://192.168.43.40/calamity/auto_mobile_detail.php";
    String value;
    String hos_id;

    getUserDetails userData;

    TextView name,address,website,phone,email;

    Button btn_auto;

    public auto_mobile_particular_details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_auto_mobile_particular_details, container, false);

        name=(TextView)view.findViewById(R.id.printautoname);
        address=(TextView)view.findViewById(R.id.printautoaddress);
        website=(TextView)view.findViewById(R.id.printautowebsite);
        phone=(TextView)view.findViewById(R.id.printautophone);
        email=(TextView)view.findViewById(R.id.printautoemail);
        btn_auto=(Button)view.findViewById(R.id.appointmentauto);



        userData=new getUserDetails();
        name.setText(userData.getc_name());
        email.setText(userData.getc_email());
        address.setText(userData.getc_add());
        website.setText(userData.getc_website());
        phone.setText(userData.getc_phone());

        hos_id=userData.getc_id();


        value=getArguments().getString("auto_id");

        Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();

        btn_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity=(AppCompatActivity)v.getContext();
                AutoMobile_fragment appointment=new AutoMobile_fragment();
                Bundle bundle=new Bundle();
                bundle.putString("auto_id",hos_id);
                appointment.setArguments(bundle);
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main,appointment).addToBackStack(null).commit();

            }
        });


        new autoDetail().execute();
        return view;
    }

    class autoDetail extends AsyncTask<String, String, String> {


        ProgressDialog pg;

        @Override
        protected void onPreExecute() {
            pg = new ProgressDialog(getActivity());
            pg.setMessage("Loading");
            pg.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        JSONObject j = new JSONObject(response);
                        if (j.getString("Status").equalsIgnoreCase("True")) {
                            if (j.getString("Message").equalsIgnoreCase("Detail")) {
                                Log.v("Message", j.getString("Message"));
                                JSONArray jsonArray=j.getJSONArray("response");
                                for(int i=0;i<jsonArray.length();i++) {
                                    JSONObject object=jsonArray.getJSONObject(i);

                                    userData.setc_id(object.getString("auto_id"));
                                    hos_id=userData.getc_id();
                                    userData.setc_name(object.getString("auto_c_name"));
                                    name.setText(userData.getc_name());
                                    userData.setc_email(object.getString("auto_c_email_id"));
                                    email.setText(userData.getc_email());
                                    userData.setc_add(object.getString("auto_c_address"));
                                    address.setText(userData.getc_add());
                                    userData.setc_phone(object.getString("auto_c_phone"));
                                    phone.setText(userData.getc_phone());
                                    userData.setc_website(object.getString("auto_c_website"));
                                    website.setText(userData.getc_website());

                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), "Invalid Email ID Or Password", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    /*params.put(u_email_id,email_id);
                    params.put(password,ed_password);*/
                    params.put("auto_id",value);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            pg.dismiss();
            super.onPostExecute(s);
        }
    }



}
