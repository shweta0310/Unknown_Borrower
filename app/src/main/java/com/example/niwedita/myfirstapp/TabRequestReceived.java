package com.example.niwedita.myfirstapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.niwedita.myfirstapp.TabLayoutIncomingActivity.incomingRequestReceivedArrayList;

public class TabRequestReceived extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_tab_request_received, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.tab_request_received_recyclerView);
        recyclerView.setAdapter(new Incoming_Request_ReceivedAdapter(incomingRequestReceivedArrayList,getContext()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }
}
