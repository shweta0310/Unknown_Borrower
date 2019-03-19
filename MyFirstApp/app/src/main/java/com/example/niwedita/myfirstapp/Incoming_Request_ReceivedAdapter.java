package com.example.niwedita.myfirstapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class Incoming_Request_ReceivedAdapter extends RecyclerView.Adapter<Incoming_Request_ReceivedAdapter.MyHolder> {

    private ArrayList<Incoming_Request_Received> list;
    private Context context;

    public Incoming_Request_ReceivedAdapter(ArrayList<Incoming_Request_Received> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Incoming_Request_ReceivedAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_incoming_requests_received, viewGroup, false);
        return  new Incoming_Request_ReceivedAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Incoming_Request_ReceivedAdapter.MyHolder myHolder, int i) {

        Incoming_Request_Received incoming_request_received = list.get(i);

        myHolder.requested_date.setText(incoming_request_received.getDate());
        myHolder.borrower_name.setText(incoming_request_received.getName());
        myHolder.amount.setText(incoming_request_received.getAmount());


        myHolder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHolder.pay_now.setVisibility(View.INVISIBLE);
                myHolder.reject.setText("REJECTED");
                myHolder.reject.setTextColor(Color.parseColor("#ffffff"));
            }
        });

        myHolder.pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View popupView = inflater.inflate(R.layout.pay_now_popup,null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, false);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        myHolder.reject.setVisibility(View.INVISIBLE);
                        myHolder.pay_now.setText("PAID");
                        myHolder.pay_now.setTextColor(Color.parseColor("#ffffff"));
                        return true;
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView borrower_name, requested_date, amount;
        Button reject,pay_now;
        View sideBar;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            borrower_name = itemView.findViewById(R.id.borrower_name);
            requested_date = itemView.findViewById(R.id.requested_date);
            reject = itemView.findViewById(R.id.reject_button);
            pay_now = itemView.findViewById(R.id.pay_now);
            amount = itemView.findViewById(R.id.amount_request);
        }


    }

}
