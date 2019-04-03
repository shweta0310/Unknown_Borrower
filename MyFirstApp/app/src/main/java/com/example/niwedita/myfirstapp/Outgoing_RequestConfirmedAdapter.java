package com.example.niwedita.myfirstapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.niwedita.myfirstapp.TabLayoutActivity.token1;

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
    public void onBindViewHolder(@NonNull final Outgoing_RequestConfirmedAdapter.requestconfirmedHolder requestconfirmedHolder, int i) {
        final int j=i;
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
                AlertDialog.Builder alert1 = new AlertDialog.Builder(context,R.style.MyDialogTheme);
                alert1.setTitle("Unknown Borrower");
                alert1.setMessage("Do you want to Pay the Amount ?");
                alert1.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            final String transactionId=requestconfirmedHolder.transaction.getText().toString();
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("transactionId",Integer.parseInt(transactionId));
                            final String requestString = jsonBody.toString();


                            String url = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/outgoing/pay_back";

                            // write the volley code for Drop button
                            RequestQueue queue = Volley.newRequestQueue(context);
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("Response ",response);

                                    if (response.contains("payment successful")) {
                                        Log.d("if","================================================");
                                        outgoing_request_confirmedArrayList.remove(j);
                                        Toast.makeText(context,"Successfully Paid",Toast.LENGTH_LONG).show();
                                        Outgoing_RequestConfirmedAdapter.this.notifyDataSetChanged();

                                    }
                                    else if(response.contains("insufficient funds")) {
                                        Log.d("elseif","================================================");
                                        Toast.makeText(context,"Balance Not Sufficient",Toast.LENGTH_LONG).show();
                                    }
                                    Log.d("log2","================================================");
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
                            Log.d("jsonException ",e.toString());
                        }
                    }
                });

                alert1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("canel","cancelled==========");
                    }
                });
                alert1.create().show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return outgoing_request_confirmedArrayList.size();
    }
}
