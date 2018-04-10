package com.example.anew.kisscollegelondon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng currentPositionSelected;
    private Marker currentMarker;
    private static ArrayList<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        
        
        // onclick listener for refresh button
        findViewById(R.id.refresh).setOnClickListener(e -> refresh());

        // onclick listener for refresh button
        findViewById(R.id.add_post_button).setOnClickListener(e -> createPost(currentPositionSelected)); //replace with current position
    }

    private void refresh() {
        mMap.clear();
        posts.forEach( e -> addMarker(e.getPost(), e.getPosition()));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //make the map clickable so you can get coordinate of what is clicked
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                currentPositionSelected = point;
                if (currentMarker != null) {
                    currentMarker.remove();
                    }
                // create, add and assign the marke
                currentMarker = mMap.addMarker(new MarkerOptions()
                        .position(point)
                        .draggable(false).visible(true));
            }
        });


        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(51.505924, -0.112000)));
    }

    private void createPost(LatLng currentPositionSelected) {
        if (currentPositionSelected == null) {
            Toast.makeText(this, "Please select a position first", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, AddPostActivity.class);
        intent.putExtra("position", currentPositionSelected);
        startActivity(intent);
    }

    public void addMarker(String name, double longitude, double latitude) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(longitude, latitude)).title(name));
    }

    public void addMarker(String name, LatLng position) {
        mMap.addMarker(new MarkerOptions().position(position).title(name));
    }

    public MapsActivity getThis() {
        return this;
    }

    public static void addPostsToList(Post postToAdd) {
        posts.add(postToAdd);
    }
}
