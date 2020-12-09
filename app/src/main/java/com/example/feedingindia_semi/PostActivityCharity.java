package com.example.feedingindia_semi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class PostActivityCharity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_charity);

        mToolbar = findViewById(R.id.post_requirements_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("-----");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
