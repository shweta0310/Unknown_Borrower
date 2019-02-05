package com.unknown_borrower;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
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
import java.util.jar.JarException;


public class Wallet extends AppCompatActivity {

    private TextView balance, username;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        balance = findViewById(R.id.balance);
        username = findViewById(R.id.username);

        mQueue = Volley.newRequestQueue(this);

        Button button = (Button) findViewById(R.id.add_money);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("onCLick", "WorkingIntent1");
                Intent i1 = new Intent(Wallet.this, AddMoney.class);
                startActivity(i1);
            }
        });
    }

    @Override
    public void onResume()
    {
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
                        try
                        {
                            String name = response.getString("name");
                            username.setText(name);

                            Double amount = (Double) response.getDouble("balance");
                            balance.setText("\u20B9 " + String.valueOf(amount));
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
