package com.example.feedingindia_semi.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedingindia_semi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingEditActivityDonor extends AppCompatActivity {

    private Toolbar mToolbar;
    TextView mEmail;
    private EditText mName, mProfession, mPhone, mStatus, mAddress;
    private Button mSaveButton;

    // FIREBASE //
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;

    // PROGRESS //
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_edit_donor);



        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mCurrentUser != null;
        String current_uid = mCurrentUser.getUid();



        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor").child(current_uid);



        // TOOLBAR SET
        mToolbar = (Toolbar) findViewById(R.id.donor_account_setting_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //PROGRESS
        mProgress = new ProgressDialog(this);



        String donor_name = getIntent().getStringExtra("donor_name");
        String email = getIntent().getStringExtra("email");
        String profession = getIntent().getStringExtra("profession");
        String phone = getIntent().getStringExtra("phone");
        String status = getIntent().getStringExtra("status");
        String address = getIntent().getStringExtra("address");



        mEmail = findViewById(R.id.donor_edit_page_email);
        mName = findViewById(R.id.edit_donor_name);
        mProfession = findViewById(R.id.edit_donor_profession);
        mPhone = findViewById(R.id.edit_donor_phone_number);
        mStatus = findViewById(R.id.edit_donor_status);
        mAddress = findViewById(R.id.edit_donor_address);
        mSaveButton = findViewById(R.id.donor_info_edit_save_btn);



        mEmail.setText(email);
        mName.setText(donor_name);
        mProfession.setText(profession);
        mPhone.setText(phone);
        mStatus.setText(status);
        mAddress.setText(address);



        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // PROGRESS //
                mProgress = new ProgressDialog(SettingEditActivityDonor.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes ");
                mProgress.show();



                String email = mEmail.getText().toString();
                String name = mName.getText().toString();
                String profession = mProfession.getText().toString();
                String phone = mPhone.getText().toString();
                String status = mStatus.getText().toString();
                String address = mAddress.getText().toString();



                mStatusDatabase.child("donor_name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(), "Your details has been changed, Check Your Profile",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"There was some error in saving changes in name",Toast.LENGTH_LONG).show();
                        }
                    }
                });



                mStatusDatabase.child("email").setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(),"Your details has been changed, Check Your Profile",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"There was some error in saving changes in name",Toast.LENGTH_LONG).show();
                        }
                    }
                });



                mStatusDatabase.child("profession").setValue(profession).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"There was some error in saving changes in profession",Toast.LENGTH_LONG).show();
                        }
                    }
                });



                mStatusDatabase.child("address").setValue(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"There was some error in saving changes in profession",Toast.LENGTH_LONG).show();
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



                mStatusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"There was some error in saving changes in status",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}