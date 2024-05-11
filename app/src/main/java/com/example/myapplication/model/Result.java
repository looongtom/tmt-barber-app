package com.example.myapplication.model;

public class Result {
    private int id;
    private String imageFront;
    private String imageBack;
    private String imageRight;
    private String imageLeft;

    public Result(int id, String imageFront, String imageBack, String imageRight, String imageLeft) {
        this.id = id;
        this.imageFront = imageFront;
        this.imageBack = imageBack;
        this.imageRight = imageRight;
        this.imageLeft = imageLeft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageFront() {
        return imageFront;
    }

    public void setImageFront(String imageFront) {
        this.imageFront = imageFront;
    }

    public String getImageBack() {
        return imageBack;
    }

    public void setImageBack(String imageBack) {
        this.imageBack = imageBack;
    }

    public String getImageRight() {
        return imageRight;
    }

    public void setImageRight(String imageRight) {
        this.imageRight = imageRight;
    }

    public String getImageLeft() {
        return imageLeft;
    }

    public void setImageLeft(String imageLeft) {
        this.imageLeft = imageLeft;
    }

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", imageFront='" + imageFront + '\'' +
                ", imageBack='" + imageBack + '\'' +
                ", imageRight='" + imageRight + '\'' +
                ", imageLeft='" + imageLeft + '\'' +
                '}';
    }
}
