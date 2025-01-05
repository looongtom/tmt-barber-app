package com.example.myapplication.model.hairfast.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GeneratedResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id  ;
    @SerializedName("self_img")
    private String SelfImg;
    @SerializedName("shape_img")
    private String ShapeImg;
    @SerializedName("color_img")
    private String ColorImg;
    @SerializedName("generated_img")
    private String GeneratedImg;
    @SerializedName("account_id")
    private Integer AccountId;

    @Override
    public String toString() {
        return "GeneratedResult{" +
                "id=" + id +
                ", SelfImg='" + SelfImg + '\'' +
                ", ShapeImg='" + ShapeImg + '\'' +
                ", ColorImg='" + ColorImg + '\'' +
                ", GeneratedImg='" + GeneratedImg + '\'' +
                ", AccountId=" + AccountId +
                ", CreatedAt=" + CreatedAt +
                '}';
    }

    @SerializedName("created_at")
    private Long CreatedAt;

    public String getSelfImg() {
        return SelfImg;
    }

    public void setSelfImg(String selfImg) {
        SelfImg = selfImg;
    }

    public String getShapeImg() {
        return ShapeImg;
    }

    public void setShapeImg(String shapeImg) {
        ShapeImg = shapeImg;
    }

    public String getColorImg() {
        return ColorImg;
    }

    public void setColorImg(String colorImg) {
        ColorImg = colorImg;
    }

    public String getGeneratedImg() {
        return GeneratedImg;
    }

    public void setGeneratedImg(String generatedImg) {
        GeneratedImg = generatedImg;
    }

    public Integer getAccountId() {
        return AccountId;
    }

    public void setAccountId(Integer accountId) {
        AccountId = accountId;
    }

    public Long getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Long createdAt) {
        CreatedAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GeneratedResult(String selfImg, String shapeImg, String colorImg, String generatedImg, Integer accountId, Long createdAt) {
        SelfImg = selfImg;
        ShapeImg = shapeImg;
        ColorImg = colorImg;
        GeneratedImg = generatedImg;
        AccountId = accountId;
        CreatedAt = createdAt;
    }
}
