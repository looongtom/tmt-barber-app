package com.example.myapplication.model.notification.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationResponse implements Serializable {
    private String id;
    @SerializedName("user_id")
    private int userId;
    private String title;
    private String message;
    private String type;
    @SerializedName("timestamp")
    private Long timestamp;
    @SerializedName("is_read")
    private boolean isRead;
    @SerializedName("raw_data")
    private Object rawData;

    public Object getRawData() {
        return rawData;
    }

    public void setRawData(Object rawData) {
        this.rawData = rawData;
    }

    public NotificationResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public NotificationResponse(String id) {
        this.id = id;
    }
}
