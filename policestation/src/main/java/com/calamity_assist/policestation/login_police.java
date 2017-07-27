package com.calamity_assist.policestation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.calamity_assist.policestation.Constant.SessionManager;
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

public class login_police extends Activity implements Validator.ValidationListener {

    @Email(message = "example@domain.com")
    EditText police_email;

    @Password(min=6,scheme = Password.Scheme.ANY,message = "Must Provide by the Alphabet,Number,SymBol")
    EditText police_password;


    Button btn_police_login;


    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_police);
        if(getIntent().getBooleanExtra("Exit me", false))
        {
            finish();
        }
        police_email=(EditText)findViewById(R.id.police_login_email);
       police_password=(EditText)findViewById(R.id.police_login_password);
        btn_police_login=(Button)findViewById(R.id.btn_police_login_up);


        validator=new Validator(this);
        validator.setValidationListener(this);


    }

    public void Home(View view)
    {
        validator.validate();
       //startActivity(new Intent(login_police.this,MainActivity.class));
    }

    public void Sign_up(View view)
    {
        startActivity(new Intent(login_police.this,Police_reg.class));
    }

    public void Forgot_password(View view)
    {
        startActivity(new Intent(login_police.this,Forgot_password_police.class));
    }

    @Override
    public void onValidationSucceeded() {

        new police_Login().execute();
    }

    class police_Login extends AsyncTask<String, String, String> {

        final String poli_email=police_email.getText().toString().trim();
        final String poli_password = police_password.getText().toString().trim();

        ProgressDialog pg;

        @Override
        protected void onPreExecute() {
            pg = new ProgressDialog(login_police.this);
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
                                    SessionManager.getInstance().setID(object.getString("police_id"));
                                    SessionManager.getInstance().setNames(object.getString("police_name"));
                                    SessionManager.getInstance().setEmail(object.getString("police_email"));
                                    SessionManager.getInstance().setAddress(object.getString("police_address"));
                                    SessionManager.getInstance().setMobile(object.getString("police_phone"));
                                    SessionManager.getInstance().setwebsite(object.getString("police_website"));

                                    startActivity(new Intent(login_police.this,Home_police.class));



                                }
                            }
                        } else {
                            Toast.makeText(login_police.this, "Invalid Email ID Or Password", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(login_police.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(config.Police_email,poli_email);
                    params.put(config.Police_password,poli_password);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(login_police.this);
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
