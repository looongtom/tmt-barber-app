package com.example.myapplication.model.category;

import com.example.myapplication.model.service.Servicing;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private int id;
    private String name;
    private List<Servicing> listServicing;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", listServicing=" + listServicing +
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

    public List<Servicing> getListServicing() {
        return listServicing;
    }

    public void setListServicing(List<Servicing> listServicing) {
        this.listServicing = listServicing;
    }

    public Category(int id, String name, List<Servicing> listServicing) {
        this.id = id;
        this.name = name;
        this.listServicing = listServicing;
    }

    public Category() {
    }
}
