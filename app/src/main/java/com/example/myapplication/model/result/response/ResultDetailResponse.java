package com.example.myapplication.model.result.response;

import java.io.Serializable;

public class ResultDetailResponse implements Serializable {
    private GetDetailResult data;

    public GetDetailResult getData() {
        return data;
    }

    public void setData(GetDetailResult data) {
        this.data = data;
    }

    public ResultDetailResponse() {
    }

    public ResultDetailResponse(GetDetailResult data) {
        this.data = data;
    }
}
