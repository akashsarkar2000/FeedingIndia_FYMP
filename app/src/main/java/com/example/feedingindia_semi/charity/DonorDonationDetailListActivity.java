package com.example.feedingindia_semi.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.adapter.ChatListAdapter;
import com.example.feedingindia_semi.charity.adapter.DonorDonationListAdapter;
import com.example.feedingindia_semi.charity.datamodels.ChatListModel;
import com.example.feedingindia_semi.charity.datamodels.DonorDonationListModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonorDonationDetailListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private List<DonorDonationListModel> donationListModelList;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_donation_detail_list);

        mToolbar = findViewById(R.id.charity_donation_donor_list_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Donor Donation Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init(){
        donationListModelList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(firebaseAuth.getCurrentUser().getUid());
        recyclerView = findViewById(R.id.charity_donation_donor_list_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DonorDonationDetailListActivity.this));
//        getAllDonorDonationList();
    }

    //    private void getAllDonorDonationList(){
//        databaseReference.child("Donor_Donation_Details").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                donationListModelList.clear();
//                for(DataSnapshot dp : snapshot.getChildren()){
//                    DonorDonationListModel donationListModel = dp.getValue(DonorDonationListModel.class);
//                    donationListModel.setName(dp.getKey());
//                    donationListModel.setProfession(dp.getKey());
//                    donationListModel.setEmail(dp.getKey());
//                    donationListModel.setThumb_image(dp.getKey());
//                    donationListModelList.add(donationListModel);
//                }
//                recyclerView.setAdapter(new DonorDonationListAdapter(DonorDonationDetailListActivity.this,donationListModelList));
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }

}