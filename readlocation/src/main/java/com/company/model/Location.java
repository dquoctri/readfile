package com.company.model;

import org.locationtech.jts.geom.Coordinate;

public class Location {
    private String name;
    private String serial;
    private String assetTags;
    private String technicalProductKey;
    private String organizations;
    private String address;
    private Coordinate coordinate;

    public Location(){}

    public Location(String name, String serial, Coordinate coordinate){
        this.name = name;
        this.serial = serial;
        this.coordinate = coordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getAssetTags() {
        return assetTags;
    }

    public void setAssetTags(String assetTags) {
        this.assetTags = assetTags;
    }

    public String getTechnicalProductKey() {
        return technicalProductKey;
    }

    public void setTechnicalProductKey(String technicalProductKey) {
        this.technicalProductKey = technicalProductKey;
    }

    public String getOrganizations() {
        return organizations;
    }

    public void setOrganizations(String organizations) {
        this.organizations = organizations;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
