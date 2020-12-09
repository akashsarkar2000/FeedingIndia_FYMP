package com.example.feedingindia_semi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RequirementActivityCharity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private TextView mAddButton, mRequirement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_charity);

        mRequirement = findViewById(R.id.requirement_charity);
        mAddButton = findViewById(R.id.requirement_add_button);


        mToolbar = findViewById(R.id.post_requirements_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Post & Requirements");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //  USER OBJECT REFERENCE IN KEY //
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mCurrentUser != null;
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(current_uid);
        mUserDatabase.keepSynced(true);


        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String requirements = dataSnapshot.child("requirements").getValue().toString();
                    mRequirement.setText(requirements);
                }catch (Exception e){
                    e.printStackTrace();
                }
            };

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String requirements = mRequirement.getText().toString();

                Intent status_intent = new Intent(RequirementActivityCharity.this, RequirementEditActivityCharity.class);
                status_intent.putExtra("requirements", requirements);
                startActivity(status_intent);
            }
        });

    }
}

