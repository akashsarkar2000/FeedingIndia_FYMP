package com.example.feedingindia_semi.charity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.example.feedingindia_semi.R;

public class DonorDonationDetails extends AppCompatActivity {


    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_donation_details);

        mToolbar = findViewById(R.id.donor_donation_details_page);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Donor Donation Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}