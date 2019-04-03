package com.example.niwedita.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailId;
    private Button cancel;
    private Button findAccount;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mQueue = Volley.newRequestQueue(this);

        cancel = (Button) findViewById(R.id.cancelBtn);
        findAccount = (Button) findViewById(R.id.findAccountBtn);

        emailId = (EditText) findViewById(R.id.name_editbox);

        cancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPassword.super.onBackPressed();
            }
        });

        findAccount.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    findAccountClicked();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void findAccountClicked() throws JSONException {
        final String email = emailId.getText().toString();

        String url = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/forgotPassword/sendOTPMail";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Your array Response ", response);
                    Toast.makeText(getApplicationContext(), "OTP send successfully", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ForgotPassword.this, OtpReceived.class);
                    intent.putExtra("emailId",email);
                    startActivity(intent);
                } catch (Exception e) {
                    emailId.requestFocus();
                    emailId.setError("Incorrect Email or OTP already exists");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Incorrect Email or OTP already exists", Toast.LENGTH_LONG).show();
                emailId.requestFocus();
                emailId.setError("Incorrect Email");
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("emailId",email);
                return params;
            }
        };
        // Adding request to the RequestQueue.
        mQueue.add(stringRequest);
    }
}
