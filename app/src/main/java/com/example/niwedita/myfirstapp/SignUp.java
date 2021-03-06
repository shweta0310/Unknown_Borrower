package com.example.niwedita.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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

public class SignUp extends AppCompatActivity {


    EditText email;
    EditText phone;
    Button register;
    EditText password;
    String id,token1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        Button register = (Button) findViewById(R.id.button2);
        password = findViewById(R.id.password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check=checkDataEntered();
                if(check){
                    register_user();
                }
            }
        });
    }
    //Validation of feilds
    boolean isEmail(EditText text){
        CharSequence email=text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isPassword(EditText text){
        CharSequence password=text.getText().toString();
        Pattern p=Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})");
        Matcher m=p.matcher(password);
        boolean ans=m.matches();
        return (!TextUtils.isEmpty(password) && ans);
    }

    boolean isPhone(EditText text){
        CharSequence phone=text.getText().toString();
        Pattern p=Pattern.compile("[0-9]{10}");
        Matcher m=p.matcher(phone);
        boolean ans=m.matches();
        return (!TextUtils.isEmpty(phone) && ans);
    }

    boolean checkDataEntered() {
        int flag=0;
        if (!isEmail(email)) {
            email.setError("Enter Valid Email-ID");
        }
        else
            flag++;
        if (!isPhone(phone)) {
            phone.setError("Enter Valid Phone Number");
        }
        else
            flag++;
        if (!isPassword(password)) {
            password.setError("Enter Valid Password (8-40 characters long and at least one Upeercase,one Lowercase and one Special character included)");
        }
        else
            flag++;
        if(flag==3)
            return true;
        else
            return false;
    }
    //POST Request to the server with email,phone and password
    void register_user() {

        try {



            String url = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/users/register";
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            JSONObject requestObject = new JSONObject();
            requestObject.put("emailId",email.getText().toString());
            requestObject.put("contactNum",phone.getText().toString());
            requestObject.put("password",password.getText().toString());

            final String requestString = requestObject.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        id = jsonObject.getString("userId");
                        Log.e("userId",id);
                        token1 = jsonObject.getString("token");
                        Log.e("token1",token1);

                        Intent intent = new Intent(SignUp.this, SignUp2.class);
                        intent.putExtra("responseObject",jsonObject.toString());
                        startActivity(intent);

                    } catch(JSONException e) {
                        Log.e("ERROR","Unexpected JSON Exception",e);

                        Toast.makeText(getApplicationContext(), "Error with the signup. Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERROR", error.toString());
                    Toast.makeText(getApplicationContext(), "Error with the signup. Please try again", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/json; charset=utf-8");
                    return params;
                }

                @Override
                public byte[] getBody() {
                    try{
                        return requestString.getBytes();
                    }catch (Exception e){
                        return null;
                    }
                }
            };

            queue.add(stringRequest);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }
}