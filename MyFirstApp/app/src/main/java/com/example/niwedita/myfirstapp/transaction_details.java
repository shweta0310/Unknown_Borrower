package com.example.niwedita.myfirstapp;

public class transaction_details {
    private String transactionid;
    private String borrowername;
    private String lendername;
    private int amount;
    private String requestdate;
    private String settleddate;

    public transaction_details(String transactionid, String borrowername, String lendername, int amount, String requestdate, String settleddate) {
        this.transactionid = transactionid;
        this.borrowername = borrowername;
        this.lendername = lendername;
        this.amount = amount;
        this.requestdate = requestdate;
        this.settleddate = settleddate;
    }


    public String getTransactionid() {
        return transactionid;
    }

    public String getBorrowername() {
        return borrowername;
    }

    public String getLendername() {
        return lendername;
    }

    public int getAmount() {
        return amount;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public String getSettleddate() {
        return settleddate;
    }



}
