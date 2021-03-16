package com.example.feedingindia_semi.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedingindia_semi.charity.DonorDonationDetails;
import com.example.feedingindia_semi.charity.MainActivityCharity;
import com.example.feedingindia_semi.charity.PostActivityCharity;
import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.SettingActivityCharity;
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
    private TextView mCharityAddress, mCharityPhone,mCharityName,mCharityDescription, mCharityEmail;
    private Button mPostButton, mContactButton, mDonateButton, mMessageButton, mCommentPageButton;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference mFriendReqDatabase;
    private ProgressDialog mProgressDialog;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mFriendDatabase;
    private DatabaseReference mRootRef;
    private String mCurrent_state;
    private String charityKey;
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
        mCharityDescription = findViewById(R.id.charity_description_description2);
        mCharityPhone = findViewById(R.id.charity_description_phone4);
        mCharityEmail = findViewById(R.id.charity_description_email);

        mCommentPageButton = findViewById(R.id.donor_comment_charity_review_button);
        mPostButton = findViewById(R.id.charity_description_post_button);
        mContactButton = findViewById(R.id.charity_description_contact_button);
        mDonateButton = findViewById(R.id.charity_description_donate_button);
        mMessageButton = findViewById(R.id.charity_description_message_button);

        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor").child(mAuth.getCurrentUser().getUid());
        }

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getCharityKey() == null){
                    Toast.makeText(CharityDescriptionActivityDonor.this, "Please Wait", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), RequirementDetailsActivityDonor.class);
                intent.putExtra("key",charityKey);
                startActivity(intent);
            }
        });
        mContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = mCharityPhone.getText().toString();
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", phone, null));
                startActivity(phoneIntent);
            }
        });

        mDonateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCharityKey() == null){
                    Toast.makeText(CharityDescriptionActivityDonor.this, "Please Wait", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(CharityDescriptionActivityDonor.this,"Fill this form, The data will send to this charity",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), DonationDetailsToCharityDonor.class);
                intent.putExtra("key",charityKey);
                startActivity(intent);
            }
        });
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("key",charityKey);
                startActivity(intent);
            }
        });
        mCommentPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                if(getCharityKey() == null){
                    Toast.makeText(CharityDescriptionActivityDonor.this, "Please Wait", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), CommentActivityDonor.class);
                intent.putExtra("key",charityKey);
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
                try {
                    Log.i("key",dataSnapshot.getKey());
                    String charity_name = Objects.requireNonNull(dataSnapshot.child("charity_name").getValue()).toString();
                    String charity_address = Objects.requireNonNull(dataSnapshot.child("charity_address").getValue()).toString();
                    String phone = Objects.requireNonNull(dataSnapshot.child("phone").getValue()).toString();
                    String image = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                    String description = Objects.requireNonNull(dataSnapshot.child("description").getValue()).toString();
                    String email = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                    setCharityKey(dataSnapshot.getKey());
                    mCharityName.setText(charity_name);
                    mCharityAddress.setText(charity_address);
                    mCharityPhone.setText(phone);
                    mCharityDescription.setText(description);
                    mCharityEmail.setText(email);

                    Picasso.get().load(image).placeholder(R.drawable.default_image).into(mCharityImages);
                    mProgressDialog.dismiss();
                }catch (Exception e){

                    Toast.makeText(CharityDescriptionActivityDonor.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CharityDescriptionActivityDonor.this,MainActivityDonor.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void setCharityKey(String charityKey) {
        this.charityKey = charityKey;
    }

    public String getCharityKey() {
        return charityKey;
    }
}


