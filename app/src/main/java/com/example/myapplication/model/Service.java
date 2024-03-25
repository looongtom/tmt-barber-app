package com.example.myapplication.model;

import java.io.Serializable;

public class Service implements Serializable {
    private int id;
    private String name;
    private Double price;
    private String description;
    private String filePath;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Service(int id, String name, Double price, String description, String filePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.filePath = filePath;
    }

    public Service() {
    }
}
