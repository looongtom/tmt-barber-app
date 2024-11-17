package com.example.myapplication.model.result.response;

import java.io.Serializable;

public class CreateResultResponseData implements Serializable {
    private CreateResultResponse data;

    public CreateResultResponse getData() {
        return data;
    }

    public void setData(CreateResultResponse data) {
        this.data = data;
    }

    public CreateResultResponseData(CreateResultResponse data) {
        this.data = data;
    }
}
