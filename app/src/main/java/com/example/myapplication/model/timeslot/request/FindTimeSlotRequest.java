package com.example.myapplication.model.timeslot.request;

import com.google.gson.annotations.SerializedName;

public class FindTimeSlotRequest {
    @SerializedName("barber_id")
    private int barberId;
    @SerializedName("booked_date")
    private String date;
    @SerializedName("start_time")
    private String startTime;
    private String status;

    public int getBarberId() {
        return barberId;
    }

    public void setBarberId(int barberId) {
        this.barberId = barberId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FindTimeSlotRequest(int barberId, String date, String startTime, String status) {
        this.barberId = barberId;
        this.date = date;
        this.startTime = startTime;
        this.status = status;
    }
}
