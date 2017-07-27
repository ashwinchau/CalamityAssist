package com.calamity_assist.hospital;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.calamity_assist.hospital.mail.SendMail;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.util.List;
import java.util.Random;

public class otp_verify extends AppCompatActivity implements Validator.ValidationListener {

    @NonNull
    @Length(min = 4,message = "OTP Length is 4 number..")
    EditText otp_check;

    long r_no,otp;

    String hos_id,hos_email;

    Validator validator;

    Button btn_resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        validator=new Validator(this);
        validator.setValidationListener(this);

        otp_check=(EditText)findViewById(R.id.otp_edt);
        btn_resend=(Button)findViewById(R.id.btn_hospital_resend_up);
        // resend=(TextView)findViewById(R.id.resend_tv);


        r_no=getIntent().getExtras().getLong("otp");
        hos_email=getIntent().getExtras().getString("hospital_email");
        hos_id=getIntent().getExtras().getString("hospital_id");

        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    public void verifyotp(View view){

        validator.validate();
    }
    private void sendEmail() {
        //Getting content for email
        Random r = new Random();
        otp = r.nextInt(9999 - 1000) + 1000;
        String emailid = hos_email;
        String subject = "OTP Message";
        String message = "Hello this is Calamity Assist.. Your One Time Password is " + otp + "....";

        //Creating SendMail object
        SendMail sm = new SendMail(this, emailid, subject, message);

        //Executing sendmail to send email
        sm.execute();
        r_no=otp;
    }

    @Override
    public void onValidationSucceeded() {
        if(String.valueOf(r_no).equalsIgnoreCase(otp_check.getText().toString())){

            Intent in=new Intent(this,Change_password_hospital.class);
            in.putExtra("hospital_id",hos_id);
            startActivity(in);
        }
        else
        {
            Toast.makeText(this, "OTP Verification Failed..", Toast.LENGTH_SHORT).show();
            otp_check.setText("");
        }

    }



    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
