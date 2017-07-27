package com.calamity_assist.automobilecompany;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reset_password extends AppCompatActivity implements Validator.ValidationListener {

    @Password(min=6,scheme = Password.Scheme.ANY,message = "Must Provide by the Alphabet,Number,SymBol")
    EditText new_password;

    @ConfirmPassword
    EditText confirm_password;

    Button btn_submit;

    Validator validator;

    String hos_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        new_password=(EditText)findViewById(R.id.auto_new_password);
        confirm_password=(EditText)findViewById(R.id.auto_confirm_password);
        validator=new Validator(this);
        validator.setValidationListener(this);

        hos_id= getIntent().getExtras().getString("auto_id");
        Toast.makeText(this,  hos_id, Toast.LENGTH_SHORT).show();


    }
    public void login_page(View view)
    {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        new changepass().execute();
    }

    private class changepass extends AsyncTask<String, String, String> {

        final String newpwd = new_password.getText().toString();
        ProgressDialog pg = new ProgressDialog(Reset_password.this);


        @Override
        protected void onPreExecute() {

            pg.setMessage("Loding..");
            pg.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringRequest sr = new StringRequest(Request.Method.POST, config.change, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(ChangePassswor.this, response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.getString("Status").equalsIgnoreCase("True")) {


                            Toast.makeText(Reset_password.this, "Password Change Successfully..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Reset_password.this, login.class));

                        } else {
                            Toast.makeText(Reset_password.this, "Wrong Data..", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    HashMap<String, String> p = new HashMap<String, String>();

                    // p.put(config.change_password, "changepass");
                    p.put(config.auto_id, hos_id);
                    p.put("new_pass", newpwd);

                    return p;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(Reset_password.this);
            rq.add(sr);

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
