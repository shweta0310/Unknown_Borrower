package com.example.niwedita.myfirstapp;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class outgoing extends AppCompatActivity {

    ArrayList<Outgoing_Request_made> outgoingRequest_madeList;
    ArrayList<Outgoing_Request_Confirmed> outgoingRequestConfirmedArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing);


//        outgoingRequest_madeList = new ArrayList<>();
//        outgoingRequestConfirmedArrayList= new ArrayList<>();
//
//        for (int i = 0;i<10;i++)
//            outgoingRequest_madeList.add(new Outgoing_Request_made("Siddhartha", " Requested Date: 12-12-12"));
//
//        for( int i=0; i< 10;i++)
//            outgoingRequestConfirmedArrayList.add( new Outgoing_Request_Confirmed("Siddhartha","Confirmed Date:12-3-23","Requested Date: 1-3-23"));
//
//
//        RecyclerView recyclerView = findViewById(R.id.request_made_recyclerView);
//        recyclerView.setAdapter(new Outgoing_RequestMadeAdapter(outgoingRequest_madeList, this));
//
//        RecyclerView recyclerView1 = findViewById(R.id.request_confirmed_recyclerView);
//        recyclerView1.setAdapter(new Outgoing_RequestConfirmedAdapter(outgoingRequestConfirmedArrayList,this));
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
//
//        recyclerView1.setLayoutManager(layoutManager1);

    }
}
