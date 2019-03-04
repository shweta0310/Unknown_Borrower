package com.example.niwedita.myfirstapp;

public class Outgoing_Request_Confirmed {
    String name,dueDate,requestDate;

    public Outgoing_Request_Confirmed(String name, String dueDate, String requestDate) {
        this.name = name;
        this.dueDate = dueDate;
        this.requestDate = requestDate;
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
