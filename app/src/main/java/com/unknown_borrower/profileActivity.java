package com.unknown_borrower;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class profileActivity extends AppCompatActivity {

    private TextView username, contactNum, email, city, state, organization, age, gender;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.username);
        contactNum = findViewById(R.id.contactNum);
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        organization = findViewById(R.id.organisation);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);

        Button button = (Button) findViewById(R.id.editProfileBtn);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("onCLick", "WorkingIntent1");
                Intent i1 = new Intent(profileActivity.this, editProfileActivity.class);
                startActivity(i1);
            }
        });

        mQueue = Volley.newRequestQueue(this);

    }

    @Override
    public void onResume(){
        super.onResume();
        viewUser();

    }

    String url = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/profile/getProfile";

    private void viewUser()
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        String str = response.toString();
                        Log.d("INFO",str);
                        try
                        {
                            String name = response.getString("name");
                            username.append(name);

                            String num = response.getString("contactNum");
                            contactNum.append(num);

                            String Email = response.getString("emailId");
                            email.append(Email);

                            String City = response.getString("city");
                            city.append(City);

                            String State = response.getString("state");
                            state.append(State);

                            String Org = response.getString("org");
                            organization.append(Org);

                            Integer Age = response.getInt("age");
                            String ageInt = Integer.toString(Age);
                            age.append(ageInt);

                            Integer Gender = response.getInt("gender");
                            if(Gender==1)
                            {
                                gender.append("F");
                            }
                            if(Gender==0)
                            {
                                gender.append("M");
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
            {
                error.printStackTrace();
            }
        })

        {
            @Override
            public Map getHeaders() throws AuthFailureError
            {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Token eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaWF0IjoxNTQ5Mjg0NDk4LCJleHAiOjE1NDkzNzA4OTh9.igOx4RvRlfrZdVQm7I4C_2E-aAN4vuvpnH-zK3QU16o");
                return headers;
            }
        };

        mQueue.add(request);
    };

}
