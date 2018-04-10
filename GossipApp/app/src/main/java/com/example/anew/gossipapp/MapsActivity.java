package com.example.anew.gossipapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; // this is the map

    // this marker is like a mouse: it displays the location where the post is going to be posted on
    private Marker currentMarker;

    // This holds all the post currently displayed on the map
    private static ArrayList<Post> posts = new ArrayList<>();

    private static FirebaseDatabase database = FirebaseDatabase.getInstance(); // instance of db
    private static DatabaseReference referenceToDatabase = database.getReference(); // reference of db

    private final String TAG = "databaseListener";

    private boolean mapCreated = false;


    /**
     * This is run when the activity is loaded; it loads the map and sets the on click listeners.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // onclick-listener add post button
        findViewById(R.id.add_post_button).setOnClickListener(e -> createPost(currentMarker.getPosition())); //replace with current position

        // The following code adds a listener to the Firebase database: every time it's updated it
        // updates the map for all the users currently using the map.
        referenceToDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                refresh(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.502465, -0.110058), 13));

        //make the map clickable so you can see where your marker is going to be placed
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                if (currentMarker != null) {
                    currentMarker.remove();
                }
                // create, add and assign the marke
                currentMarker = mMap.addMarker(new MarkerOptions()
                        .position(point)
                        .draggable(false).visible(true));
            }
        });

        mMap.setOnMarkerClickListener(marker -> {
            Toast.makeText(this, "Marker Clicked", Toast.LENGTH_SHORT).show();
            Post postToReturn = findPost(marker.getPosition());
            if (postToReturn==null) {
                Toast.makeText(this, "Post No Longer Available", Toast.LENGTH_SHORT).show();
                return false;
            }
            Intent intent = new Intent(this, PostActivity.class);
            intent.putExtra("post", postToReturn);
            startActivity(intent);
            return false;
        });
    }

    public void onResume() {
        super.onResume();
        if (mapCreated) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.502465, -0.110058), 13));
        }
        mapCreated = true;
    }

    /**
     * This method is called every time the databased is modified. It reloads all the markers in
     * the correct postion and resets the list of all posts and populating with the posts currently
     * in the database.
     * @param dataSnapshot snapshot of the current state of the database.
     */
    private void refresh(DataSnapshot dataSnapshot) {
        mMap.clear();
        posts.clear();
        long currentTime = System.currentTimeMillis();
        for (DataSnapshot ds : dataSnapshot.getChildren()) { // all the 1st level of the tree
            Post post = ds.getValue(Post.class); // gets the value that stores object of type Post
            if (post.getExpirationTime() < currentTime) {
                System.out.println(ds.getKey());
                dataSnapshot.child(ds.getKey());
            }
            else {
                posts.add(post); // adds it to the list of all posts
                addMarker(post.getMessage(), post.getPosition()); // creates the marker
            }
        }
    }

    /**
     * This method returns a position currently displayed on the map given the position (ie matching
     * longitude and latitude). This method should never return null as of now because it is only
     * called when a marker is clicked, and a marker is there only if the post is locally displayed.
     * @param position location of the post
     * @return The post corresponding to the location. Null if none found.
     */
    private Post findPost(LatLng position) {
        Post postToReturn = null;
        for (Post post : posts) {
            if (post.getPosition().equals(position)) return post;
        }
        return null;
    }

    /**
     * It is called when the user wants to create a post after having clicked on the map and the
     * newPost button. It opnes the add_post_activity.
     * @param currentPositionSelected creates the post given the location
     */
    private void createPost(LatLng currentPositionSelected) {
        if (currentPositionSelected == null) {
            Toast.makeText(this, "Please select a position first", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, AddPostActivity.class);
        intent.putExtra("position", currentPositionSelected);
        startActivity(intent);
    }

    /**
     * Add a marker to the map.
     * @param name The title of the marker.
     * @param longitude Longitude of the marker.
     * @param latitude Latitude of the marker.
     */
    public void addMarker(String name, double longitude, double latitude) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(longitude, latitude)).title(name));
    }

    /**
     * Add a marker to the map.
     * @param name The title of the marker.
     * @param position The position of the marker.
     */
    public void addMarker(String name, LatLng position) {
        mMap.addMarker(new MarkerOptions().position(position).title(name));
    }

    /**
     * Add a post the local list storing all the posts in the database. This list works almost like
     * a cache avoiding the client to re-download the database every time.
     */
    public static void addPostsToList(Post postToAdd) {
        referenceToDatabase.push().setValue(postToAdd);
    }
}
