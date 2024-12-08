package com.example.myapplication.model.notification.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetListNotification implements Serializable {
    @SerializedName("data")
    private List<NotificationResponse> data;

    public GetListNotification() {
    }

    public List<NotificationResponse> getData() {
        return data;
    }

    public void setData(List<NotificationResponse> data) {
        this.data = data;
    }

    public GetListNotification(List<NotificationResponse> data) {
        this.data = data;
    }
}
