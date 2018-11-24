package com.krishna.sdlabs.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomResultModel {

    int type;//0 for user, 1 for single item, 2 for double item
    CustomUserModel customUserModel;

    public class CustomUserModel {
        @SerializedName("name")
        String userName;

        @SerializedName("image")
        String userImage;

        public CustomUserModel(String userName, String userImage) {
            this.userName = userName;
            this.userImage = userImage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }
    }

    String singleUrl;
    ArrayList<String> doubleUrlList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CustomUserModel getCustomUserModel() {
        return customUserModel;
    }

    public void setCustomUserModel(CustomUserModel customUserModel) {
        this.customUserModel = customUserModel;
    }

    public String getSingleUrl() {
        return singleUrl;
    }

    public void setSingleUrl(String singleUrl) {
        this.singleUrl = singleUrl;
    }

    public ArrayList<String> getDoubleUrlList() {
        return doubleUrlList;
    }

    public void setDoubleUrlList(ArrayList<String> doubleUrlList) {
        this.doubleUrlList = doubleUrlList;
    }
}
