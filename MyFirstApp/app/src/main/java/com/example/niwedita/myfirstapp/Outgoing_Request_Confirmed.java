package com.example.niwedita.myfirstapp;

public class Outgoing_Request_Confirmed {
    String name,dueDate,requestDate,transaction,amount;

    public Outgoing_Request_Confirmed(String name, String dueDate, String requestDate,String transactionID,String amount) {
        this.name = name;
        this.dueDate = dueDate;
        this.requestDate = requestDate;
        this.transaction=transactionID;
        this.amount=amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getDueDate() {
        return dueDate;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
