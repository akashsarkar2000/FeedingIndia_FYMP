package com.example.feedingindia_semi.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.adapter.CommentAdapter;
import com.example.feedingindia_semi.charity.adapter.DonorDonationDetailsListAdapter;
import com.example.feedingindia_semi.charity.datamodels.CommentData;
import com.example.feedingindia_semi.charity.datamodels.DonorDonationData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DonorDonationDetailListActivity extends AppCompatActivity {

    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase;
    private ProgressDialog mProgressDialog;
    private List<DonorDonationData> donorDonationDataList;
    private Toolbar mToolbar;
    private FirebaseUser mCurrentUser;
    private String charityKey;
    private FirebaseAuth firebaseAuth;
    private View view;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_donation_detail_list);

        recyclerView = findViewById(R.id.charity_donation_donor_list_recycler);

        mToolbar = findViewById(R.id.charity_donation_donor_list_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Donor Request List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view = this.getCurrentFocus();
        donorDonationDataList = new ArrayList<DonorDonationData>();
        charityKey = getIntent().getStringExtra("key");
        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        key = intent.getStringExtra("user_id");

        Log.i("mykey",firebaseAuth.getCurrentUser().getEmail());


        //  USER OBJECT REFERENCE IN KEY //
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(current_uid);
        databaseReference.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mProgressDialog = new ProgressDialog(DonorDonationDetailListActivity.this);
        mProgressDialog.setTitle("Loading All Details");
        mProgressDialog.setMessage("Please wait while we load all the details...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();


        getAllComments();


    }
    

    private void getAllComments(){
        databaseReference.child("Donor_Donation_Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donorDonationDataList.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    DonorDonationData donordonationData = data.getValue(DonorDonationData.class);
                    donorDonationDataList.add(donordonationData);
                    DonorDonationDetailsListAdapter donorAdapter = new DonorDonationDetailsListAdapter(donorDonationDataList, DonorDonationDetailListActivity.this);
                    recyclerView.setAdapter(donorAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mProgressDialog.dismiss();
    }





}