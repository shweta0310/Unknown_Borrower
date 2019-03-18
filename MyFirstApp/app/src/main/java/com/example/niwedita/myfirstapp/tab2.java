package com.example.niwedita.myfirstapp;

//package net.simplifiedcoding.androidtablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static com.example.niwedita.myfirstapp.TabLayoutActivity.outgoingRequestConfirmedArrayList;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class tab2 extends Fragment {

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View view= inflater.inflate(R.layout.tab2, container, false);




        RecyclerView recyclerView1 = view.findViewById(R.id.tab2request_confirmed_recyclerView);
        recyclerView1.setAdapter(new Outgoing_RequestConfirmedAdapter(outgoingRequestConfirmedArrayList,getContext()));

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager1);

        return view;
    }

}