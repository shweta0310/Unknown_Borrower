package com.example.niwedita.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class transaction extends AppCompatActivity implements transactionAdapter.OnItemClickListener {

    private Button button;
    RecyclerView recyclerView;
    transactionAdapter adapter;
    String token=null;
    String userId=null;

    ArrayList<transaction_details> transactionlist;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        transactionlist = new ArrayList<>();
        this.token = getIntent().getStringExtra("token");

        Log.e("","het");

        this.userId = getIntent().getStringExtra("userId");
      //  Log.e("",userId);


        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRequestQueue = Volley.newRequestQueue(this);
        //  viewalltransactions();
//        for (int i = 0; i < 10; i++)
//            transactionlist.add(new transaction_details("098XXX231", "Rounak", "Shweta", 5000, "01-01-19", "02-02-19"));
//
        adapter = new transactionAdapter(transactionlist, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(transaction.this);

        Log.e("","hi");
        //}

        // private void viewalltransactions()
        //{
        String url="http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/transaction/getalltransactions";

        //Log.e("","hi");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray response) {

                        // Log.e("","hi");

                        String str = response.toString();
                        Log.d("INFO",str);
                        try {


                            for (int i = 0; i < response.length(); i++) {
                                JSONObject hit = response.getJSONObject(i);

                                String transaction_id = hit.getString("transactionId");
                                String borrower_name = hit.getString("borrowerName");
                                String lender_name = hit.getString("lenderName");
                                String requested_date = hit.getString("requestedDate");
                                String completion_date = hit.getString("completionDate");
                                double amount = hit.getDouble("amount");

                                transactionlist.add(new transaction_details(transaction_id, borrower_name, lender_name, amount, requested_date, completion_date));
                                //transaction_details(transaction_id, borrower_name, lender_name, amount, requested_date, completion_date);
                            }

                            adapter = new transactionAdapter(transactionlist,transaction.this);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(transaction.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            public Map getHeaders() throws AuthFailureError
            {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Token " + token);
                return headers;
            }
        };
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this,ratings.class);
        startActivity(detailIntent);
    }
}




