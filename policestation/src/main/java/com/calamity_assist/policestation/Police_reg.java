package com.calamity_assist.policestation;

import android.app.Activity;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Police_reg extends Activity implements Validator.ValidationListener {


    @NotEmpty
    EditText policestation_name;

    @Email(message = "example@domain.com")
    EditText police_emailid;

    @NotEmpty
    EditText policestation_address;

    @Length(min = 10)
    EditText police_phone;

    @Url(message = "www.localhost.com")
    EditText police_website;

    @Password(min=6,scheme = Password.Scheme.ANY,message = "Must Provide by the Alphabet,Number,SymBol")
    EditText police_password;


    Validator validator;

    String poli_name;
    String poli_email;
    String poli_address;
    String poli_phone;
    String poli_website;
    String poli_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_reg);


        policestation_name=(EditText)findViewById(R.id.police_station_name);
        police_emailid=(EditText)findViewById(R.id.police_reg_email);
        policestation_address=(EditText)findViewById(R.id.police_location);
        police_phone=(EditText)findViewById(R.id.police_phone);
        police_website=(EditText)findViewById(R.id.police_website);
        police_password=(EditText)findViewById(R.id.police_reg_password);


        validator=new Validator(this);
        validator.setValidationListener(this);

    }


    public void reg(View view)
    {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        police_reg();
    }

    private void police_reg() {

        poli_name=policestation_name.getText().toString().trim();
        poli_email=police_emailid.getText().toString().trim();
        poli_address=policestation_address.getText().toString().trim();
        poli_phone=police_phone.getText().toString().trim();
        poli_website=police_website.getText().toString().trim();
        poli_password=police_password.getText().toString().trim();

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
                            Toast.makeText(Police_reg.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Police_reg.this, login_police.class));
                        }
                    }
                    else
                    {
                        Toast.makeText(Police_reg.this, "Invalid Data", Toast.LENGTH_LONG).show();

                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Police_reg.this, error.toString() , Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String,String>();
                params.put(config.Police_name,poli_name);
                params.put(config.Police_email,poli_email);
                params.put(config.Police_address,poli_address);
                params.put(config.Police_phone,poli_phone);
                params.put(config.Police_website,poli_website);
                params.put(config.Police_password,poli_password);
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
