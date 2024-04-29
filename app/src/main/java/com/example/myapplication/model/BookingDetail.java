package com.example.myapplication.model;

public class BookingDetail {
    private int id;
    private int bookingId;
    private int serviceId;

    public BookingDetail() {
    }

    public BookingDetail(int bookingId, int serviceId) {
        this.bookingId = bookingId;
        this.serviceId = serviceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
