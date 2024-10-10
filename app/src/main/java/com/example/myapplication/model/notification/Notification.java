package com.example.myapplication.model.notification;

import com.example.myapplication.model.booking.response.BookingResponse;
import com.example.myapplication.model.hairfast.HairFastWS;

import java.io.Serializable;

public class Notification implements Serializable {
    public static final String BookingType = "booking";
    public static final String HairfastType = "hairfast";
    private String title, message, timestamp,type;
    private HairFastWS hairFastWS;
    private BookingResponse bookingResponse;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Notification(String title, String message, String timestamp, String type) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
    }

    public Notification(String title, String message, String timestamp, String type, BookingResponse bookingResponse) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
        this.bookingResponse = bookingResponse;
    }

    public Notification(String title, String message, String timestamp, String type, HairFastWS hairFastWS) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
        this.hairFastWS = hairFastWS;
    }

    public HairFastWS getHairFastWS() {
        return hairFastWS;
    }

    public void setHairFastWS(HairFastWS hairFastWS) {
        this.hairFastWS = hairFastWS;
    }

    public BookingResponse getBookingResponse() {
        return bookingResponse;
    }

    public void setBookingResponse(BookingResponse bookingResponse) {
        this.bookingResponse = bookingResponse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }



    @Override
    public String toString() {
        return "Notification{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
