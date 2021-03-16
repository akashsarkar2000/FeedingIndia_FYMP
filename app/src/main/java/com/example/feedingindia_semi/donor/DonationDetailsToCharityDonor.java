package com.example.feedingindia_semi.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.example.feedingindia_semi.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedingindia_semi.donor.datamodels.CommentData;
import com.example.feedingindia_semi.donor.datamodels.DonationDetailsData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonationDetailsToCharityDonor extends AppCompatActivity {

    TextView mSubmit, mCancel;
    private List<DonationDetailsData> donationDetailsDataList;
    private Toolbar mToolbar;
    private Button send;
    private String charityKey;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private View view;
    private RecyclerView recyclerView;
    private EditText mFoodName, mFoodQuantity, mFoodExpDate, mDonationDate, mMoneyDonation, mMoneyPurpose, mRequirementsDate, mRequirementsItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details_to_charity_donor);

        mToolbar = findViewById(R.id.donor_side_donation_details_page);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Fill Donation Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view = this.getCurrentFocus();
        donationDetailsDataList = new ArrayList<DonationDetailsData>();
        charityKey = getIntent().getStringExtra("key");
        firebaseAuth = FirebaseAuth.getInstance();
        Log.i("mykey",firebaseAuth.getCurrentUser().getEmail());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(charityKey);


        send = findViewById(R.id.donation_donor_side_submit_button);
        mFoodName = findViewById(R.id.donation_donor_side_food_name);
        mFoodQuantity = findViewById(R.id.donation_donor_side_food_quantity);
        mFoodExpDate = findViewById(R.id.donation_donor_side_food_exp_date);
        mDonationDate = findViewById(R.id.donation_donor_side_food_donation_date);
        mMoneyDonation = findViewById(R.id.donation_donor_side_money_amount);
        mMoneyPurpose = findViewById(R.id.donation_donor_side_money_purpose);
        mRequirementsDate = findViewById(R.id.donation_donor_side_requirement_donation_date);
        mRequirementsItems = findViewById(R.id.donation_donor_side_requirment_items);

        listeners();
//        getAllComments();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(DonationDetailsToCharityDonor.this,CharityDescriptionActivityDonor.class);
                intent.putExtra("user_id",charityKey);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }



    private void pushDonationDetails() throws Exception{

        String food_name = mFoodName.getText().toString();
        String food_quantity_kg = mFoodQuantity.getText().toString();
        String food_expiry_date = mFoodExpDate.getText().toString();
        String food_donation_date = mDonationDate.getText().toString();
        String money_amount = mMoneyDonation.getText().toString();
        String money_purpose = mMoneyPurpose.getText().toString();
        String requirement_date = mRequirementsDate.getText().toString();
        String requirements_items = mRequirementsItems.getText().toString();
        if (food_name == null || food_name.isEmpty() || food_quantity_kg == null || food_quantity_kg.isEmpty() || food_expiry_date == null
                || food_expiry_date.isEmpty() || food_donation_date == null || food_donation_date.isEmpty() || money_amount == null
                || money_amount.isEmpty() || money_purpose == null || money_purpose.isEmpty() || requirement_date == null
                || requirement_date.isEmpty() || requirements_items == null || requirements_items.isEmpty() ){
            Toast.makeText(this, "Please fill all detail first", Toast.LENGTH_SHORT).show();
            return;
        }
        DonationDetailsData donationDetailsData = new DonationDetailsData();
        donationDetailsData.setUid(firebaseAuth.getCurrentUser().getUid());
        donationDetailsData.setDonationDonorEmail(firebaseAuth.getCurrentUser().getEmail());
        donationDetailsData.setFoodName(food_name);
        donationDetailsData.setFoodQuantityKg(food_quantity_kg);
        donationDetailsData.setFoodExpiryDate(food_expiry_date);
        donationDetailsData.setFoodDonationDate(food_donation_date);
        donationDetailsData.setMoneyAmount(money_amount);
        donationDetailsData.setMoneyPurpose(money_purpose);
        donationDetailsData.setRequirementsDate(requirement_date);
        donationDetailsData.setRequirementsItems(requirements_items);
//        commentData.setEmail(firebaseAuth.getCurrentUser().getEmail());
        databaseReference.child("Donor_Donation_Details").child(firebaseAuth.getCurrentUser().getUid()+System.currentTimeMillis()).setValue(donationDetailsData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent mainIntent = new Intent(DonationDetailsToCharityDonor.this, CharityDescriptionActivityDonor.class);
                    Toast.makeText(DonationDetailsToCharityDonor.this, "Details send to the charity", Toast.LENGTH_SHORT).show();
                    startActivity(mainIntent);
                }
            }
        });
    }

    private void listeners(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pushDonationDetails();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(DonationDetailsToCharityDonor.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }




}