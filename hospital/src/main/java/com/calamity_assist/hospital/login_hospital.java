package com.calamity_assist.hospital;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.calamity_assist.hospital.Constant.SessionManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.calamity_assist.hospital.config.insert;

public class login_hospital extends Activity implements Validator.ValidationListener {

    @Email(message = "example@domain.com")
    EditText hospital_email;

    @Password(min=6,scheme = Password.Scheme.ANY,message = "Must Provide by the Alphabet,Number,SymBol")
    EditText hospital_password;

    Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_hospital);

        if(getIntent().getBooleanExtra("Exit me", false))
        {
            finish();
        }
        hospital_email=(EditText)findViewById(R.id.hospital_login_email);
        hospital_password=(EditText)findViewById(R.id.hospital_login_password);

        validator=new Validator(this);
        validator.setValidationListener(this);
    }

    public void Home(View view)
    {
        validator.validate();
    }

    public void Sign_up(View view)
    {
        startActivity(new Intent(login_hospital.this,Hospital_Reg.class));
    }

    public void Forgot_password(View view)
    {
        startActivity(new Intent(login_hospital.this,Forgot_password_hospital.class));
    }

    @Override
    public void onValidationSucceeded() {
        new hospiatl_Login().execute();

    }
    class hospiatl_Login extends AsyncTask<String, String, String> {

        final String hos_email=hospital_email.getText().toString().trim();
        final String hos_password = hospital_password.getText().toString().trim();

        ProgressDialog pg;

        @Override
        protected void onPreExecute() {
            pg = new ProgressDialog(login_hospital.this);
            pg.setMessage("Loading");
            pg.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST,config.login,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {



                    try {
                        JSONObject j = new JSONObject(response);

  //                                        Toast.makeText(login_hospital.this, "Hillllllll", Toast.LENGTH_SHORT).show();

                        if (j.getString("Status").equalsIgnoreCase("True")) {
                            if (j.getString("Message").equalsIgnoreCase("Successfully Login...")) {
                                Log.v("Message", j.getString("Message"));

                                JSONArray jsonArray=j.getJSONArray("response");
                                for(int i=0;i<jsonArray.length();i++) {
                                    JSONObject object=jsonArray.getJSONObject(i);

                                  Toast.makeText(login_hospital.this, object.getString("hospital_name"), Toast.LENGTH_SHORT).show();
                                    SessionManager.getInstance().setID(object.getString("hospital_id"));
                                    SessionManager.getInstance().setNames(object.getString("hospital_name"));
                                    SessionManager.getInstance().setEmail(object.getString("hospital_email"));
                                    SessionManager.getInstance().setAddress(object.getString("hospital_address"));
                                    SessionManager.getInstance().setPhone(object.getString("hospital_phone"));
                                    SessionManager.getInstance().setwebsite(object.getString("hospital_website"));


                                    startActivity(new Intent(login_hospital.this,Home_hospital.class));
                                }
                            }
                        } else {
                            Toast.makeText(login_hospital.this, "Invalid Email ID Or Password", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(login_hospital.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(config.Hospital_email,hos_email);
                    params.put(config.Hospital_password,hos_password);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(login_hospital.this);
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
