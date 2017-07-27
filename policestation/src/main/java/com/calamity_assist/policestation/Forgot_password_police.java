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
import com.calamity_assist.policestation.mail.SendMail;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Forgot_password_police extends Activity implements Validator.ValidationListener {
    @Email(message = "example@domain.com")
    EditText forgot_email;

    Button btn_forgot;

    Validator validator;

    String police_id;
    long otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_police);
        forgot_email=(EditText)findViewById(R.id.police_forgot_email);

        validator=new Validator(this);
        validator.setValidationListener(this);
    }

    public void verify(View view)
    {
        validator.validate();
         }

    @Override
    public void onValidationSucceeded() {
        new forgot_Login().execute();
    }


    private void sendEmail() {
        //Getting content for email
        Random r = new Random();
        otp = r.nextInt(9999 - 1000) + 1000;
        String emailid = forgot_email.getText().toString();
        String subject = "OTP Message";
        String message = "Hello this is Calamity Assist.. Your One Time Password is " + otp + "....";

        //Creating SendMail object
        SendMail sm = new SendMail(this, emailid, subject, message);

        //Executing sendmail to send email
        sm.execute();

    }
    class forgot_Login extends AsyncTask<String, String, String> {

        final String poli_email=forgot_email.getText().toString().trim();

        ProgressDialog pg;

        @Override
        protected void onPreExecute() {
            pg = new ProgressDialog(Forgot_password_police.this);
            pg.setMessage("Loading");
            pg.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST,config.forgot,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {



                    try {
                        JSONObject j = new JSONObject(response);

                        if (j.getString("Status").equalsIgnoreCase("True")) {
                            if (j.getString("Message").equalsIgnoreCase("Successfully sent Otp...")) {
                                Log.v("Message", j.getString("Message"));

                                startActivity(new Intent(Forgot_password_police.this, otp_verifiy.class));

                                JSONArray jsonArray=j.getJSONArray("response");
                                for(int i=0;i<jsonArray.length();i++) {
                                  JSONObject jsonObject=jsonArray.getJSONObject(i);

                                    police_id = jsonObject.getString("police_id");

                                    Intent intent = new Intent(Forgot_password_police.this, otp_verifiy.class);

                                    intent.putExtra("otp",otp);
                                    intent.putExtra("police_id", police_id);
                                    intent.putExtra("email", poli_email);
                                    startActivity(intent);

                                    //   Toast.makeText(Forgot_password_police.this, police_id, Toast.LENGTH_SHORT).show();
                                    sendEmail();

                                }
                            }
                        } else {
                            Toast.makeText(Forgot_password_police.this, "Invalid Email ID", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Forgot_password_police.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(config.Police_email,poli_email);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Forgot_password_police.this);
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
