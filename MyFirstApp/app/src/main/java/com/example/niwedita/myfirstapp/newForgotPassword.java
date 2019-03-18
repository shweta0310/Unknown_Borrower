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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class newForgotPassword extends AppCompatActivity {

    EditText newPassword;
    EditText retypePassword;

    Button submit;

    String userId = null;
    String OTP = null;

    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_forgot_password);

        this.userId = getIntent().getStringExtra("userId");
        this.OTP = getIntent().getStringExtra("otp");

        mQueue = Volley.newRequestQueue(this);

        newPassword = (EditText) findViewById(R.id.new_password);
        retypePassword = (EditText) findViewById(R.id.retype_password);

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    submitPasswordClicked();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    // To check the password is valid or not
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public void submitPasswordClicked() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("OTP", OTP);
        jsonObject.put("newPassword", newPassword.getText().toString());

        final String requestString = jsonObject.toString();
        Log.d("request", requestString);

        final String nwPassword = newPassword.getText().toString();
        final String rtypPassword = retypePassword.getText().toString();

        if(newPassword.getText().toString().length()<8 && (!isValidPassword(newPassword.getText().toString()))) {
            newPassword.requestFocus();
            newPassword.setError("Not valid or 8 characters minimum");
        }
        else if(retypePassword.getText().toString().length()<8 && (!isValidPassword(retypePassword.getText().toString()))) {
            retypePassword.requestFocus();
            retypePassword.setError("Not valid or 8 characters minimum");
        }
        else if(!(nwPassword.equals(rtypPassword))) {
            retypePassword.requestFocus();
            retypePassword.setError("Passwords do not match");
        }
        else {
            //WHEN SUMBIT BUTTON IS CLICKED
            String url = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/forgotPassword/resetPassword";

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Your Array Response", response);
                            Toast.makeText(getApplicationContext(),"Password Updated Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(newForgotPassword.this, loginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error is ", "" + error);
                    Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    return headers;
                }
                //Passing Body to the request
                @Override
                public byte[] getBody() {
                    try {
                        return requestString.getBytes();
                    } catch (Exception e) {
                        return null;
                    }
                }

            };

            // Adding request to the RequestQueue.
            mQueue.add(stringRequest);
        }
    }
}
