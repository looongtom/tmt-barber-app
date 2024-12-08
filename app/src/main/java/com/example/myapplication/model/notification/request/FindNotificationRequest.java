package com.example.myapplication.model.notification.request;

import com.google.gson.annotations.SerializedName;

public class FindNotificationRequest {
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("start_time")
    private Long startTime;
    @SerializedName("end_time")
    private Long endTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public FindNotificationRequest() {
    }

    public FindNotificationRequest(Integer userId, Long startTime, Long endTime) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
