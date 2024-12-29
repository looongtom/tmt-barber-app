package com.example.myapplication.model.timeslot;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimeSlot implements Serializable {
    private int id;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("booked_date")
    private String date;
    private String status;
    @SerializedName("barber_id")
    private int barberId;

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", barberId=" + barberId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBarberId() {
        return barberId;
    }

    public void setBarberId(int barberId) {
        this.barberId = barberId;
    }

    public TimeSlot(int id, String startTime, String date, String status, int barberId) {
        this.id = id;
        this.startTime = startTime;
        this.date = date;
        this.status = status;
        this.barberId = barberId;
    }

    public TimeSlot() {
    }
}
