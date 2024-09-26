package com.example.myapplication.model.hairfast;

import com.google.gson.annotations.SerializedName;


public class GenerateHairResponse {
    @SerializedName("data")
   private HairResponse hairResponse;

    public HairResponse getHairResponse() {
        return hairResponse;
    }

    public void setHairResponse(HairResponse hairResponse) {
        this.hairResponse = hairResponse;
    }

    public GenerateHairResponse() {
    }
}
