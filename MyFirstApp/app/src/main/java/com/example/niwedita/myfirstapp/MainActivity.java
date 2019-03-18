package com.example.niwedita.myfirstapp;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;

    String token=null;

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

    }

    public void openincoming(){
        Intent intent=new Intent(this,incoming.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }

    public void opensearch(){
        Intent intent=new Intent(this,search.class);
        intent.putExtra("token",token);
        startActivity(intent);
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
