package com.example.feedingindia_semi.donor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedingindia_semi.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class PostDetailsActivityDonor extends AppCompatActivity {

    private Toolbar mToolbar;
    private DatabaseReference mUsersDatabase;
    private FirebaseUser mCurrentUser;
    private TextView mRequirement;
    private Button mLike, mDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details_donor);

        mToolbar = findViewById(R.id.donor_post_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Charity Posts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mLike = findViewById(R.id.donor_post_like);

        mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Toast.makeText(PostDetailsActivityDonor.this,"You Liked it!",Toast.LENGTH_LONG).show();

            }
        });

        mDislike = findViewById(R.id.donor_post_dislike);

        mDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Toast.makeText(PostDetailsActivityDonor.this,"You Disliked it!",Toast.LENGTH_LONG).show();
;
            }
        });
    }
}