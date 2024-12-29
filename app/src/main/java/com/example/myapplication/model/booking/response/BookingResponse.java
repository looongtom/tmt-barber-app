package com.example.myapplication.model.booking.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BookingResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    @SerializedName("customer_id")
    private Integer customerId;
    @SerializedName("customer_name")
    private String customerName;
    @SerializedName("barber_id")
    private Integer barberId;
    @SerializedName("barber_name")
    private String barberName;
    @SerializedName("result_id")
    private Integer resultId;
    @SerializedName("slot_id")
    private Integer timeSlotId;
    @SerializedName("time_slot")
    private TimeSlotResponse timeSlot;
    private String status;
    private Integer price;
    @SerializedName("feedback_id")
    private Integer feedbackId;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("updated_at")
    private Long updatedAt;
    @SerializedName("booked_date")
    private String bookedDate;
    @SerializedName("list_service_struct")
    private List<ServicingResponse> listServiceStruct;

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

    public List<ServicingResponse> getListServiceStruct() {
        return listServiceStruct;
    }

    public void setListServiceStruct(List<ServicingResponse> listServiceStruct) {
        this.listServiceStruct = listServiceStruct;
    }

    public TimeSlotResponse getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlotResponse timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getBarberName() {
        return barberName;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public BookingResponse(Integer id, Integer customerId, String customerName, Integer barberId, String barberName, Integer resultId, Integer timeSlotId, TimeSlotResponse timeSlot, String status, Integer price, Integer feedbackId, Long createdAt, Long updatedAt, List<ServicingResponse> listServiceStruct) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.barberId = barberId;
        this.barberName = barberName;
        this.resultId = resultId;
        this.timeSlotId = timeSlotId;
        this.timeSlot = timeSlot;
        this.status = status;
        this.price = price;
        this.feedbackId = feedbackId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.listServiceStruct = listServiceStruct;
    }

    public BookingResponse() {
    }

    @Override
    public String toString() {
        return "BookingResponse{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", barberId=" + barberId +
                ", barberName='" + barberName + '\'' +
                ", resultId=" + resultId +
                ", timeSlotId=" + timeSlotId +
                ", timeSlot=" + timeSlot +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", feedbackId=" + feedbackId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", bookedDate='" + bookedDate + '\'' +
                ", listServiceStruct=" + listServiceStruct +
                '}';
    }
}
