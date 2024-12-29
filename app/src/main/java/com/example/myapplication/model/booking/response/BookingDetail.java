package com.example.myapplication.model.booking.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingDetail {
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
    @SerializedName("booked_date")
    private String bookedDate;
    @SerializedName("start_time")
    private String startTime;
    private String status;
    private Integer price;
    @SerializedName("feedback_id")
    private Integer feedbackId;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("updated_at")
    private Long updatedAt;
    @SerializedName("list_services")
    private List<ServicingResponse> listServiceStruct;

    public String getStatus() {
        return status;
    }

    public Integer getPrice() {
        return price;
    }

    public List<ServicingResponse> getListServiceStruct() {
        return listServiceStruct;
    }

    public void setListServiceStruct(List<ServicingResponse> listServiceStruct) {
        this.listServiceStruct = listServiceStruct;
    }

    public BookingDetail() {
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

    public String getStartTime() {
        return startTime;
    }
}
