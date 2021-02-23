package com.example.feedingindia_semi.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.donor.datamodels.CharityData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class RequirementDetailsActivityDonor extends AppCompatActivity {

    private Toolbar mToolbar;
    private DatabaseReference mUsersDatabase;
    private FirebaseUser mCurrentUser;
    private TextView mRequirement;
    private Button mRequestRequirement;
    private FirebaseAuth firebaseAuth;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_details_donor);

        firebaseAuth = FirebaseAuth.getInstance();
        mToolbar = findViewById(R.id.requirements_details_page_toolbar);
        Intent intent = getIntent();
        this.key = intent.getStringExtra("key");
        Log.i("keyagain",this.key);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Charity Requirements");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        final String user_id = getIntent().getStringExtra("user_id");
//        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(user_id);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();



        mRequirement = findViewById(R.id.requirements_details_donor);
        mRequestRequirement = findViewById(R.id.requirement_request_button_donor);


        mRequestRequirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
//                Intent intent = new Intent(getApplicationContext(), PostActivityCharity.class);
//                startActivity(intent);
                Toast.makeText(RequirementDetailsActivityDonor.this,"Your Request has been send to charity, Work in Progress",Toast.LENGTH_LONG).show();

            }
        });
        try {
            getRequirement();
        }catch (Exception e){
            Toast.makeText(this,"404 not found",Toast.LENGTH_SHORT).show();
        }
    }

    private void getRequirement() throws Exception{
        if(this.key == null){
            return;
        }
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity");
        mUsersDatabase.child(this.key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CharityData charityData = snapshot.getValue(CharityData.class);
                if(charityData != null){
                    Log.i("Data",charityData.getRequirements());
                    mRequirement.setText(charityData.getRequirements());
                }else {
                    Log.e("error","null data");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
