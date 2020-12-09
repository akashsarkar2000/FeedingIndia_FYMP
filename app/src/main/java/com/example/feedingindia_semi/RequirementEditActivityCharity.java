package com.example.feedingindia_semi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequirementEditActivityCharity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private EditText mName;
    private Button mSaveButton;

    // PROGRESS //
    private ProgressDialog mProgress;

    // FIREBASE //
    private DatabaseReference mStatusDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_edit_charity);

        // TOOLBAR SET
        mToolbar = (Toolbar) findViewById(R.id.post_requirements_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit Requirements");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mCurrentUser != null;
        String current_uid = mCurrentUser.getUid();
        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(current_uid);

        //PROGRESS
        mProgress = new ProgressDialog(this);


        mName = findViewById(R.id.edit_charity_description);
        mSaveButton = findViewById(R.id.requirements_edit_save_btn);

        String charity_requirements = getIntent().getStringExtra("requirements");
        mName.setText(charity_requirements);


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // PROGRESS //
                mProgress = new ProgressDialog(RequirementEditActivityCharity.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes ");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();

                String charity_requirements = mName.getText().toString();

                mStatusDatabase.child("requirements").setValue(charity_requirements).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()){

                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(),"Your charity requirements has been changed, Check Your List",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"There was some error in saving changes",Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });

    }
}

