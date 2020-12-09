package com.example.feedingindia_semi.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feedingindia_semi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingEditActivityCharity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mName, mAddress, mPhone, mDescription;
    private Button mSaveButton;


    // FIREBASE //
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;

    // PROGRESS //
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_edit_charity);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mCurrentUser != null;
        String current_uid = mCurrentUser.getUid();

        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(current_uid);

        // TOOLBAR SET
        mToolbar = (Toolbar) findViewById(R.id.account_setting_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //PROGRESS
        mProgress = new ProgressDialog(this);

        String charity_name = getIntent().getStringExtra("charity_name");
        String charity_address = getIntent().getStringExtra("charity_address");
        String phone = getIntent().getStringExtra("phone");
        String description = getIntent().getStringExtra("description");

        mName = findViewById(R.id.edit_charity_name);
        mAddress = findViewById(R.id.edit_charity_address);
        mPhone = findViewById(R.id.edit_charity_phone);
        mDescription = findViewById(R.id.edit_charity_description);
        mSaveButton = findViewById(R.id.requirements_edit_save_btn);

        mName.setText(charity_name);
        mAddress.setText(charity_address);
        mPhone.setText(phone);
        mDescription.setText(description);


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // PROGRESS //
                mProgress = new ProgressDialog(SettingEditActivityCharity.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes ");
                mProgress.show();

                String name = mName.getText().toString();
                String address = mAddress.getText().toString();
                String phone = mPhone.getText().toString();
                String description = mDescription.getText().toString();

                mStatusDatabase.child("charity_name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()){

                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(),"Your details has been changed, Check Your Profile",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"There was some error in saving changes",Toast.LENGTH_LONG).show();
                        }

                    }
                });

                mStatusDatabase.child("charity_address").setValue(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()){

                            mProgress.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"There was some error in saving changes in address",Toast.LENGTH_LONG).show();
                        }

                    }
                });

                mStatusDatabase.child("phone").setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()){

                            mProgress.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"There was some error in saving changes in phone number",Toast.LENGTH_LONG).show();
                        }

                    }
                });

                mStatusDatabase.child("description").setValue(description).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()){

                            mProgress.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"There was some error in saving changes in description",Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });

    }
}

