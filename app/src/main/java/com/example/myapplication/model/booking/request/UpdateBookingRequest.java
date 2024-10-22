package com.example.myapplication.model.booking.request;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class UpdateBookingRequest {
    private Integer id;
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

    public UpdateBookingRequest(Integer id, Set<Integer> listService) {
        this.id = id;
        this.listService = listService;
    }

    public UpdateBookingRequest() {
    }

    public UpdateBookingRequest(Integer id, Integer customerId, Integer barberId, Integer timeSlotId, String status, Integer price) {
        this.id = id;
        this.customerId = customerId;
        this.barberId = barberId;
        this.timeSlotId = timeSlotId;
        this.status = status;
        this.price = price;
    }

    public UpdateBookingRequest(Integer id, Integer customerId, Integer barberId, Integer timeSlotId, String status, Integer price, Set<Integer> listService) {
        this.id = id;
        this.customerId = customerId;
        this.barberId = barberId;
        this.timeSlotId = timeSlotId;
        this.status = status;
        this.price = price;
        this.listService = listService;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getBarberId() {
        return barberId;
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPrice() {
        return price;
    }

    public Set<Integer> getListService() {
        return listService;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setBarberId(Integer barberId) {
        this.barberId = barberId;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setListService(Set<Integer> listService) {
        this.listService = listService;
    }
}
