package com.example.feedingindia_semi.charity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.donor.CharityDescriptionActivityDonor;
import com.example.feedingindia_semi.donor.DonationDetailsToCharityDonor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class DonorDonationProfileCharity extends AppCompatActivity {

    private Toolbar mToolbar;
    private String charityKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_donation_profile_charity);

        mToolbar = findViewById(R.id.charity_donor_donation_profile_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Donor Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        charityKey = getIntent().getStringExtra("key");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(DonorDonationProfileCharity.this, DonorDonationDetailsDescriptionCharity.class);
                intent.putExtra("user_id",charityKey);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }



}