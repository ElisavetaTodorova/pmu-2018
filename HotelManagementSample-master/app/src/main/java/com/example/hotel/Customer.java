package com.example.hotel;

import java.util.ArrayList;


public class Customer {
    //used to save app data

    static String name = null;
    static String email = null;
    static String password = null;

    public static ArrayList<Reservation> reservations = new ArrayList<>();

    public String getName() {
        return Customer.name;
    }

    //setting name
    public void setName(String name) {
        Customer.name = name;
    }

    public String getEmail() {
        return Customer.email;
    }

    public void setEmail(String email) {
        Customer.email = email;
    }

    public String getPassword() {
        return Customer.password;
    }

    public void setPassword(String password) {
        Customer.password = password;
    }


}
