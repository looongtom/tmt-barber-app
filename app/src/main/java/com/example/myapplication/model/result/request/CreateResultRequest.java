package com.example.myapplication.model.result.request;

import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;

public class CreateResultRequest {
    @SerializedName("list_img")
    private MultipartBody.Part[] listImg;
    @SerializedName("barber_id")
    private String barberId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("booking_id")
    private String bookingId;
    @SerializedName("description")
    private String description;

    public MultipartBody.Part[] getListImg() {
        return listImg;
    }

    public void setListImg(MultipartBody.Part[] listImg) {
        this.listImg = listImg;
    }

    public String getBarberId() {
        return barberId;
    }

    public void setBarberId(String barberId) {
        this.barberId = barberId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CreateResultRequest(MultipartBody.Part[] listImg, String barberId, String userId, String bookingId, String description) {
        this.listImg = listImg;
        this.barberId = barberId;
        this.userId = userId;
        this.bookingId = bookingId;
        this.description = description;
    }
}
