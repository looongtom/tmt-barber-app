package com.example.myapplication.model.booking.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimeSlotResponse implements Serializable {
    private Integer id;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("booked_date")
    private String bookedDate;

    public TimeSlotResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public TimeSlotResponse(Integer id, String startTime, String bookedDate) {
        this.id = id;
        this.startTime = startTime;
        this.bookedDate = bookedDate;
    }
}
