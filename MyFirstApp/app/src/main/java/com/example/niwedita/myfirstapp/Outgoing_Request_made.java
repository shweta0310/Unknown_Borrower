package com.example.niwedita.myfirstapp;

public class Outgoing_Request_made {
    String name;
    String date;


    public Outgoing_Request_made(String name, String date) {
        this.name = name;
        this.date = date;
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
