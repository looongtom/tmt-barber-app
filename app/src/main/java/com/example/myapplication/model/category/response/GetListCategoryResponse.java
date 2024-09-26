package com.example.myapplication.model.category.response;

import com.example.myapplication.model.category.Category;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetListCategoryResponse implements Serializable {
    @SerializedName("data")
    private List<Category> listCategory;

    @Override
    public String toString() {
        return "GetListCategoryResponse{" +
                "listCategory=" + listCategory +
                '}';
    }

    public List<Category> getListCategory() {
        return listCategory;
    }

    public void setListCategory(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    public GetListCategoryResponse(List<Category> listCategory) {
        this.listCategory = listCategory;
    }
}
