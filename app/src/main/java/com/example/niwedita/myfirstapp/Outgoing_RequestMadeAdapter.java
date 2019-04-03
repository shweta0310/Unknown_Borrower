package com.example.niwedita.myfirstapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.niwedita.myfirstapp.TabLayoutActivity.token1;

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
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        final int j=i;

        Outgoing_Request_made outgoingRequest_made = list.get(i);
        myHolder.date.setText(outgoingRequest_made.getDate());
        myHolder.name.setText(outgoingRequest_made.getName());
        myHolder.transaction.setText(outgoingRequest_made.getTransaction());
        myHolder.amount2.setText(outgoingRequest_made.getAmount2());
        myHolder.drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context,R.style.MyDialogTheme);
                alert.setTitle("Unknown Borrower");
                alert.setMessage("Do you want to drop the Request Made ?");

                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Log.d("tect","==================================================================================");
                        try {
                            final String transactionId=myHolder.transaction.getText().toString();
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("transactionId",Integer.parseInt(transactionId));
                            final String requestString = jsonBody.toString();


                            String url = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/outgoing/dropRequest";

                            // write the volley code for Drop button
                            RequestQueue queue = Volley.newRequestQueue(context);
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("dropResp",response);
                                    list.remove(j);
                                    Log.d("Drop","Successful");
                                    Toast.makeText(context,"Successfully Dropped",Toast.LENGTH_LONG).show();
                                    Outgoing_RequestMadeAdapter.this.notifyDataSetChanged();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("error is ", "" + error);
                                }
                            }) {

                                //This is for Headers If You Needed
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> headers = new HashMap<>();
                                    headers.put("Content-Type", "application/json");
                                    headers.put("Authorization", "Token " + token1);
                                    return headers;
                                }

                                @Override
                                public byte[] getBody() {
                                    try{
                                        return requestString.getBytes();
                                    }catch (Exception e){
                                        return null;
                                    }
                                }
                            };

                            queue.add(request);

                        }catch (JSONException e){
                            Log.d("jsonException",e.toString());
                        }

                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("tactcancel","===============================================");
                    }
                });
                alert.create().show();

                }

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView name, date , transaction,amount2;
        Button drop;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            drop = itemView.findViewById(R.id.drop_button);
            transaction=itemView.findViewById(R.id.tranMadeID);
            amount2=itemView.findViewById(R.id.amount2Textview);
        }
    }
}
