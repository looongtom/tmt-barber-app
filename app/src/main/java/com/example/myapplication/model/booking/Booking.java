package com.example.myapplication.model.booking;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Booking implements Serializable {
    private Integer id;
    @SerializedName("customer_id")
    private Integer customerId;
    @SerializedName("barber_id")
    private Integer barberId;
    @SerializedName("result_id")
    private Integer resultId;
    @SerializedName("slot_id")
    private Integer timeSlotId;
    private String status;
    private Integer price;
    @SerializedName("feedback_id")
    private Integer feedbackId;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("updated_at")
    private Long updatedAt;
    @SerializedName("preview_id")
    private Integer previewId;

    public Integer getPreviewId() {
        return previewId;
    }

    public void setPreviewId(Integer previewId) {
        this.previewId = previewId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getBarberId() {
        return barberId;
    }

    public void setBarberId(Integer barberId) {
        this.barberId = barberId;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Booking() {
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Booking(Integer id, Integer customerId, Integer barberId, Integer resultId, Integer timeSlotId, String status, Integer price, Integer feedbackId, Long createdAt, Long updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.barberId = barberId;
        this.resultId = resultId;
        this.timeSlotId = timeSlotId;
        this.status = status;
        this.price = price;
        this.feedbackId = feedbackId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
