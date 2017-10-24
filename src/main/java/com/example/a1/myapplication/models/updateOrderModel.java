package com.example.a1.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class updateOrderModel {

    @SerializedName("SID")
    @Expose
    private String SID;
    @SerializedName("containerID")
    @Expose
    private int trackID;
    @SerializedName("action")
    @Expose
    private String action;

    public String getSID() {
        return SID;
    }

    public int getTrackID() {
        return trackID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public void setTrackID(int trackID) {
        this.trackID = trackID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
