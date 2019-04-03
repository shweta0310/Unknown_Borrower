package com.example.niwedita.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;

    String token=null;
    private RequestQueue mQueue; //for getting the user's name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        this.token = getIntent().getStringExtra("token");
        Log.e("Token",token);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle((Activity) this,
                drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        ImageView img = findViewById(R.id.app_bk);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 3;
        Bitmap smallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dashboard3, options);       img.setImageBitmap(smallBitmap);


        Button incoming = (Button) findViewById(R.id.incoming);
        incoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openincoming();
            }
        });

        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensearch();
            }
        });


        Button outgoing = (Button) findViewById(R.id.outgoing);
        outgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openoutgoing();
            }
        });

        mQueue = Volley.newRequestQueue(this);

    }

    public void openincoming(){
        Intent intent=new Intent(this,TabLayoutIncomingActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }

    public void opensearch(){

        final String borrowerId;
        borrowerId = getIntent().getStringExtra("borrowerId");

        //Get user's name ( To add in Transaction table )
        String url = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/profile/getProfile";
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
                            String borrowerName=response.getString("name");
                            Intent intent=new Intent(MainActivity.this,search.class);
                            intent.putExtra("token",token);
                            intent.putExtra("borrowerId",borrowerId);
                            intent.putExtra("borrowerName",borrowerName);
                            startActivity(intent);
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

        mQueue.add(request);

    }

    public void openoutgoing(){
        Intent intent=new Intent(this,TabLayoutActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        String itemName=(String)item.getTitle();

        closeDrawer();

        switch (item.getItemId()){
            case R.id.item_a:
                openprofile();
                break;
            case R.id.item_b:
                openwallet();
                break;
            case R.id.item_c:
                opentransaction();
                break;
            case R.id.item_d:
                openlogin();
                break;
            case R.id.item_e:
                openAccountSetting();
                break;

        }

        return true;
    }

    public void openAccountSetting() {
        Intent intent = new Intent(this, AccountSetting.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }

    public void opentransaction(){
        Intent intent=new Intent(this,transaction.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }

    public void openwallet(){
        Intent intent=new Intent(this,Wallet.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }

    public void openprofile(){
        Intent intent=new Intent(this,profile.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }

    public void openlogin(){
        Intent intent=new Intent(this,loginActivity.class);
        startActivity(intent);
    }

    private void closeDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        }
        super.onBackPressed();
    }

}
