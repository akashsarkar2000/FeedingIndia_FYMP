package com.example.feedingindia_semi.charity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.feedingindia_semi.R;

public class DonorDonationDetailsDescriptionCharity extends AppCompatActivity {

    private Toolbar mToolbar;
    private String charityKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_donation_details_description_charity);

        mToolbar = findViewById(R.id.charity_donor_donation_description_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Requested Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        charityKey = getIntent().getStringExtra("key");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(DonorDonationDetailsDescriptionCharity.this, DonorDonationDetailListActivity.class);
                intent.putExtra("user_id",charityKey);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


}