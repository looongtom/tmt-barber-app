package com.example.myapplication.model;

import java.io.Serializable;

public class TimeSlot implements Serializable {
    private int id;
    private String timeStart;
    private String status;
    private String date;
    private int idBarber;

    public TimeSlot() {
    }

    public TimeSlot(int id, String timeStart, String status, String date, int idBarber) {
        this.id = id;
        this.timeStart = timeStart;
        this.status = status;
        this.date = date;
        this.idBarber = idBarber;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", timeStart='" + timeStart + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", idBarber=" + idBarber +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdBarber() {
        return idBarber;
    }

    public void setIdBarber(int idBarber) {
        this.idBarber = idBarber;
    }
}
