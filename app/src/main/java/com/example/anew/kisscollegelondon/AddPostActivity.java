package com.example.anew.kisscollegelondon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class AddPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final LatLng position = (LatLng) getIntent().getExtras().get("position");
        setContentView(R.layout.add_post_activity);
        findViewById(R.id.postButton).setOnClickListener(e -> post(position));
    }

    private void post(LatLng position) {
        EditText et = findViewById(R.id.editText);
        Post newPost = new Post(position, et.getText().toString());
        MapsActivity.addPostsToList(newPost);

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

        Toast.makeText(this, et.getText().toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "" + position.longitude, Toast.LENGTH_SHORT).show();
    }
}
