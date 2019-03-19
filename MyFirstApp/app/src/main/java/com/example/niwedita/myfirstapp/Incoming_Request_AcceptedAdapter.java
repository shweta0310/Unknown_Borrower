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

public class Incoming_Request_AcceptedAdapter extends RecyclerView.Adapter<Incoming_Request_AcceptedAdapter.MyHolder> {

    private ArrayList<Incoming_Request_Accepted> list;
    private Context context;

    public Incoming_Request_AcceptedAdapter(ArrayList<Incoming_Request_Accepted> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Incoming_Request_AcceptedAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_incoming_requests_accepted, viewGroup, false);
        return  new Incoming_Request_AcceptedAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Incoming_Request_AcceptedAdapter.MyHolder myHolder, int i) {

        Incoming_Request_Accepted incoming_request_accepted = list.get(i);

        myHolder.requested_date2.setText(incoming_request_accepted.getDate());
        myHolder.borrower_name2.setText(incoming_request_accepted.getName());
        myHolder.amount2.setText(incoming_request_accepted.getAmount());
        myHolder.payDate.setText(incoming_request_accepted.getPayDate());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView borrower_name2, requested_date2, amount2, payDate,transaction;
        Button view_profile;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            borrower_name2 = itemView.findViewById(R.id.borrower_name2);
            requested_date2 = itemView.findViewById(R.id.requested_date2);
            amount2 = itemView.findViewById(R.id.amount_request2);
            payDate = itemView.findViewById(R.id.pay_date);
            transaction=itemView.findViewById(R.id.transactionReqAcc);

        }


    }
}
