package com.example.feedingindia_semi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequirementDetailsActivityDonor extends AppCompatActivity {

    private Toolbar mToolbar;
    private DatabaseReference mUsersDatabase;
    private FirebaseUser mCurrentUser;
    private TextView mRequirement;
    private Button mRequestRequirement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_details_donor);

        mToolbar = findViewById(R.id.requirements_details_page_toolbar);
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



    }
}
