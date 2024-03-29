package com.example.feedingindia_semi.charity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.feedingindia_semi.BaseActivity;
import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.donor.CharityDescriptionActivityDonor;
import com.example.feedingindia_semi.donor.CommentActivityDonor;
import com.example.feedingindia_semi.donor.MainActivityDonor;
import com.example.feedingindia_semi.donor.SettingActivityDonor;
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
    private String charityKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingCharity = findViewById(R.id.setting_pic);
        donorList = findViewById(R.id.donor_details_pic);
        requirement = findViewById(R.id.donor_base_lottie);
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
                Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
                startActivity(intent);
            }
        });

        settingCharity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivityCharity.class);
                startActivity(intent);
            }
        });

        donorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DonorDonationDetailListActivity.class);
                startActivity(intent);
            }
        });

        requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        getMenuInflater().inflate(R.menu.main_menu1, menu);
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

        if (item.getItemId() == R.id.id_main_contact_us_charity){

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"feedingindia2021@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback regarding Feeding India Application");
            startActivity(intent);

        }

        if (item.getItemId() == R.id.id_main_trusted_donor_charity){

            Intent intent = new Intent(getApplicationContext(), CommentActivityDonor.class);
            Intent allUserIntent = new Intent (MainActivityCharity.this, TrustedDonorCharity.class);
            startActivity(allUserIntent);

        }

        return true;
    }

}
