package com.example.a1.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class userModel {

    @SerializedName("SID")
    @Expose
    private String SID;
    @SerializedName("permission")
    @Expose
    private String permission;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("location")
    @Expose
    private String location;

    public String getSID() {
        return SID;
    }

    public String getPermission() {
        return permission;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

}
