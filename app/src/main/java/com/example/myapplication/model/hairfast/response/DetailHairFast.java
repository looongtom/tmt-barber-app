package com.example.myapplication.model.hairfast.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetailHairFast implements Serializable {
    @SerializedName("self_img")
    private String SelfImgCloud;
    @SerializedName("shape_img")
    private String ShapeImgCloud;
    @SerializedName("color_img")
    private String ColorImgCloud;
    @SerializedName("generated_img")
    private String GeneratedImgCloud;

    public String getSelfImgCloud() {
        return SelfImgCloud;
    }

    public String getShapeImgCloud() {
        return ShapeImgCloud;
    }

    public String getColorImgCloud() {
        return ColorImgCloud;
    }

    public String getGeneratedImgCloud() {
        return GeneratedImgCloud;
    }

    public DetailHairFast(String selfImgCloud, String shapeImgCloud, String colorImgCloud, String generatedImgCloud) {
        SelfImgCloud = selfImgCloud;
        ShapeImgCloud = shapeImgCloud;
        ColorImgCloud = colorImgCloud;
        GeneratedImgCloud = generatedImgCloud;
    }

    public DetailHairFast() {
    }
}
