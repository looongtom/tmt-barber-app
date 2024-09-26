package com.example.myapplication.model.account.response;

import com.example.myapplication.model.account.Account;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetListBarberResponse {
    @SerializedName("data")
    private List<Account> listBarber;

    public GetListBarberResponse() {

    }

    public List<Account> getListBarber() {
        return listBarber;
    }

    public void setListBarber(List<Account> listBarber) {
        this.listBarber = listBarber;
    }
}
