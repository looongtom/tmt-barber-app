package com.example.myapplication.model.timeslot.response;

import com.example.myapplication.model.timeslot.TimeSlot;

import java.io.Serializable;
import java.util.List;

public class UpdateTimeSlotResponse implements Serializable {
    private TimeSlot data;

    public TimeSlot getData() {
        return data;
    }

    public void setData(TimeSlot data) {
        this.data = data;
    }

    public UpdateTimeSlotResponse(TimeSlot data) {
        this.data = data;
    }
}
