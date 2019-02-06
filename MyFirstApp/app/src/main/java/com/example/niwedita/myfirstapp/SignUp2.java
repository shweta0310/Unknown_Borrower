package com.example.android.signup;

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
        Intent intent = getIntent();
        Token=intent.getStringExtra("token");
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

                String url = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/profile/createProfile";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("VOLLEY_SCROLL", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.toString());
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", name.toString());
                        //params.put("age", Integer.parseInt(age.getText().toString()));
                        //params.put("gender", gender1);
                        params.put("occupation", occupation.toString());
                        params.put("org", organization.toString());
                        params.put("city", city.toString());
                        params.put("state", state.toString());
                        params.put("country", country.toString());
                        //params.put("balance", Integer.parseInt(balance.toString()));
                        //params.put("ratings", Integer.parseInt(ratings.toString()));
                        return params;
                    }
                    protected Map<String ,Integer> getParams1() throws AuthFailureError {
                        Map<String,Integer> params1= new HashMap<>();
                        params1.put("gender", gender1);
                        params1.put("age", Integer.parseInt(age.getText().toString()));
                        params1.put("balance",0);
                        params1.put("ratings", 0);
                        return params1;
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        String creds = String.format("%s:%s","userlId","token");
                        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                        params.put("Authorization", auth);
                        return params;

                    }
                };
                queue.add(stringRequest);
                Intent intent = new Intent(SignUp2.this, Dashboard.class);
                intent.putExtra("token", Token);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("ERROR", e.toString());
            }
        }
}
