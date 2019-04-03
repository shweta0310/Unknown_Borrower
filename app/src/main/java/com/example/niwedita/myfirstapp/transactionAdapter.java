package com.example.niwedita.myfirstapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class transactionAdapter extends RecyclerView.Adapter<transactionAdapter.transactionViewHolder> {

    private Context mCtx;
    private List<transaction_details> trasactionList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public transactionAdapter(List<transaction_details> trasactionList, Context mCtx) {
        this.mCtx = mCtx;
        this.trasactionList = trasactionList;
    }


    @NonNull
    @Override
    public transactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.transaction_cards,null);
        transactionViewHolder holder = new transactionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull transactionViewHolder transactionViewHolder, int i) {
        transaction_details transaction = trasactionList.get(i);
        transactionViewHolder.TextviewtransactionId.setText(transaction.getTransactionid());
        transactionViewHolder.TextviewBorrowerId.setText(transaction.getBorrowername());
        transactionViewHolder.TextviewLenderId.setText(transaction.getLendername());
        transactionViewHolder.TextviewRequestDate.setText(transaction.getRequestdate());
        transactionViewHolder.TextviewSettleddate.setText(transaction.getSettleddate());
        transactionViewHolder.Textviewamount.setText(String.valueOf(transaction.getAmount()));
    }

    @Override
    public int getItemCount() {
        return trasactionList.size();
    }

    class transactionViewHolder extends RecyclerView.ViewHolder
    {

        //public BreakIterator transactionId;
        TextView TextviewtransactionId,TextviewBorrowerId,TextviewLenderId,TextviewRequestDate,TextviewSettleddate,Textviewamount;
        Button button;

        public transactionViewHolder(@NonNull View itemView) {
            super(itemView);
//            TextView TextviewtransactionId,TextviewBorrowerId,TextviewLenderId,TextviewRequestDate,TextviewSettleddate,Textviewamount;
            TextviewtransactionId = itemView.findViewById(R.id.textView2);
            TextviewBorrowerId = itemView.findViewById(R.id.textView5);
            TextviewLenderId = itemView.findViewById(R.id.textView9);
            TextviewRequestDate = itemView.findViewById(R.id.textView11);
            TextviewSettleddate = itemView.findViewById(R.id.textView7);
            Textviewamount = itemView.findViewById(R.id.textView13);
            button = itemView.findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
