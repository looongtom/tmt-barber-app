package com.example.myapplication.model.hairfast.request;

import com.google.gson.annotations.SerializedName;

public class RequestGetListGeneratedResult {
    @SerializedName("account_id")
    private Integer AccountId;

    public RequestGetListGeneratedResult(Integer accountId) {
        AccountId = accountId;
    }
}
