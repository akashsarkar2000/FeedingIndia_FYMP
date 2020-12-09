package com.example.feedingindia_semi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class MainSelectionActivityDonor extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private Toolbar mToolbar;
    LottieAnimationView settingDonor, charityList, connectedCharity, chatSection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_selection_donor);

        settingDonor = findViewById(R.id.donor_setting_pic);
        charityList = findViewById(R.id.charity_list_pic);
        connectedCharity = findViewById(R.id.charity_details_pic);
        chatSection = findViewById(R.id.donor_chat_section_pic);

        mAuth = FirebaseAuth.getInstance();
        mToolbar = findViewById(R.id.donor_main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Feeding India : Donor");
//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);  /// FOR BACK BUTTON


        chatSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), PostActivityCharity.class);
                startActivity(intent);
            }
        });

        settingDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), SettingActivityDonor.class);
                startActivity(intent);
            }
        });

        charityList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), MainActivityDonor.class);
                startActivity(intent);
            }
        });

        connectedCharity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), CharityListActivityDonor.class);
                startActivity(intent);
            }
        });


        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor").child(mAuth.getCurrentUser().getUid());
        }
    }

    // FUNCTION FOR LOGOUT AND LOGIN
    private void sendToStart() {
        Intent startIntent = new Intent (MainSelectionActivityDonor.this, LoginActivityDonor.class);
        startActivity(startIntent);
        finish();
    }

    // LOGIN AND REDIRECT TO NEXT PAGE //
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null){
            sendToStart();
        }
        else {
            mUserRef.child("online").setValue("true");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
        }
    }

    // DISPLAY OF MAIN MENU //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    // SIGN OUT //
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.id_main_logout){
            FirebaseAuth.getInstance().signOut();
            sendToStart();  // If Logout Successful then redirect to First page
        }
        return true;
    }




}

