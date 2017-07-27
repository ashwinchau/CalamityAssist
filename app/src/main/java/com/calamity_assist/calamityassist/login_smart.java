package com.calamity_assist.calamityassist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.calamity_assist.calamityassist.Constant.SessionManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
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

import static com.calamity_assist.calamityassist.R.string.email_id;

public class login_smart extends Activity implements Validator.ValidationListener {


    private boolean exit = false;

    @Email(message = "example@domain.com")
    @BindView(R.id.activity_login_smart_Email)
    EditText edt_email;

    TextView nav;

    @Password(min=6,scheme = Password.Scheme.ANY,message = "Must Provide by the Alphabet,Number,SymBol")
    @BindView(R.id.activity_login_smart_Password)
    EditText edt_password;

    @BindView(R.id.btn_Login)
    Button btnLogin;

    Validator validator;


    public static final String insert="http://192.168.43.40/calamity/login.php";
    public static final String u_email_id="email";
    public static final String password="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_smart);


        if(getIntent().getBooleanExtra("Exit me", false))
        {
            finish();
        }

        ButterKnife.bind(this);

        validator=new Validator(this);
        validator.setValidationListener(this);

        nav=(TextView)findViewById(R.id.email_navigation);
    }

   // public void navigation_bottom(View view){startActivity(new Intent(this,Bottom_bar.class));}
    public void forgot_Password(View view) {startActivity(new Intent(this,Forgot_Password.class));}
    public void Sign_up_Screen(View view){startActivity(new Intent(this,Sign_up_Screen.class));}




    @OnClick(R.id.btn_Login)
    public void validateCredentials()
    {
       validator.validate();

    }
    @Override
    public void onBackPressed() {
      /*  final Intent i=new Intent(login_smart.this,login_smart.class);
        i.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        finish();
        startActivity(i);*/

        if (exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
// finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;


                }
            }, 3 * 1000);

        }
         }


 /*   public void log()
    {
        email_id=edt_email.getText().toString().trim();
        ed_password = edt_password.getText().toString().trim();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject j=new JSONObject(response);
                    if(j.getString("Status").equalsIgnoreCase("1"))
                    {
                        if(j.getString("message").equalsIgnoreCase("Login Success"))
                        {
                            Log.v("message", j.getString("message"));
                            Toast.makeText(login_smart.this, j.getString("message"), Toast.LENGTH_LONG).show();
                            OpenProfile();
                        }

                    }
                    else
                    {
                        Toast.makeText(login_smart.this, "Invalid Data", Toast.LENGTH_LONG).show();

                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login_smart.this, error.toString() , Toast.LENGTH_SHORT).show();
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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
*/


    class userLogin extends AsyncTask<String, String, String> {

        final String email_id=edt_email.getText().toString().trim();
        final String ed_password = edt_password.getText().toString().trim();

        ProgressDialog pg;

        @Override
        protected void onPreExecute() {
            pg = new ProgressDialog(login_smart.this);
            pg.setMessage("Loading");
            pg.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, insert,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject j = new JSONObject(response);
                                if (j.getString("Status").equalsIgnoreCase("True")) {
                                    if (j.getString("Message").equalsIgnoreCase("Successfully Login...")) {
                                        Log.v("Message", j.getString("Message"));
                                        JSONArray jsonArray=j.getJSONArray("response");
                                        for(int i=0;i<jsonArray.length();i++) {
                                            JSONObject object=jsonArray.getJSONObject(i);
                                            SessionManager.getInstance().setID(object.getString("u_id"));
                                            SessionManager.getInstance().setNames(object.getString("f_name"));
                                            SessionManager.getInstance().setEmail(object.getString("u_email_id"));
                                            SessionManager.getInstance().setImg(object.getString("profile_photo"));
                                           /* SessionManager.getInstance().setPassword(object.getString("u_password"));
                                            SessionManager.getInstance().setMobile(object.getString("mobile_no"));
                                            */


                                            startActivity(new Intent(login_smart.this, MainActivity.class));
                                        }
                                    }
                                } else {
                                    Toast.makeText(login_smart.this, "Invalid Email ID Or Password", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(login_smart.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(u_email_id,email_id);
                    params.put(password,ed_password);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(login_smart.this);
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
    public void onValidationSucceeded() {
        new userLogin().execute();
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

    public void OpenProfile()
    {
        Intent in=new Intent(login_smart.this,MainActivity.class);
        in.putExtra(u_email_id,email_id);
        startActivity(in);
    }

}
