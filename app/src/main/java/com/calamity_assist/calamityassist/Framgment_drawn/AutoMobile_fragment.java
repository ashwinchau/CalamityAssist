package com.calamity_assist.calamityassist.Framgment_drawn;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.calamity_assist.calamityassist.Constant.SessionManager;
import com.calamity_assist.calamityassist.GmapFragment;
import com.calamity_assist.calamityassist.MainActivity;
import com.calamity_assist.calamityassist.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AutoMobile_fragment extends Fragment implements View.OnClickListener {

    SessionManager sm;
    String u_id;
    String value;

    String url="http://192.168.43.40/calamity/auto_mobile_report.php";

    EditText edt_user,edt_vehicle_model,edt_vehicle_style,edt_vehicle_color,edt_vehicle_pat_no;


    Button btn_auto;

    public AutoMobile_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_auto_mobile_fragment, container, false);


        edt_user=(EditText)view.findViewById(R.id.user_name);
        edt_vehicle_color=(EditText)view.findViewById(R.id.vehicle_color);
        edt_vehicle_model=(EditText)view.findViewById(R.id.vehicle_model);
        edt_vehicle_style=(EditText)view.findViewById(R.id.vehicle_style);
        edt_vehicle_pat_no=(EditText)view.findViewById(R.id.vehicle_plat_number);
        btn_auto=(Button)view.findViewById(R.id.btn_auto_mobile);


        value=getArguments().getString("auto_id");
        sm=new SessionManager(getActivity());
        u_id=sm.getID();
        Toast.makeText(getActivity(), "User_id"+u_id, Toast.LENGTH_SHORT).show();

        Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();

        btn_auto.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        new appontt().execute();
    }

    class appontt extends AsyncTask<String,String,String> {

        final String name=edt_user.getText().toString().trim();
        final String vehicle_color=edt_vehicle_color.getText().toString().trim();
        final String vehicle_model=edt_vehicle_model.getText().toString().trim();
        final String vehicle_style=edt_vehicle_style.getText().toString().trim();
        final String vehicle_plat=edt_vehicle_pat_no.getText().toString().trim();

        ProgressDialog pg;

        @Override
        protected void onPreExecute() {
            pg = new ProgressDialog(getActivity());
            pg.setMessage("Loading");
            pg.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try
                    {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.getString("Status").equalsIgnoreCase("1"))
                        {
                            if(jsonObject.getString("message").equalsIgnoreCase("AutoMobile Report Generated"))
                            {
                                Log.v("message", jsonObject.getString("message"));
                                Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Invalid Data", Toast.LENGTH_LONG).show();

                        }

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.toString() , Toast.LENGTH_SHORT).show();

                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params=new HashMap<String,String>();
                    params.put("auto_id",value);
                    params.put("u_id",u_id);
                    params.put("user_name",name);
                    params.put("vehicle_model",vehicle_model);
                    params.put("vehicle_style",vehicle_style);
                    params.put("vehicle_color",vehicle_color);
                    params.put("vehicle_plat_no",vehicle_plat);
                    return params;
                }

            };
            RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
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
