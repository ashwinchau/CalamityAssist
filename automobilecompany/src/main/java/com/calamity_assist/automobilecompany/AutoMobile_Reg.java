package com.calamity_assist.automobilecompany;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


public class AutoMobile_Reg extends Activity implements Validator.ValidationListener {
    @NotEmpty
    EditText c_name;

    @Email(message = "example@domain.com")
    EditText c_emailid;

    @NotEmpty
    EditText c_address;

    @Length(min = 10)
    EditText c_phone;

    @Url(message = "www.localhost.com")
    EditText c_website;

    @Password(min=6,scheme = Password.Scheme.ANY,message = "Must Provide by the Alphabet,Number,SymBol")
    EditText c_password;

    Button auto_sign_up;

    Validator validator;

    public static final String insert="http://192.168.43.40/calamity/auto_reg.php";
    public static final String company_name="c_name";
    public static final String c_email="c_emailid";
    public static final String c_address_c="c_address";
    public static final String co_phone="c_phone";
    public static final String co_website="c_website";
    public static final String co_password="c_password";

    String auto_company_name;
    String auto_company_email;
    String auto_company_address;
    String auto_company_phone;
    String auto_company_website;
    String auto_company_password;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_mobile__reg);

        c_name=(EditText)findViewById(R.id.c_name);
        c_emailid=(EditText)findViewById(R.id.c_email);
        c_address=(EditText)findViewById(R.id.address_map_location);
        c_phone=(EditText)findViewById(R.id.c_phone);
        c_website=(EditText)findViewById(R.id.c_website);
        c_password=(EditText)findViewById(R.id.c_password);


        validator=new Validator(this);
        validator.setValidationListener(this);


        //  address= (EditText) findViewById(R.id.address_map_location);

//        address.setText("Address"+getIntent().getExtras().getString("param"));
    }

    public void reg(View view)
    {
        validator.validate();
    }
    @Override
    public void onValidationSucceeded() {
        addAutoCompany();
    }

    private void addAutoCompany() {
        auto_company_name=c_name.getText().toString().trim();
        auto_company_email=c_emailid.getText().toString().trim();
        auto_company_address=c_address.getText().toString().trim();
        auto_company_phone=c_phone.getText().toString().trim();
        auto_company_website=c_website.getText().toString().trim();
        auto_company_password=c_password.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {
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
                            Toast.makeText(AutoMobile_Reg.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AutoMobile_Reg.this, login.class));
                        }
                    }
                    else
                    {
                        Toast.makeText(AutoMobile_Reg.this, "Invalid Data", Toast.LENGTH_LONG).show();

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AutoMobile_Reg.this, error.toString() , Toast.LENGTH_SHORT).show();
                //regi.setText(error.toString());
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String,String>();
                params.put(company_name,auto_company_name);
                params.put(c_email,auto_company_email);
                params.put(c_address_c,auto_company_address);
                params.put(co_phone,auto_company_phone);
                params.put(co_website,auto_company_website);
                params.put(co_password,auto_company_password);
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
