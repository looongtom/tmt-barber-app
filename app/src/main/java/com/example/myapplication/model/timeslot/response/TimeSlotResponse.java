package com.example.myapplication.model.timeslot.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimeSlotResponse implements Serializable {
    private int id;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("booked_date")
    private String status;
    @SerializedName("barber_id")
    private int barberId;

}