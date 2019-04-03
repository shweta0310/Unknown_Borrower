package com.example.niwedita.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class SignUp2 extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText gender;
    EditText occupation;
    EditText organization;
    EditText city;
    EditText state;
    EditText country;
    EditText signup;
    String Token;
    int userId;
    String emailId;
    String contactNum;
    EditText wallet;
    int gender1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        occupation = findViewById(R.id.occupation);
        organization = findViewById(R.id.organization);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        country = findViewById(R.id.country);
        wallet=findViewById(R.id.wallet);
        Button signup = (Button) findViewById(R.id.button);


        //On-click
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check=checkDataEntered();
                if(check)
                    sign_up_user();
            }
        });
        try {
            JSONObject responseObject = new JSONObject(getIntent().getStringExtra("responseObject"));
            Token = responseObject.getString("token");
            userId = Integer.parseInt(responseObject.getString("userId"));
            emailId = responseObject.getString("emailId");
            contactNum = responseObject.getString("contactNum");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(Token);
    }

    boolean isEmpty(EditText text){
        CharSequence str=text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isAge(EditText text){
        CharSequence age = text.getText().toString();
        Pattern p=Pattern.compile("[0-9]+");
        Matcher m=p.matcher(age);
        boolean ans=m.matches();
        return (!TextUtils.isEmpty(age) && ans);
    }

    boolean isGender(EditText text){
        CharSequence gender = text.getText().toString();
        Pattern p=Pattern.compile("[MF]");
        Matcher m=p.matcher(gender);
        boolean ans=m.matches();
        return (!TextUtils.isEmpty(gender) && ans);
    }

    boolean checkDataEntered(){
        int flag=0;
        if (isEmpty(name)) {
            name.setError("Enter Valid Name");
        }
        else flag++;
        if(!isAge(age)){
            age.setError("Enter Valid Age");
        }
        else flag++;
        if(!isGender(gender)){
            gender.setError("Enter Valid Gender");
        }
        else flag++;
        if(isEmpty(occupation)) {
            occupation.setError("Enter Occupation");
        }
        else flag++;
        if(isEmpty(organization)) {
            organization.setError("Enter Organization");
        }
        else flag++;
        if(isEmpty(city)) {
            city.setError("Enter City");
        }
        else flag++;
        if(isEmpty(state)) {
            state.setError("Enter State");
        }
        else flag++;
        if(isEmpty(country)) {
            country.setError("Enter Country");
        }
        else flag++;
        if(isEmpty(wallet)){
            wallet.setText("0");
        }
        if(flag==8)
            return true;
        else
            return false;
    }

    void sign_up_user() {

        try {
            if (gender.toString().equals("M"))
                gender1 = 0;
            else if (gender.toString().equals("F"))
                gender1 = 1;

            String url = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/profile/createProfile";
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            JSONObject requestObject = new JSONObject();
            requestObject.put("emailId",emailId);
            requestObject.put("contactNum",contactNum);
            requestObject.put("userId",userId);
            requestObject.put("name", name.getText().toString());
            requestObject.put("occupation", occupation.getText().toString());
            requestObject.put("org", organization.getText().toString());
            requestObject.put("city", city.getText().toString());
            requestObject.put("state", state.getText().toString());
            requestObject.put("country", country.getText().toString());
            requestObject.put("gender", gender1);
            requestObject.put("age", Integer.parseInt(age.getText().toString()));
            requestObject.put("balance",wallet.getText().toString());
            requestObject.put("ratings", 0);

            final String requestString = requestObject.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY_SCROLL", response);

                    Intent intent = new Intent(SignUp2.this, MainActivity.class);
                    intent.putExtra("token", Token);
                    intent.putExtra("borrowerId",String.valueOf(userId));
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERROR", error.toString());
                }
            }) {


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization","Token "+ Token);
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
