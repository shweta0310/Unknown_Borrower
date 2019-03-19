package com.example.niwedita.myfirstapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewProfile extends AppCompatActivity {
    private TextView username, contactNum, email, city, state, organization, age, gender;
    private RequestQueue requestQueue;
    String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);

        username = findViewById(R.id.username);
        contactNum = findViewById(R.id.contactNum);
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        organization = findViewById(R.id.organisation);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);

        userId=getIntent().getStringExtra("userId");
        requestQueue = Volley.newRequestQueue(this);
        viewUser();

    }

    @Override
    public void onResume() {
        super.onResume();
        viewUser();

    }

    void viewUser() {

        String url = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/search/viewProfile?id="+userId;
        Log.e("USERID:",url);
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String str = response.toString();
                        Log.e("INFO",str);
                        try
                        {
                            String name = response.getString("name");
                            username.setText(name);

                            String num = response.getString("contactNum");
                            contactNum.setText(num);

                            String Email = response.getString("emailId");
                            email.setText(Email);

                            String City = response.getString("city");
                            city.setText(City);

                            String State = response.getString("state");
                            state.setText(State);

                            String Org = response.getString("org");
                            organization.setText(Org);

                            Integer Age = response.getInt("age");
                            String ageInt = Integer.toString(Age);
                            age.setText(ageInt);

                            Integer Gender = response.getInt("gender");
                            if(Gender==1)
                            {
                                gender.setText("F");
                            }
                            if(Gender==0)
                            {
                                gender.setText("M");
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {   Log.e("Volley error","errorr");
                error.printStackTrace();
            }
        })

        {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError
            {
                HashMap<String,String> headers = new HashMap<String,String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        requestQueue.add(objectRequest);
    }
}
