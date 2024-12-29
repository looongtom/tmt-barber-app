package com.example.myapplication.model.timeslot.request;

import java.io.Serializable;

public class UpdateStatusTimeSlotRequest implements Serializable {
    private int id;
    private String status;

    public UpdateStatusTimeSlotRequest() {
    }

    public UpdateStatusTimeSlotRequest(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
