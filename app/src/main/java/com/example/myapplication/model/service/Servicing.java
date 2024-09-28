package com.example.myapplication.model.service;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Servicing implements Serializable {
    private int id;
    private String name;
    private int price;
    private String description;
    private String url;
    @SerializedName("category_id")
    private int categoryId;

    @Override
    public String toString() {
        return "Servicing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Servicing(int id, String name, int price, String description, String url, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.url = url;
        this.categoryId = categoryId;
    }
}
