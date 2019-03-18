package com.example.niwedita.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class incoming extends AppCompatActivity {

    ArrayList<Incoming_Request_Received> incoming_request_receivedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming);

        incoming_request_receivedList = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            incoming_request_receivedList.add(new Incoming_Request_Received("Nisarg Joshi", "10/02/2019", "Rs. 1000"));

        final RecyclerView recyclerView = findViewById(R.id.request_received_recycleView);
        recyclerView.setAdapter(new Incoming_Request_ReceivedAdapter(incoming_request_receivedList, this));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Button button = (Button) findViewById(R.id.acceptedBtn);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("onCLick", "WorkingIntent1");
                Intent i1 = new Intent(incoming.this, incomingAccepted.class);
                startActivity(i1);
            }
        });
    }

}
