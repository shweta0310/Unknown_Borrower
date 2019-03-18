package com.example.niwedita.myfirstapp;
    import android.support.design.widget.TabLayout;
    import android.content.Intent;
    import android.support.v4.view.ViewPager;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.support.v7.widget.LinearLayoutManager;
    import android.support.v7.widget.RecyclerView;
    import android.support.v7.widget.Toolbar;
    import android.util.Log;

    import com.android.volley.AuthFailureError;
    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.JsonArrayRequest;
    import com.android.volley.toolbox.JsonObjectRequest;
    import com.android.volley.toolbox.Volley;
    import com.google.gson.JsonObject;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;


    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Map;

//Implementing the interface OnTabSelectedListener to our MainActivity
//This interface would help in swiping views

public class TabLayoutActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    String token=null;
    private RequestQueue mQueue;
    // Declaring the lists for two tabs
    static public ArrayList<Outgoing_Request_made> outgoingRequest_madeList;
    static public ArrayList<Outgoing_Request_Confirmed> outgoingRequestConfirmedArrayList;

    String url1 = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/outgoing/outReqMade";
    String url2 = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/outgoing/outReqConfirmed";


    // adding tabs synchronously -- inside the oncreate -- since volley is asynchronous
    public void addTabs(){

        //starts here
        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Request Made"));
        tabLayout.addTab(tabLayout.newTab().setText("Request Confirmed"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        // ends here

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        outgoingRequest_madeList = new ArrayList<>();
        outgoingRequestConfirmedArrayList= new ArrayList<>();


        this.token = getIntent().getStringExtra("token");

        // getting the data from backend
        mQueue = Volley.newRequestQueue(this);

        // request must be created synchronously .... since volley is asynchronous

        JsonArrayRequest request1 = new JsonArrayRequest(    Request.Method.GET, url1, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        String str = response.toString();
                        Log.d("INFO",str);



                        try
                        {

                            for(int i=0;i<response.length();i++)
                            {
                                JSONObject transaction = response.getJSONObject(i);
                                Log.d("info",transaction.toString());
                                Outgoing_Request_made x =new Outgoing_Request_made(transaction.getString("lenderName"), " Requested Date:"+transaction.getString("requestedDate"));
                                Log.d("Info",x.toString());
                                outgoingRequest_madeList.add(x);
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


        JsonArrayRequest request2 = new JsonArrayRequest(    Request.Method.GET, url2, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        String str = response.toString();
                        Log.d("INFO",str);



                        try
                        {

                            for(int i=0;i<response.length();i++)
                            {
                                JSONObject transaction = response.getJSONObject(i);
                                Log.d("info",transaction.toString());
                                Outgoing_Request_Confirmed x =new Outgoing_Request_Confirmed(transaction.getString("lenderName"),"Confirmed Date: " + transaction.getString("acceptedDate"),"Requested Date: "+transaction.getString("requestedDate"));
                                Log.d("Info",x.toString());
                                outgoingRequestConfirmedArrayList.add(x);
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