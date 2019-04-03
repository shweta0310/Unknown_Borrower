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
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class OtpReceived extends AppCompatActivity {

    private EditText otp;
    private Button cancel;
    private Button changePassword;

    String email = null;

    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_received);

        this.email = getIntent().getStringExtra("emailId");
        Log.e("Email ",email);

        mQueue = Volley.newRequestQueue(this);

        cancel = (Button) findViewById(R.id.cancelBtn);
        changePassword = (Button) findViewById(R.id.findAccountBtn);

        otp = (EditText) findViewById(R.id.otptext);

        cancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtpReceived.super.onBackPressed();
            }
        });

        changePassword.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    changePasswordClicked();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void changePasswordClicked() throws JSONException {
        final String OTP = otp.getText().toString();

        String url = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/forgotPassword/verifyOtp";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JsonParser parser = new JsonParser();
                    JsonElement jsonObject = parser.parse(response);
                    String userId = jsonObject.getAsJsonObject().get("userId").getAsString();

                    Log.e("Your array Response ", response);
                    Toast.makeText(getApplicationContext(), "OTP matched successfully", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(OtpReceived.this, newForgotPassword.class);
                    intent.putExtra("email",email);
                    intent.putExtra("otp",OTP);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                } catch (Exception e) {
                    otp.requestFocus();
                    otp.setError("Incorrect OTP");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Incorrect OTP2", Toast.LENGTH_LONG).show();
                    otp.requestFocus();
                    otp.setError("Incorrect OTP2");
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("otp",OTP);
                return params;
            }
        };
        // Adding request to the RequestQueue.
        mQueue.add(stringRequest);
    }
}
