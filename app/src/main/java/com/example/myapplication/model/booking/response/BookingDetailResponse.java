package com.example.myapplication.model.booking.response;

import com.google.gson.annotations.SerializedName;

public class BookingDetailResponse {
    @SerializedName("data")
    private BookingDetail bookingDetail;

    public BookingDetailResponse(BookingDetail bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

    public BookingDetailResponse() {
    }

    public BookingDetail getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(BookingDetail bookingDetail) {
        this.bookingDetail = bookingDetail;
    }
}
