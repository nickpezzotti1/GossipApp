package com.example.anew.gossipapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PostActivity extends AppCompatActivity {
    private Post post;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        post = (Post) getIntent().getExtras().get("post");

        message = ((TextView) findViewById(R.id.postMessage));
        message.setText(post.getMessage());
    }
}
