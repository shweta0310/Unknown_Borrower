package com.example.niwedita.myfirstapp;

public class Outgoing_Request_made {
    String name;
    String date,transaction;


    public Outgoing_Request_made(String name, String date,String transactionId) {
        this.name = name;
        this.date = date;
        this.transaction=transactionId;
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
}
