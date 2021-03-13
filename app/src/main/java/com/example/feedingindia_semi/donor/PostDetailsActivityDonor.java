package com.example.feedingindia_semi.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.donor.datamodels.CharityData;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class PostDetailsActivityDonor extends AppCompatActivity {

    private Toolbar mToolbar;
    private DatabaseReference mUsersDatabase;
    private FirebaseUser mCurrentUser;
    private TextView mRequirement;
    private Button mLike, mDislike;
    private String key;
    private TextView postCharityName, postCharityDescription;
    ImageView postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details_donor);
        Intent intent =getIntent();
        key = intent.getStringExtra("key");

        mToolbar = findViewById(R.id.donor_post_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Charity Posts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        postCharityName = findViewById(R.id.post_charity_name);
        postCharityDescription = findViewById(R.id.post_charity_description);
        postImage = findViewById(R.id.post_charity_image);


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

            }
        });
        try {
            getPostData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPostData() throws Exception{
        if(this.key == null){
            return;
        }
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity");
        mUsersDatabase.child(this.key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CharityData charityData = snapshot.getValue(CharityData.class);
                if(charityData != null){
                    //data fethc hogya yaha
                    Log.i("Data",charityData.getCharity_name());
                    postCharityName.setText(charityData.getCharity_name());
                    Log.i("Data",charityData.getPost_description());
                    postCharityDescription.setText(charityData.getPost_description());
                    Log.i("Data",charityData.getPost_image());
                    Picasso.get().load(charityData.getPost_image()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.default_image).into(postImage);


                }else {
                    Log.e("error","null data");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}