package com.example.a1.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class updateContainerModel {

    @SerializedName("SID")
    @Expose
    private String SID;
    @SerializedName("containerID")
    @Expose
    private int containerID;

    public String getSID() {
        return SID;
    }

    public int getcontainerID() {
        return containerID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public void setcontainerID(int containerID) {
        this.containerID = containerID;
    }

}
