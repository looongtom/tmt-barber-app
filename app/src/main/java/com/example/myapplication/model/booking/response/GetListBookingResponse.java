package com.example.myapplication.model.booking.response;

import com.example.myapplication.model.booking.Booking;

import java.util.List;

public class GetListBookingResponse {
    private Integer total;
    private List<BookingResponse> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<BookingResponse> getData() {
        return data;
    }

    public void setData(List<BookingResponse> data) {
        this.data = data;
    }

    public GetListBookingResponse(List<BookingResponse> data) {
        this.data = data;
    }

    public GetListBookingResponse() {
    }
}