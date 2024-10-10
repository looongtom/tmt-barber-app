package com.example.myapplication.model.hairfast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HairFastWS implements Serializable {
    @SerializedName("self_img_cloud")
    private String SelfImgCloud;
    @SerializedName("shape_img_cloud")
    private String ShapeImgCloud;
    @SerializedName("color_img_cloud")
    private String ColorImgCloud;
    @SerializedName("generated_img_cloud")
    private String GeneratedImgCloud;

    public HairFastWS() {
    }

    public HairFastWS(String selfImgCloud, String shapeImgCloud, String colorImgCloud, String generatedImgCloud) {
        SelfImgCloud = selfImgCloud;
        ShapeImgCloud = shapeImgCloud;
        ColorImgCloud = colorImgCloud;
        GeneratedImgCloud = generatedImgCloud;
    }

    @Override
    public String toString() {
        return "HairFastWS{" +
                "SelfImgCloud='" + SelfImgCloud + '\'' +
                ", ShapeImgCloud='" + ShapeImgCloud + '\'' +
                ", ColorImgCloud='" + ColorImgCloud + '\'' +
                ", GeneratedImgCloud='" + GeneratedImgCloud + '\'' +
                '}';
    }

    public String getSelfImgCloud() {
        return SelfImgCloud;
    }

    public void setSelfImgCloud(String selfImgCloud) {
        SelfImgCloud = selfImgCloud;
    }

    public String getShapeImgCloud() {
        return ShapeImgCloud;
    }

    public void setShapeImgCloud(String shapeImgCloud) {
        ShapeImgCloud = shapeImgCloud;
    }

    public String getColorImgCloud() {
        return ColorImgCloud;
    }

    public void setColorImgCloud(String colorImgCloud) {
        ColorImgCloud = colorImgCloud;
    }

    public String getGeneratedImgCloud() {
        return GeneratedImgCloud;
    }

    public void setGeneratedImgCloud(String generatedImgCloud) {
        GeneratedImgCloud = generatedImgCloud;
    }
}
