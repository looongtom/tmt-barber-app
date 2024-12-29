package com.example.myapplication.model.result;

import java.io.Serializable;

public class Result implements Serializable {
    private int id;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Result(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public Result(String url) {
        this.url = url;
    }

    public Result() {
    }
}
