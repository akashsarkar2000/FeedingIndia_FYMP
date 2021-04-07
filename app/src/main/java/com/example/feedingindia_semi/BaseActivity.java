package com.example.feedingindia_semi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.feedingindia_semi.charity.LoginActivityCharity;
import com.example.feedingindia_semi.charity.MainActivityCharity;
import com.example.feedingindia_semi.donor.LoginActivityDonor;
import com.example.feedingindia_semi.donor.MainActivityDonor;

public class BaseActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView charity,donor;
    private LottieAnimationView charityLottie, donorLottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        charity = findViewById(R.id.charity);
        charityLottie = findViewById(R.id.charity_base_lottie);
        donor = findViewById(R.id.donor);
        donorLottie = findViewById(R.id.donor_base_lottie);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        onClickListeners();
        try {
            checkSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onClickListeners(){
        charity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, LoginActivityCharity.class));
            }
        });
        charityLottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, LoginActivityCharity.class));
            }
        });
        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, LoginActivityDonor.class));
            }
        });
        donorLottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, LoginActivityDonor.class));
            }
        });
    }


    private void checkSession() throws Exception{
        boolean charity = sharedPreferences.getBoolean("charity",false);
        boolean donor = sharedPreferences.getBoolean("donor",false);
        if(charity){
            startActivity(new Intent(BaseActivity.this, MainActivityCharity.class));
        }else if(donor){
            startActivity(new Intent(BaseActivity.this, MainActivityDonor.class));
        }
    }
}