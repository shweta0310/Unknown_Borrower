package com.example.niwedita.myfirstapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class search extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Contact> contactList;
    private ContactsAdapter mAdapter;
    private SearchView searchView;
    String token=null;
    EditText enterAmount;

    // url to fetch contacts json
    //private static final String URL = "https://api.androidhive.info/json/contacts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        enterAmount=findViewById(R.id.addText);
        setSupportActionBar(toolbar);

        this.token=getIntent().getStringExtra("token");

        // toolbar fancy stuff
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.toolbar_title);
        }

        recyclerView = findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        mAdapter = new ContactsAdapter(this, contactList, this);

        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * fetches json by making http calls
     */
    private void fetchContacts(String url) {
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Please try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<Contact> items = new Gson().fromJson(response.toString(), new TypeToken<List<Contact>>() {
                        }.getType());

                        // adding contacts to contacts list
                        contactList.clear();
                        contactList.addAll(items);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                String url = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/search/get?searchString=" + query;
                fetchContacts(url);
                //mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                String url = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/search/get?searchString=" + query;
                fetchContacts(url);
                //mAdapter.getFilter().filter(query);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags =  view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility( flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(Contact contact) {
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getName() + ", " + contact.getOrganization() + "," + contact.getCity()+","+ contact.getUserId(), Toast.LENGTH_LONG).show();
        Log.e("UserId-intent content:",String.valueOf(contact.getUserId()));
        Intent intent=new Intent(search.this,ViewProfile.class);
        intent.putExtra("userId",String.valueOf(contact.getUserId()));
        startActivity(intent);
    }

    @Override
    public void onSendRequest(Contact contact) {

        String checkAmount=enterAmount.getText().toString();
        if (checkAmount.equals(""))
            Toast.makeText(getApplicationContext(), "Enter Transaction Amount", Toast.LENGTH_LONG).show();
        else {
            String borrowerName, lenderName;
            final double amount;
            int borrowerId, lenderId;

            borrowerName = getIntent().getStringExtra("borrowerName");
            borrowerId = Integer.parseInt(getIntent().getStringExtra("borrowerId"));
            lenderId = contact.getUserId();
            lenderName = contact.getName();
            amount = Double.parseDouble(enterAmount.getText().toString());

            Toast.makeText(getApplicationContext(), "Transaction: " + borrowerId + ", " + borrowerName + ", " + lenderId + ", " + lenderName + ", " + amount, Toast.LENGTH_LONG).show();

            try {
                String url = "http://unknownborrowersbk-dev.us-east-1.elasticbeanstalk.com/transaction/sendRequest";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JSONObject requestObject = new JSONObject();
                requestObject.put("borrowerName", borrowerName);
                requestObject.put("lenderId", lenderId);
                requestObject.put("lenderName", lenderName);
                requestObject.put("amount", amount);
                requestObject.put("status", 1);

                final String requestString = requestObject.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE-Send Request: ", response);
                        Intent intent = new Intent(search.this, outgoing.class);
                        intent.putExtra("amount",amount);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE-Send Request: ", error.toString());
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("Authorization", "Token " + token);
                        return params;

                    }

                    @Override
                    public byte[] getBody() {
                        try {
                            return requestString.getBytes();
                        } catch (Exception e) {
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
}