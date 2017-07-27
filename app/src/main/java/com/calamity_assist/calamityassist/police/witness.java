package com.calamity_assist.calamityassist.police;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.calamity_assist.calamityassist.MainActivity;
import com.calamity_assist.calamityassist.R;
import com.calamity_assist.calamityassist.hopital.Appointment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class witness extends Fragment implements Validator.ValidationListener, View.OnClickListener {


    String value,u_id;
    SessionManager sm;
    String url="http://192.168.43.40/calamity/witness_detail.php";

    @NotEmpty
    EditText edt_name;

    @NotEmpty(message = "Enter the 10 Digite Number !")
    EditText edt_phone;

    @NotEmpty(message = "Age")
    EditText edt_age;

    Validator validator;

    Button btn_witness;


    public witness() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_witness, container, false);


        edt_name=(EditText)view.findViewById(R.id.witness_name);
        edt_phone=(EditText)view.findViewById(R.id.witness_phone);
        edt_age=(EditText)view.findViewById(R.id.witness_age);
        btn_witness=(Button)view.findViewById(R.id.witness_report);

        validator=new Validator(this);
        validator.setValidationListener(this);

        btn_witness.setOnClickListener(this);

        value=getArguments().getString("police_id");
        sm=new SessionManager(getActivity());
        u_id=sm.getID();


        Toast.makeText(getActivity(),"police id"+value+"u_id"+u_id, Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onValidationSucceeded() {
        new appont().execute();
    }

    class appont extends AsyncTask<String,String,String> {

        final String name=edt_name.getText().toString().trim();
        final String age=edt_age.getText().toString().trim();
        final String phone=edt_phone.getText().toString().trim();
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
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.getString("Status").equalsIgnoreCase("1"))
                        {
                            if(jsonObject.getString("message").equalsIgnoreCase("Witness Fill Details!"))
                            {
                                Log.v("message", jsonObject.getString("message"));
                                Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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
                    params.put("u_id",u_id);
                    params.put("witness_name",name);
                    params.put("phone_no",phone);
                    params.put("age",age);


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

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors)
        {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText)
            {
                ((EditText) view).setError(message);
            } else
            {
                Toast.makeText(getActivity(),"", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        AppCompatActivity appCompatActivity=(AppCompatActivity)v.getContext();

        policestation_report policestation_report=new policestation_report();
        Bundle bundle=new Bundle();
        bundle.putString("h","h");
        policestation_report.setArguments(bundle);
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main,policestation_report).addToBackStack(null).commit();

        validator.validate();
    }
}
