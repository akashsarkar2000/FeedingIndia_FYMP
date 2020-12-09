package com.example.feedingindia_semi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
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


public class MainActivityCharity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private Toolbar mToolbar;
    LottieAnimationView settingCharity, donorList, requirement, chatSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingCharity = findViewById(R.id.setting_pic);
        donorList = findViewById(R.id.donor_details_pic);
        requirement = findViewById(R.id.selection_posts_requirements_pic);
        chatSection = findViewById(R.id.chat_section_pic);

        mAuth = FirebaseAuth.getInstance();
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Feeding India : Charity");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  /// FOR BACK BUTTON

        chatSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), PostActivityCharity.class);
                startActivity(intent);
            }
        });

        settingCharity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), SettingActivityCharity.class);
                startActivity(intent);
            }
        });

        donorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), DonorListActivityCharity.class);
                startActivity(intent);
            }
        });

        requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), SelectionPostRequirementActivityCharity.class);
                startActivity(intent);
            }
        });


        if(mAuth.getCurrentUser() != null){
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(mAuth.getCurrentUser().getUid());
        }
    }

    // FUNCTION FOR LOGOUT AND LOGIN
    private void sendToStart() {
        Intent startIntent = new Intent (MainActivityCharity.this, LoginActivityCharity.class);
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
