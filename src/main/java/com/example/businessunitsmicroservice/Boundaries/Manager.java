package com.example.businessunitsmicroservice.Boundaries;

public class Manager {

    private String email;



    public Manager(){}

    public Manager(String emailManager) {
        this.email=emailManager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
