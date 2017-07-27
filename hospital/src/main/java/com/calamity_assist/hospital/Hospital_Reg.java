package com.calamity_assist.hospital;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hospital_Reg extends Activity implements Validator.ValidationListener {

    @NotEmpty
    EditText hospital_name;

    @Email(message = "example@domain.com")
    EditText hospital_emailid;

    @NotEmpty
    EditText hospital_address;

    @Length(min = 10)
    EditText hospital_phone;

    @Url(message = "www.localhost.com")
    EditText hospital_website;

    @Password(min=6,scheme = Password.Scheme.ANY,message = "Must Provide by the Alphabet,Number,SymBol")
    EditText hospital_password;


    Validator validator;


    String hos_name;
    String hos_email;
    String hos_address;
    String hos_phone;
    String hos_website;
    String hos_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital__reg);


        hospital_name=(EditText)findViewById(R.id.hospital_reg_name);
        hospital_emailid=(EditText)findViewById(R.id.hospital_reg_email);
        hospital_address=(EditText)findViewById(R.id.hospital_reg_location);
        hospital_phone=(EditText)findViewById(R.id.hospital_reg_phone);
        hospital_website=(EditText)findViewById(R.id.hospital_reg_website);
        hospital_password=(EditText)findViewById(R.id.hospital_reg_password);




        validator=new Validator(this);
        validator.setValidationListener(this);


    }
    public void reg(View view)
    {
        //startActivity(new Intent(Hospital_Reg.this,login_hospital.class));
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        hospital_reg();
    }

    private void hospital_reg() {

        hos_name=hospital_name.getText().toString().trim();
        hos_email=hospital_emailid.getText().toString().trim();
        hos_address=hospital_address.getText().toString().trim();
        hos_phone=hospital_phone.getText().toString().trim();
        hos_website=hospital_website.getText().toString().trim();
        hos_password=hospital_password.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, config.insert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("Status").equalsIgnoreCase("1"))
                    {
                        if(jsonObject.getString("message").equalsIgnoreCase("Sign up Sucessfully"))
                        {
                            Log.v("message", jsonObject.getString("message"));
                            Toast.makeText(Hospital_Reg.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Hospital_Reg.this, login_hospital.class));
                        }
                    }
                    else
                    {
                        Toast.makeText(Hospital_Reg.this, "Invalid Data", Toast.LENGTH_LONG).show();

                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Hospital_Reg.this, error.toString() , Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String,String>();
                params.put(config.Hospital_name,hos_name);
                params.put(config.Hospital_email,hos_email);
                params.put(config.Hospital_address,hos_address);
                params.put(config.Hospital_phone,hos_phone);
                params.put(config.Hospital_website,hos_website);
                params.put(config.Hospital_password,hos_password);
                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors)
        {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText)
            {
                ((EditText) view).setError(message);
            } else
            {
                Toast.makeText(this,"", Toast.LENGTH_LONG).show();
            }
        }

    }
}
