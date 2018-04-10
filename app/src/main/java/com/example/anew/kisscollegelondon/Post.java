package com.example.anew.kisscollegelondon;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by new on 4/9/18.
 */

public class Post {
    private LatLng position;
    private String message;


    public Post(LatLng position, String post) {
        this.position = position;
        this.message = post;
    }

    public LatLng getPosition() {
        return position;
    }

    public String getPost() {
        return message;
    }
}
