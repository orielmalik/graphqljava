package com.example.businessunitsmicroservice.Boundaries;

public class employees {
    private String email;
public employees(){}
    public employees(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
