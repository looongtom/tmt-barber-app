package com.example.myapplication.model.booking.response;

public class ServicingResponse implements java.io.Serializable{
    private int id;
    private String name;
    private int price;
    private String description;
    private String url;

    @Override
    public String toString() {
        return "ServicingResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public ServicingResponse(int id, String name, int price, String description, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getStringId(){
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
