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

public class Outgoing_RequestConfirmedAdapter extends RecyclerView.Adapter<Outgoing_RequestConfirmedAdapter.requestconfirmedHolder>{

    private ArrayList<Outgoing_Request_Confirmed> outgoing_request_confirmedArrayList;
    private Context context;

    public Outgoing_RequestConfirmedAdapter(ArrayList<Outgoing_Request_Confirmed> outgoing_request_confirmedArrayList, Context context) {
        this.outgoing_request_confirmedArrayList = outgoing_request_confirmedArrayList;
        this.context = context;
    }

    class requestconfirmedHolder extends RecyclerView.ViewHolder{

        TextView name,reqDate, dueDate,transaction,amount;
        Button pay;
        public requestconfirmedHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.nameTextView);
            reqDate=itemView.findViewById(R.id.requestDateTextView);
            dueDate = itemView.findViewById(R.id.ConfirmedDateTextView);
            pay= itemView.findViewById(R.id.payButton);
            amount=itemView.findViewById(R.id.amountTextview);
            transaction=itemView.findViewById(R.id.transConID);
        }
    }


    @NonNull
    @Override
    public requestconfirmedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_outgoing_requestsconfirmed,viewGroup,false);
        return new Outgoing_RequestConfirmedAdapter.requestconfirmedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Outgoing_RequestConfirmedAdapter.requestconfirmedHolder requestconfirmedHolder, int i) {

        Outgoing_Request_Confirmed outgoing_request_confirmed= outgoing_request_confirmedArrayList.get(i);
        requestconfirmedHolder.name.setText(outgoing_request_confirmed.getName());
        requestconfirmedHolder.reqDate.setText(outgoing_request_confirmed.getRequestDate());
        requestconfirmedHolder.dueDate.setText(outgoing_request_confirmed.getDueDate());
        requestconfirmedHolder.transaction.setText(outgoing_request_confirmed.getTransaction());
        requestconfirmedHolder.amount.setText(outgoing_request_confirmed.getAmount());


        requestconfirmedHolder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write the code for pay button
            }
        });

    }

    @Override
    public int getItemCount() {
        return outgoing_request_confirmedArrayList.size();
    }
}
