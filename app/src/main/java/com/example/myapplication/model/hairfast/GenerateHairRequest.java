package com.example.myapplication.model.hairfast;

import com.google.gson.annotations.SerializedName;

public class GenerateHairRequest {
    @SerializedName("self_img")
    private String SelfImg;
    @SerializedName("shape_img")
    private String ShapeImg;
    @SerializedName("color_img")
    private String ColorImg;
}
