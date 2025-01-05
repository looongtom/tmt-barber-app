package com.example.myapplication.model.hairfast.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetListGeneratedResult {
    @SerializedName("data")
    private List<GeneratedResult> generateHairResponseList;

    public List<GeneratedResult> getGenerateHairResponseList() {
        return generateHairResponseList;
    }

    public void setGenerateHairResponseList(List<GeneratedResult> generateHairResponseList) {
        this.generateHairResponseList = generateHairResponseList;
    }

    public GetListGeneratedResult(List<GeneratedResult> generateHairResponseList) {
        this.generateHairResponseList = generateHairResponseList;
    }
}
