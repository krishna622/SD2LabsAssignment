package com.krishna.sdlabs.models;

import com.google.gson.annotations.SerializedName;

public class ResultModel {

    @SerializedName("status")
    boolean status;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    DataModel dataModel;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public DataModel getDataModel() {
        return dataModel;
    }
}
