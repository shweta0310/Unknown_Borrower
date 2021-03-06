package com.example.niwedita.myfirstapp;

public class Incoming_Request_Received {
    String name;
    String date;
    String amount;
    String transaction;

    public Incoming_Request_Received(String name, String date, String amount,String transactionID ) {
        this.name = name;
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
