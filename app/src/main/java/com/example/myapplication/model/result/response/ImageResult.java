package com.example.myapplication.model.result.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageResult implements Serializable {
    private int id;
    private String url;
    @SerializedName("result_id")
    private int resultId;

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

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public ImageResult(int id, String url, int resultId) {
        this.id = id;
        this.url = url;
        this.resultId = resultId;
    }

    public ImageResult() {
    }
}
