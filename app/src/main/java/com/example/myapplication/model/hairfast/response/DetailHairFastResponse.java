package com.example.myapplication.model.hairfast.response;

import java.io.Serializable;

public class DetailHairFastResponse implements Serializable {
    private DetailHairFast data;

    public DetailHairFastResponse(DetailHairFast data) {
        this.data = data;
    }

    public DetailHairFast getData() {
        return data;
    }

    public void setData(DetailHairFast data) {
        this.data = data;
    }

    public DetailHairFastResponse() {
    }
}
