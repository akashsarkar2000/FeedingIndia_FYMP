package com.example.feedingindia_semi.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.feedingindia_semi.charity.PostActivityCharity;
import com.example.feedingindia_semi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class CharityDescriptionActivityDonor extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private Toolbar mToolbar;
    private RecyclerView mUsersList;
    private ImageView mCharityImages;
    private TextView mCharityAddress, mCharityPhone,mCharityName,mCharityDescription;
    private Button mPostButton, mContactButton, mFoodButton, mMoneyButton;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference mFriendReqDatabase;
    private ProgressDialog mProgressDialog;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mFriendDatabase;
    private DatabaseReference mRootRef;
    private String mCurrent_state;

//    public ProfileActivity(DatabaseReference mFriendReqDatabase) {
//        this.mFriendReqDatabase = mFriendReqDatabase;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_description_donor);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.charity_description_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Charity Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final String user_id = getIntent().getStringExtra("user_id");
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(user_id);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mCharityImages = findViewById(R.id.charity_description_images);
        mCharityName = findViewById(R.id.charity_description_name);
        mCharityAddress = findViewById(R.id.charity_description_address);
        mCharityDescription = findViewById(R.id.charity_description_description);
        mCharityPhone = findViewById(R.id.charity_description_phone);

        mPostButton = findViewById(R.id.charity_description_post_button);
        mContactButton = findViewById(R.id.charity_description_contact_button);
        mFoodButton = findViewById(R.id.charity_description_food_button);
        mMoneyButton = findViewById(R.id.charity_description_money_button);

        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor").child(mAuth.getCurrentUser().getUid());
        }

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), RequirementDetailsActivityDonor.class);
                startActivity(intent);
            }
        });
        mContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), PostActivityCharity.class);
                startActivity(intent);
            }
        });
        mFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), PostActivityCharity.class);
                startActivity(intent);
            }
        });
        mMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), PostActivityCharity.class);
                startActivity(intent);
            }
        });


        mProgressDialog = new ProgressDialog(CharityDescriptionActivityDonor.this);
        mProgressDialog.setTitle("Loading Charity data");
        mProgressDialog.setMessage("Please wait while we load the charity data.");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();


        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String charity_name = Objects.requireNonNull(dataSnapshot.child("charity_name").getValue()).toString();
                String charity_address = Objects.requireNonNull(dataSnapshot.child("charity_address").getValue()).toString();
                String phone = Objects.requireNonNull(dataSnapshot.child("phone").getValue()).toString();
                String image = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                String description = Objects.requireNonNull(dataSnapshot.child("description").getValue()).toString();

                mCharityName.setText(charity_name);
                mCharityAddress.setText(charity_address);
                mCharityPhone.setText(phone);
                mCharityDescription.setText(description);

                Picasso.with(CharityDescriptionActivityDonor.this).load(image).placeholder(R.drawable.default_image).into(mCharityImages);
                mProgressDialog.dismiss();


//                mFriendReqDatabase.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        if(dataSnapshot.hasChild(user_id)){
//
//                            String req_type = Objects.requireNonNull(dataSnapshot.child(user_id).child("request_type").getValue()).toString();
//
//                            if(req_type.equals("received")){
//
//                                mCurrent_state = "req_received";
//                                mProfileSendReqBtn.setText("Accept Request");
//                                mDeclineBtn.setVisibility(View.VISIBLE);
//                                mDeclineBtn.setEnabled(true);
//
//                            }
//                            else if(req_type.equals("sent")){
//
//                                mCurrent_state = "req_sent";
//                                mProfileSendReqBtn.setText("Cancel Request");
//
//                                mDeclineBtn.setVisibility(View.INVISIBLE);
//                                mDeclineBtn.setEnabled(false);
//
//                            }
//
//                        }
//                        else{
//
//                            mFriendDatabase.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                    if(dataSnapshot.hasChild(user_id)){
//
//                                        mCurrent_state = "friends";
//                                        mProfileSendReqBtn.setText("Remove Connection");
//
//                                        mDeclineBtn.setVisibility(View.INVISIBLE);
//                                        mDeclineBtn.setEnabled(false);
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//
//                            mProgressDialog.dismiss();
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        mProgressDialog.dismiss();
//                    }
//                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        }

}


