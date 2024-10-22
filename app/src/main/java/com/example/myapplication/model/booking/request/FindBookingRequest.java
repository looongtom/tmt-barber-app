package com.example.myapplication.model.booking.request;

import com.google.gson.annotations.SerializedName;

public class FindBookingRequest {
    private Integer page;
    @SerializedName("pageSize")
    private Integer pageSize;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public FindBookingRequest(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public FindBookingRequest() {
    }
}
