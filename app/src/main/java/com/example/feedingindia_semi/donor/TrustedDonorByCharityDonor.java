package com.example.feedingindia_semi.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.AllCommentsByDonorForCharity;
import com.example.feedingindia_semi.charity.datamodels.TrustedDonorData;
import com.example.feedingindia_semi.donor.adapters.CommentAdapter;
import com.example.feedingindia_semi.donor.adapters.TrustedDonorByCharityAdapter;
import com.example.feedingindia_semi.donor.datamodels.CommentData;
import com.example.feedingindia_semi.donor.datamodels.TrustedDonorByCharityData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrustedDonorByCharityDonor extends AppCompatActivity {

    private List<TrustedDonorByCharityData> trustedDonorByCharityDataList;
    private Toolbar mToolbar;
    private EditText editText;
    private ImageButton send;
    private String charityKey;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private View view;
    private RecyclerView recyclerView;
    String key;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_donor_by_charity_donor);

        // TOOLBAR SET
        mToolbar = (Toolbar) findViewById(R.id.donor_trusted_donor_users_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Charities Trusted Donor's");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.id_all_donor_trusted_donor_users_charity_side);

        view = this.getCurrentFocus();
        trustedDonorByCharityDataList = new ArrayList<TrustedDonorByCharityData>();
        charityKey = getIntent().getStringExtra("key");
        firebaseAuth = FirebaseAuth.getInstance();
        Log.i("mykey",firebaseAuth.getCurrentUser().getEmail());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(charityKey);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        getAllComments();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(TrustedDonorByCharityDonor.this,CharityDescriptionActivityDonor.class);
                intent.putExtra("user_id",charityKey);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void getAllComments(){
        databaseReference.child("Trusted_Donor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trustedDonorByCharityDataList.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    TrustedDonorByCharityData commentData = data.getValue(TrustedDonorByCharityData.class);
                    trustedDonorByCharityDataList.add(commentData);
                    TrustedDonorByCharityAdapter commentAdapter = new TrustedDonorByCharityAdapter(trustedDonorByCharityDataList,TrustedDonorByCharityDonor.this);
                    recyclerView.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}