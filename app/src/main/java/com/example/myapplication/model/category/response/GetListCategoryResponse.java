package com.example.myapplication.model.category.response;

import com.example.myapplication.model.category.Category;
import com.example.myapplication.model.service.Servicing;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GetListCategoryResponse implements Serializable {
    @SerializedName("data")
    private Map<String, List<Servicing>> serviceMap ;

    public GetListCategoryResponse(Map<String, List<Servicing>> serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    public String toString() {
        return "GetListCategoryResponse{" +
                "serviceMap=" + serviceMap +
                '}';
    }

    public Map<String, List<Servicing>> getServiceMap() {
        return serviceMap;
    }

    public void setServiceMap(Map<String, List<Servicing>> serviceMap) {
        this.serviceMap = serviceMap;
    }
}
