package com.example.niwedita.myfirstapp;

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
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountSetting extends AppCompatActivity {

    String token = null;

    EditText currentPassword;
    EditText newPassword;
    EditText retypePassword;

    Button saveChanges;

    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        this.token = getIntent().getStringExtra("token");
        Log.e("Token", token);

        mQueue = Volley.newRequestQueue(this);

        currentPassword = (EditText) findViewById(R.id.current_password);
        newPassword = (EditText) findViewById(R.id.new_password);
        retypePassword = (EditText) findViewById(R.id.retype_password);

        saveChanges = (Button) findViewById(R.id.saveChange);

        saveChanges.setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveChangesClicked();
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

    public void saveChangesClicked() throws JSONException {

        final String crtPassword = currentPassword.getText().toString();
        final String nwPassword = newPassword.getText().toString();
        final String rtypPassword = retypePassword.getText().toString();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currentPassword", crtPassword);
        jsonObject.put("newPassword", rtypPassword);

        final String requestString = jsonObject.toString();

        if(currentPassword.getText().toString().length()<8 && (!isValidPassword(currentPassword.getText().toString())))
        {
            currentPassword.requestFocus();
            currentPassword.setError("Not valid or 8 characters minimum");
        }
        else if(newPassword.getText().toString().length()<8 && (!isValidPassword(newPassword.getText().toString()))) {
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
            //WHEN CHANGE PASSWORD BUTTON IS CLICKED
            String url = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/profile/changePassword";

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.e("Your Array Response", response);
                                Toast.makeText(getApplicationContext(),"Password Changed Successfully", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                currentPassword.requestFocus();
                                currentPassword.setError("Incorrect Password");
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Maybe wrong Password
                    Toast.makeText(getApplicationContext(), "Incorrect Password!", Toast.LENGTH_SHORT).show();
                    currentPassword.requestFocus();
                    currentPassword.setError("Incorrect Password");
                }
            }){
                //passing headers to the Request
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Token " + token);
                    return headers;
                }

                //Passing body to the Request
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
