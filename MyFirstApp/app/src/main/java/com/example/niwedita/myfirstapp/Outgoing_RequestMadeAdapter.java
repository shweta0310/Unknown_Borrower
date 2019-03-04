package com.example.niwedita.myfirstapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Outgoing_RequestMadeAdapter extends RecyclerView.Adapter<Outgoing_RequestMadeAdapter.MyHolder> {

    private ArrayList<Outgoing_Request_made> list ;
    private Context context;

    public Outgoing_RequestMadeAdapter(ArrayList<Outgoing_Request_made> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_outgoing_requests, viewGroup, false);
        return  new Outgoing_RequestMadeAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        Outgoing_Request_made outgoingRequest_made = list.get(i);
        myHolder.date.setText(outgoingRequest_made.getDate());
        myHolder.name.setText(outgoingRequest_made.getName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView name, date;
        Button drop;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            drop = itemView.findViewById(R.id.drop_button);
        }


    }
}
