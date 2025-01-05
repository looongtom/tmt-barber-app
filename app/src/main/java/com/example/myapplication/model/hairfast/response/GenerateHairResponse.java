package com.example.myapplication.model.hairfast.response;

import com.google.gson.annotations.SerializedName;


public class GenerateHairResponse {
    @SerializedName("data")
   private GeneratedResult hairResponse;

    public GeneratedResult getHairResponse() {
        return hairResponse;
    }

    public void setHairResponse(GeneratedResult hairResponse) {
        this.hairResponse = hairResponse;
    }

    public GenerateHairResponse() {
    }
}
