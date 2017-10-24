package com.example.a1.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.List;

public class containerModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("shipID")
    @Expose
    private int shipID;
    @SerializedName("ordersIDArray")
    @Expose
    private List<Integer> ordersIDArray = null;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("destinationsArray")
    @Expose
    private List<String> destinationsArray = null;
    @SerializedName("pathsArray")
    @Expose
    private List<List<String>> pathsArray = null;
    @SerializedName("properties")
    @Expose
    private List<Object> properties = null;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("volume")
    @Expose
    private int volume;
    @SerializedName("available_weigth")
    @Expose
    private int availableWeigth;
    @SerializedName("available_volume")
    @Expose
    private int availableVolume;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShipID() {
        return shipID;
    }

    public void setShipID(int shipID) {
        this.shipID = shipID;
    }

    public List<Integer> getOrdersIDArray() {
        return ordersIDArray;
    }

    public void setOrdersIDArray(List<Integer> ordersIDArray) {
        this.ordersIDArray = ordersIDArray;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDestinationsArray() {
        return destinationsArray;
    }

    public void setDestinationsArray(List<String> destinationsArray) {
        this.destinationsArray = destinationsArray;
    }

    public List<List<String>> getPathsArray() {
        return pathsArray;
    }

    public void setPathsArray(List<List<String>> pathsArray) {
        this.pathsArray = pathsArray;
    }

    public List<Object> getProperties() {
        return properties;
    }

    public void setProperties(List<Object> properties) {
        this.properties = properties;
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

    public int getAvailableWeigth() {
        return availableWeigth;
    }

    public void setAvailableWeigth(int availableWeigth) {
        this.availableWeigth = availableWeigth;
    }

    public int getAvailableVolume() {
        return availableVolume;
    }

    public void setAvailableVolume(int availableVolume) {
        this.availableVolume = availableVolume;
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
