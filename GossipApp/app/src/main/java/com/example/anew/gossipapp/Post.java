package com.example.anew.gossipapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by new on 4/9/18.
 */

public class Post implements Parcelable{
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

    protected Post(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

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
