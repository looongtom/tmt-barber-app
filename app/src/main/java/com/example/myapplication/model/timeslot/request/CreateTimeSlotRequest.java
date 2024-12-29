package com.example.myapplication.model.timeslot.request;

import java.util.ArrayList;
import java.util.List;

public class CreateTimeSlotRequest {
    private List<FindTimeSlotRequest> list;

    public CreateTimeSlotRequest(int barberId,String date) {
        List<FindTimeSlotRequest> createdTimeSlotList = new ArrayList<>();
        //loop from 09:00 to 17:30 with 30 minutes interval
        for (int i = 9; i < 18; i++) {
            for (int j = 0; j < 60; j += 30) {
                String startTime = String.format("%02d:%02d", i, j);
                FindTimeSlotRequest findTimeSlotRequest = new FindTimeSlotRequest(barberId, date, startTime, "Available");
                createdTimeSlotList.add(findTimeSlotRequest);
            }
        }
        this.list = createdTimeSlotList;
    }

    public List<FindTimeSlotRequest> getList() {
        return list;
    }

    public void setList(List<FindTimeSlotRequest> list) {
        this.list = list;
    }
}
