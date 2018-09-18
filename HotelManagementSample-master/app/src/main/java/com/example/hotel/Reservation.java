package com.example.hotel;


public class Reservation {

    private String customerName;
    private String rooms;
    private String people;
    private String dateFrom;
    private String dateTo;

    public Reservation(String customerName, String rooms, String people, String dateFrom, String dateTo) {
        this.customerName = customerName;
        this.rooms = rooms;
        this.people = people;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRooms() {
        return rooms;
    }

    public String getPeople() {
        return people;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }
}
