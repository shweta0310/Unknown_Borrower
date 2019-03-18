package com.example.niwedita.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class incomingAccepted extends AppCompatActivity {

    ArrayList<Incoming_Request_Accepted> incoming_request_acceptedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_accepted);

        incoming_request_acceptedList = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            incoming_request_acceptedList.add(new Incoming_Request_Accepted("Nisarg Joshi", "20/02/2019", "10/02/1999","Rs. 1000"));

        RecyclerView recyclerView = findViewById(R.id.request_accepted_recyclerView);
        recyclerView.setAdapter(new Incoming_Request_AcceptedAdapter(incoming_request_acceptedList, this));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Button button = (Button) findViewById(R.id.receivedBtn2);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomingAccepted.super.onBackPressed();
            }
        });
    }
}
