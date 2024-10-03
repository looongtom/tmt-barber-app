package com.example.myapplication.model.timeslot.response;

import com.example.myapplication.model.timeslot.TimeSlot;

import java.io.Serializable;
import java.util.List;

public class FindTimeSlotResponse implements Serializable {
    private List<TimeSlot> data;

    public FindTimeSlotResponse(List<TimeSlot> data) {
        this.data = data;
    }

    public List<TimeSlot> getData() {
        return data;
    }

    public void setData(List<TimeSlot> data) {
        this.data = data;
    }

    public FindTimeSlotResponse() {
    }
}
