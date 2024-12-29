package com.example.myapplication.model.result.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

public class CreateResultResponse implements Serializable {
    @SerializedName("barber_id")
    private int barberId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("booking_id")
    private int bookingId;
    private String description;
    @SerializedName("list_img")
    private ImageResult[] listImg;

    public int getBarberId() {
        return barberId;
    }

    @Override
    public String toString() {
        return "CreateResultResponse{" +
                "barberId=" + barberId +
                ", userId=" + userId +
                ", bookingId=" + bookingId +
                ", description='" + description + '\'' +
                ", listImg=" + Arrays.toString(listImg) +
                '}';
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageResult[] getListImg() {
        return listImg;
    }

    public void setListImg(ImageResult[] listImg) {
        this.listImg = listImg;
    }

    public CreateResultResponse(int barberId, int userId, int bookingId, String description, ImageResult[] listImg) {
        this.barberId = barberId;
        this.userId = userId;
        this.bookingId = bookingId;
        this.description = description;
        this.listImg = listImg;
    }
}
