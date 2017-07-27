package com.calamity_assist.automobilecompany;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.calamity_assist.automobilecompany.Constant.SessionManager;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class login extends Activity implements Validator.ValidationListener {

    @Email(message = "example@domain.com")
    EditText auto_email;

    @Password(min=6,scheme = Password.Scheme.ANY,message = "Must Provide by the Alphabet,Number,SymBol")
    EditText auto_password;

    Button btn_auto_login;

    Validator validator;

    public static final String insert="http://192.168.43.40/calamity/auto_login.php";
    public static final String u_email_id="c_email";
    public static final String password="c_password";

    String email_id ;
    String ed_password ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auto_email=(EditText)findViewById(R.id.auto_email);
        auto_password=(EditText)findViewById(R.id.auto_password);
        btn_auto_login=(Button)findViewById(R.id.btn_auto_login);

        if(getIntent().getBooleanExtra("Exit me", false))
        {
            finish();
        }
        validator=new Validator(this);
        validator.setValidationListener(this);
    }

    public void forgot(View view)
    {
        startActivity(new Intent(login.this,Forgot_password.class));
    }
    public void Sign_up(View view)
    {
        startActivity(new Intent(login.this,AutoMobile_Reg.class));
    }

    public void login(View view)
    {
        validator.validate();
    }

    public void log()
    {
        email_id=auto_email.getText().toString().trim();
        ed_password = auto_password.getText().toString().trim();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            try
            {
                JSONObject j = new JSONObject(response);
                Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                if (j.getString("Status").equalsIgnoreCase("True")) {
                    if (j.getString("Message").equalsIgnoreCase("Successfully Login...")) {
                        JSONArray jsonArray=j.getJSONArray("response");
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject object=jsonArray.getJSONObject(i);

                            Toast.makeText(login.this, object.getString("auto_id"), Toast.LENGTH_SHORT).show();
                            SessionManager.getInstance().setID(object.getString("auto_id"));
                            SessionManager.getInstance().setNames(object.getString("auto_c_name"));
                            SessionManager.getInstance().setEmail(object.getString("auto_c_email_id"));
                            SessionManager.getInstance().setAddress(object.getString("auto_c_address"));
                            SessionManager.getInstance().setPhone(object.getString("auto_c_phone"));
                            SessionManager.getInstance().setwebsite(object.getString("auto_c_website"));


                            startActivity(new Intent(login.this,automobile_home.class));
                        }
                    }
                }
                else
                {
                    Toast.makeText(login.this, "Invalid Data", Toast.LENGTH_LONG).show();

                }
            }
            catch (JSONException e){
                e.printStackTrace();

            }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login.this, error.toString() , Toast.LENGTH_SHORT).show();
                        //regi.setText(error.toString());
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String,String>();
                params.put(u_email_id,email_id);
                params.put(password,ed_password);

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    @Override
    public void onValidationSucceeded() {
      //  Toast.makeText(this,"Your Login is Succseful", Toast.LENGTH_LONG).show();
        log();
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
