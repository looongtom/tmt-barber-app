package com.example.myapplication.model.booking.request;

import java.io.Serializable;

public class UpdateBookingStatusRequest implements Serializable {
    private Integer id;
    private String status;

    public UpdateBookingStatusRequest(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
