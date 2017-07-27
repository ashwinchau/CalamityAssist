package com.calamity_assist.hospital.Fragment;


import android.content.Intent;
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
import com.calamity_assist.hospital.Constant.SessionManager;
import com.calamity_assist.hospital.Home_hospital;
import com.calamity_assist.hospital.Hospital_Reg;
import com.calamity_assist.hospital.R;
import com.calamity_assist.hospital.config;
import com.calamity_assist.hospital.login_hospital;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Update_Details extends Fragment implements Validator.ValidationListener {

    SessionManager sm;
    @NotEmpty
    EditText edt_name;

    @Email(message = "example@domain.com")
    EditText edt_email;

    @NotEmpty
    EditText edt_address;

    @Length(min = 10)
    EditText edt_phone;

    @Url(message = "http://www.localhost.com")
    EditText edt_website;


    Validator validator;


    String hos_name;
    String hos_email;
    String hos_address;
    String hos_phone;
    String hos_website;
    String hos_id;


    Button btn_update;
    public Update_Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_update__details, container, false);

        btn_update=(Button)view.findViewById(R.id.update);
        edt_name=(EditText)view.findViewById(R.id.update_name);
        edt_email=(EditText)view.findViewById(R.id.update_email);
        edt_address=(EditText)view.findViewById(R.id.update_address);
        edt_phone=(EditText)view.findViewById(R.id.update_phone);
        edt_website=(EditText)view.findViewById(R.id.update_website);

        sm=new SessionManager(getActivity());

        hos_id=sm.getID();
        edt_name.setText(sm.getNames());
        edt_email.setText(sm.getEmail());
        edt_address.setText(sm.getAddress());
        edt_website.setText(sm.getwebsite());
        edt_phone.setText(sm.getPhone());

        validator=new Validator(this);
        validator.setValidationListener(this);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
        return view;
    }



    @Override
    public void onValidationSucceeded() {
        hospital_reg();
    }

    private void hospital_reg() {

        hos_name=edt_name.getText().toString().trim();
        hos_email=edt_email.getText().toString().trim();
        hos_address=edt_address.getText().toString().trim();
        hos_phone=edt_phone.getText().toString().trim();
        hos_website=edt_website.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("Status").equalsIgnoreCase("1"))
                    {
                        if(jsonObject.getString("message").equalsIgnoreCase("Update Sucessfully"))
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
                params.put(config.Hospital_id,hos_id);
                params.put(config.Hospital_name,hos_name);
                params.put(config.Hospital_email,hos_email);
                params.put(config.Hospital_address,hos_address);
                params.put(config.Hospital_phone,hos_phone);
                params.put(config.Hospital_website,hos_website);
                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


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

}
