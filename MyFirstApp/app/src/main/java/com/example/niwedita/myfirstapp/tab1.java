package com.example.niwedita.myfirstapp;

//package net.simplifiedcoding.androidtablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static com.example.niwedita.myfirstapp.TabLayoutActivity.outgoingRequest_madeList;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class tab1 extends Fragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View view = inflater.inflate(R.layout.tab1, container, false);





        RecyclerView recyclerView = view.findViewById(R.id.tab1request_made_recyclerView);

        recyclerView.setAdapter(new Outgoing_RequestMadeAdapter(outgoingRequest_madeList, getContext()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;


    }


}