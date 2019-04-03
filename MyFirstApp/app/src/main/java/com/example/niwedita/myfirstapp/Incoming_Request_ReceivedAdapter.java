package com.example.niwedita.myfirstapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.List;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;
import static com.example.niwedita.myfirstapp.TabLayoutIncomingActivity.token1;

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
        final int j=i;
        myHolder.requested_date.setText(incoming_request_received.getDate());
        myHolder.borrower_name.setText(incoming_request_received.getName());
        myHolder.amount.setText(incoming_request_received.getAmount());
        myHolder.transaction.setText(incoming_request_received.getTransaction());


        myHolder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String transactionId=myHolder.transaction.getText().toString();
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("transactionId",Integer.parseInt(transactionId));
                    final String requestString = jsonBody.toString();


                    String url = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/incoming/decline";

                    // write the volley code for Drop button
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("dropResp",response);
                            list.remove(j);
                            Log.d("Drop","Successful");
                            Toast.makeText(context,"Successfully Dropped",Toast.LENGTH_LONG).show();
                            Incoming_Request_ReceivedAdapter.this.notifyDataSetChanged();
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
        myHolder.pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String transactionId=myHolder.transaction.getText().toString();
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("transactionId",Integer.parseInt(transactionId));
                    final String requestString = jsonBody.toString();


                    String url = "http://UnknownBorrowersBK-dev.qjp3wbxcie.us-east-1.elasticbeanstalk.com/incoming/pay";

                    // write the volley code for Drop button
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response ",response);

                            if (response.contains("payment successful")) {
                                Log.d("if","================================================");
                                list.remove(j);
                                Toast.makeText(context,"Successfully Paid",Toast.LENGTH_LONG).show();
                                Incoming_Request_ReceivedAdapter.this.notifyDataSetChanged();

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView borrower_name, requested_date, amount, transaction;
        Button reject,pay_now;
        View sideBar;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            borrower_name = itemView.findViewById(R.id.borrower_name);
            requested_date = itemView.findViewById(R.id.requested_date);
            reject = itemView.findViewById(R.id.reject_button);
            pay_now = itemView.findViewById(R.id.pay_now);
            amount = itemView.findViewById(R.id.amount_request);
            transaction= itemView.findViewById(R.id.transactionReqRec);
        }


    }

}
