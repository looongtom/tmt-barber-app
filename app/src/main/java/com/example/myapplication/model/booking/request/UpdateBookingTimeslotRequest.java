package com.example.myapplication.model.booking.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateBookingTimeslotRequest implements Serializable {
    private Integer id;
    @SerializedName("timeslot_id")
    private Integer timeSlotId;

    public UpdateBookingTimeslotRequest(Integer id, Integer timeSlotId) {
        this.id = id;
        this.timeSlotId = timeSlotId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }
}
