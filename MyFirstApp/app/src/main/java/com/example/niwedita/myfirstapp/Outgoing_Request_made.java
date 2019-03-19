package com.example.niwedita.myfirstapp;

public class Outgoing_Request_made {
    String name;
    String date,transaction,amount2;


    public Outgoing_Request_made(String name, String date,String transactionId,String amount) {
        this.name = name;
        this.date = date;
        this.transaction=transactionId;
        this.amount2=amount;
    }

    public String getAmount2() {
        return amount2;
    }

    public void setAmount2(String amount2) {
        this.amount2 = amount2;
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
