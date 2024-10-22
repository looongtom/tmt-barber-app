package com.example.myapplication.model.result.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetDetailResult implements Serializable {
    private int id;
    private String description;
    @SerializedName("barber_id")
    private int barberId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("booking_id")
    private int bookingId;
    @SerializedName("list_img")
    private List<ImageResult>list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBarberId() {
        return barberId;
    }

    public void setBarberId(int barberId) {
        this.barberId = barberId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public List<ImageResult> getList() {
        return list;
    }

    public void setList(List<ImageResult> list) {
        this.list = list;
    }

    public GetDetailResult(int id, String description, int barberId, int userId, int bookingId, List<ImageResult> list) {
        this.id = id;
        this.description = description;
        this.barberId = barberId;
        this.userId = userId;
        this.bookingId = bookingId;
        this.list = list;
    }

    public GetDetailResult() {
    }
}
