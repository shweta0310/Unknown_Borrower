package com.example.niwedita.myfirstapp;

public class Incoming_Request_Accepted {

    String name;
    String payDate;
    String date;
    String amount,transaction;

    public Incoming_Request_Accepted(String name, String payDate, String date, String amount,String transactionID) {
        this.name = name;
        this.payDate = payDate;
        this.date = date;
        this.amount = amount;
        this.transaction=transactionID;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
