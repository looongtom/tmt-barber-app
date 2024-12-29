package com.example.myapplication.model.account.response;

import com.example.myapplication.model.account.Account;
import com.google.gson.annotations.SerializedName;

public class GetProfileResponse {
    @SerializedName("data")
    Account account;

    public GetProfileResponse(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
