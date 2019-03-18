package com.example.niwedita.myfirstapp;

public class Incoming_Request_Accepted {

    String name;
    String payDate;
    String date;
    String amount;

    public Incoming_Request_Accepted(String name, String payDate, String date, String amount) {
        this.name = name;
        this.payDate = payDate;
        this.date = date;
        this.amount = amount;
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
