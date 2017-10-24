package com.example.a1.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

public class orderModel {

    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("reciever")
    @Expose
    private String reciever;
    @SerializedName("trackID")
    @Expose
    private int trackID;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("volume")
    @Expose
    private int volume;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("containerID")
    @Expose
    private int containerID;
    @SerializedName("reg_date")
    @Expose
    private String regDate;
    @SerializedName("send_date")
    @Expose
    private String sendDate;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("recieve_date")
    @Expose
    private String recieveDate;
    @SerializedName("status")
    @Expose
    private String status;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public int getTrackID() {
        return trackID;
    }

    public void setTrackID(int trackID) {
        this.trackID = trackID;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getContainerID() {
        return containerID;
    }

    public void setContainerID(int containerID) {
        this.containerID = containerID;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRecieveDate() {
        return recieveDate;
    }

    public void setRecieveDate(String recieveDate) {
        this.recieveDate = recieveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            if(field.getName()=="$change"||field.getName()=="serialVersionUID") continue;
                try {
                    result.append( field.getName() );
                    result.append(": ");
                    //requires access to private field:
                    result.append( field.get(this) );
                } catch ( IllegalAccessException ex ) {
                    System.out.println(ex);
                }
            result.append(newLine);
        }

        return result.toString();
    }

}
