package com.example.niwedita.myfirstapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddMoney extends AppCompatActivity {

    private EditText amountText;
    private Button submitButton;

    private TextView availableBalance;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        availableBalance = findViewById(R.id.availablebalance);

        mQueue = Volley.newRequestQueue(this);

        amountText = (EditText) findViewById(R.id.addAmount);
        submitButton = (Button) findViewById(R.id.submit);

        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addMoneyButtonClicked();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        viewAmount();
    }

    private void viewAmount()
    {
        String url = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/profile/getProfile";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            Double amount = response.getDouble("balance");

                            availableBalance.append("Available Balance:  \u20B9" + String.valueOf(amount));
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
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Token eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaWF0IjoxNTQ5Mjg0NDk4LCJleHAiOjE1NDkzNzA4OTh9.igOx4RvRlfrZdVQm7I4C_2E-aAN4vuvpnH-zK3QU16o");
                return headers;
            }
        };

        mQueue.add(request);
    }

    public void addMoneyButtonClicked() throws JSONException {
        final String amt = amountText.getText().toString();
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("amount",Double.parseDouble(amt));
        final String requestString = jsonBody.toString();

        if(amt.length() == 0)
        {
            amountText.requestFocus();
            amountText.setError("Field cannot be empty");
        }
        else if(amt.startsWith("0"))
        {
            amountText.requestFocus();
            amountText.setError("Field cannot start with zero");
        }
        else if(amt.startsWith("."))
        {
            amountText.requestFocus();
            amountText.setError("Field cannot start with decimal");
        }
        else if(amt.endsWith("."))
        {
            amountText.requestFocus();
            amountText.setError("Field should not end with decimal");
        }
        else
        {
            String url = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/profile/addMoney";

            StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Your Array Response", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error is ", "" + error);
                }
            }) {

                //This is for Headers If You Needed
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Token eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaWF0IjoxNTQ5Mjg0NDk4LCJleHAiOjE1NDkzNzA4OTh9.igOx4RvRlfrZdVQm7I4C_2E-aAN4vuvpnH-zK3QU16o");
                    return headers;
                }

                //Pass Your Parameters here
                // @Override
//                protected Map<String, String> getParams() {
//                    Map<String, Double> params = new HashMap<String, Double>();
//                    params.put("balance", Double.parseDouble(amt));
//                    Map<String,String> returnParams = new HashMap<>();
//                    returnParams.put("balance",amt);
//                    return returnParams;
//                }

                @Override
                public byte[] getBody() {
                    try{
                        return requestString.getBytes();
                    }catch (Exception e){
                        return null;
                    }
                }
            };

            mQueue.add(request);

            View linearLayout =  findViewById(R.id.info);
            //LinearLayout layout = (LinearLayout) findViewById(R.id.info);


            TextView valueTV = new TextView(this);
            valueTV.setText("Money Added succesfully");
            valueTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            ((LinearLayout) linearLayout).addView(valueTV);
        }
    }
}

