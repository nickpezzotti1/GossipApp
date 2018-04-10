package com.example.anew.gossipapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by new on 4/9/18.
 */

public class Post {
    private double latitude;
    private double longitude;
    private String message;


    public Post(LatLng position, String post) {
        latitude = position.latitude;
        longitude = position.longitude;
        this.message = post;
    }

    public Post() {
        this(new LatLng(0, 0), "");
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getMessage() {
        return message;
    }

    public LatLng getPosition() {
        return new LatLng(latitude, longitude);
    }

    public void setLatitude(double latitude) {

        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
