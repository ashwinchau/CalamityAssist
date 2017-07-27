package com.calamity_assist.calamityassist;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Sign_up_Screen extends Activity implements Validator.ValidationListener {

    @BindView(R.id.regi)
    TextView regi;
    @NotEmpty
    @BindView(R.id.activity_sign_up_firstname)
    EditText edtf_name;

    @NotEmpty
    @BindView(R.id.activity_sign_up_lastname)
    EditText edtl_name;

    @Email(message = "example@domain.com")
    @BindView(R.id.activity_sign_up_Email)
    EditText edt_email;

    @Password(min=6,scheme = Password.Scheme.ANY,message = "Must Provide by the Alphabet,Number,SymBol")
    @BindView(R.id.activity_sign_up_screen_Password)
    EditText edt_passwrod;

    @Password(min=6,scheme = Password.Scheme.ANY,message = "Must Provide by the Alphabet,Number,SymBol")
    @BindView(R.id.activity_sign_up_screen_ConfirmPassword)
    EditText edt_confirm;

    @NotEmpty
    @BindView(R.id.activity_sign_up_screen_phone)
    EditText edt_phone;

    @BindView(R.id.btn_signup)
    Button btnsignup;

    Validator validator;

    //Database field name

    public static final String insert="http://192.168.43.40/calamity/user_reg.php";
    public static final String fname="fname";
    public static final String lname="lname";
    public static final String u_email_id="email";
    public static final String password="password";
    public static final String mobile="mobile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__screen);


        ButterKnife.bind(this);

        validator=new Validator(this);
        validator.setValidationListener(this);

    }

    @OnClick(R.id.btn_signup)
    public void validateCredentials()
    {
        validator.validate();
    }


    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this,"Your Login is Succseful", Toast.LENGTH_LONG).show();
        reg();
        startActivity(new Intent(this,login_smart.class));

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

    public void reg()
    {

        final String fisrtname = edtf_name.getText().toString();
        final String lastname = edtl_name.getText().toString();
        final String email_id = edt_email.getText().toString();
        final String ed_password = edt_passwrod.getText().toString();
        final String ed_mobile = edt_phone.getText().toString();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(Sign_up_Screen.this, response, Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Sign_up_Screen.this,error.toString(), Toast.LENGTH_SHORT).show();
                        regi.setText(error.toString());
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String,String>();
                params.put(fname,fisrtname);
                params.put(lname,lastname);
                params.put(u_email_id,email_id);
                params.put(password,ed_password);
                params.put(mobile,ed_mobile);

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
