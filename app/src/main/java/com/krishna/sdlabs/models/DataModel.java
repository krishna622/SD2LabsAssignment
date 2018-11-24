package com.krishna.sdlabs.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataModel {

    @SerializedName("users")
    ArrayList<UserModel> userList;

    @SerializedName("has_more")
    boolean hasMore;

    public ArrayList<UserModel> getUserList() {
        return userList;
    }

    public boolean isHasMore() {
        return hasMore;
    }
}
