package com.example.niwedita.myfirstapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabLayoutIncomingActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    String token=null;
    private RequestQueue mQueue;

    static public ArrayList<Incoming_Request_Received> incomingRequestReceivedArrayList;
    static public ArrayList<Incoming_Request_Accepted> incomingRequest_madeList;
    static public String token1;

    String url1 = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/incoming/getreqRec";
    String url2 = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/incoming/getreqAcc";

    // adding tabs synchronously -- inside the oncreate -- since volley is asynchronous
    public void addTabs() {

        //starts here
        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarIncoming);
        setSupportActionBar(toolbar);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutIncoming);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Request Received"));
        tabLayout.addTab(tabLayout.newTab().setText("Request Accepted"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pagerIncoming);

        //Creating our pager adapter
        IncomingPager adapter = new IncomingPager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        // ends here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_incoming);

        incomingRequestReceivedArrayList = new ArrayList<>();
        incomingRequest_madeList = new ArrayList<>();

        this.token = getIntent().getStringExtra("token");
        token1 = this.token;

        // getting the data from backend
        mQueue = Volley.newRequestQueue(this);

        // request must be created synchronously .... since volley is asynchronous

        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        String str = response.toString();
                        Log.d("INFORM",str);

                        try
                        {

                            for(int i=0;i<response.length();i++)
                            {
                                JSONObject transaction = response.getJSONObject(i);
                                Log.d("infoRM","===================================="+transaction.toString());
                                Incoming_Request_Received x = new Incoming_Request_Received(transaction.getString("borrowerName"), " Req on "+transaction.getString("requestedDate"), "Rs "+transaction.getString("amount")+"/-",transaction.getString("transactionId"));
                                Log.d("InfoRM",x.toString());
                                incomingRequestReceivedArrayList.add(x);
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
                headers.put("Authorization", "Token " + token);
                return headers;
            }
        };


        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        String str = response.toString();
                        Log.d("INFORC",str);

                        try
                        {

                            for(int i=0;i<response.length();i++)
                            {
                                JSONObject transaction = response.getJSONObject(i);
                                Log.d("infoRC",transaction.toString());
                                Incoming_Request_Accepted x =new Incoming_Request_Accepted(transaction.getString("borrowerName"),"Accepted on " + transaction.getString("acceptedDate"),"Req on "+transaction.getString("requestedDate"),"Rs "+transaction.getString("amount")+"/-",transaction.getString("borrowerId"));
                                Log.d("InfoRC",x.toString());
                                incomingRequest_madeList.add(x);
                            }
                            addTabs();
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
                headers.put("Authorization", "Token " + token);
                return headers;
            }
        };

        mQueue.add(request1);
        mQueue.add(request2);
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}