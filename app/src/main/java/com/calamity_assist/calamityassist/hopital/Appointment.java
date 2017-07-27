package com.calamity_assist.calamityassist.hopital;


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
import com.calamity_assist.calamityassist.MainActivity;
import com.calamity_assist.calamityassist.R;
import com.calamity_assist.calamityassist.login_smart;
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
public class Appointment extends Fragment implements Validator.ValidationListener, View.OnClickListener {

    String value,u_id;
    SessionManager sm;


    String url="http://192.168.43.40/calamity/appointment_details.php";
    String names="user_name";
    String addresss="user_address";
    String phones="user_phone";

    @NotEmpty
    EditText edt_name;

    @NotEmpty(message = "Enter the 10 Digite Number !")
    EditText edt_phone;

    @NotEmpty(message = "Enter The Address ")
    EditText edt_address;

    Validator validator;

    Button btn_appoinment;


    String name,address,phone;

    public Appointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_appointment, container, false);

        edt_name=(EditText)view.findViewById(R.id.a_name);
        edt_phone=(EditText)view.findViewById(R.id.a_phone);
        edt_address=(EditText)view.findViewById(R.id.a_address);


        btn_appoinment=(Button) view.findViewById(R.id.a_appointment);


        validator=new Validator(this);
        validator.setValidationListener(this);

        value=getArguments().getString("hos_id");
        sm=new SessionManager(getActivity());
        u_id=sm.getID();


        btn_appoinment.setOnClickListener(this);

        return view;
    }


    @Override
    public void onValidationSucceeded() {

        new appont().execute();

    }

    class appont extends AsyncTask<String,String,String> {

    final String name=edt_name.getText().toString().trim();
    final String address=edt_address.getText().toString().trim();
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
                            if(jsonObject.getString("message").equalsIgnoreCase("Hospital report generated successfully"))
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
                    params.put("hospital_id",value);
                    params.put("user_id",u_id);
                    params.put(names,name);
                    params.put(phones,phone);
                    params.put(addresss,address);
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
    public void onClick(View v) {

        validator.validate();
    }
}
