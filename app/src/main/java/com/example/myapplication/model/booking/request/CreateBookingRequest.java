package com.example.myapplication.model.booking.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Set;

public class CreateBookingRequest {
    @SerializedName("customer_id")
    private Integer customerId;
    @SerializedName("barber_id")
    private Integer barberId;
    @SerializedName("slot_id")
    private Integer timeSlotId;
    private String status;
    private Integer price;
    @SerializedName("list_service")
    private Set<Integer> listService;

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

    public Set<Integer> getListService() {
        return listService;
    }

    public void setListService(Set<Integer> listService) {
        this.listService = listService;
    }

    public CreateBookingRequest(Integer customerId, Integer barberId, Integer timeSlotId, String status, Integer price, Set<Integer> listService) {
        this.customerId = customerId;
        this.barberId = barberId;
        this.timeSlotId = timeSlotId;
        this.status = status;
        this.price = price;
        this.listService = listService;
    }

    public CreateBookingRequest() {
    }
}
