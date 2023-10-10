package com.example.taskmanagerpro.ui;

public class LocationList {
    private String uid;
    private String date;
    private String time;
    private String title;
    private String lat;
    private String long1;
    private String address;
    private String description;
    private String push;
    private String status;

    public LocationList(String uid, String date, String time, String title, String lat, String long1, String address, String description, String push, String status) {
        this.uid = uid;
        this.date = date;
        this.time = time;
        this.title = title;
        this.lat = lat;
        this.long1 = long1;
        this.address = address;
        this.description = description;
        this.push = push;
        this.status = status;
    }

    public String getPush() {
        return push;
    }

    public void setPush(String push) {
        this.push = push;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong1() {
        return long1;
    }

    public void setLong1(String long1) {
        this.long1 = long1;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
