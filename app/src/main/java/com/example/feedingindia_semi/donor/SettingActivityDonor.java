package com.example.feedingindia_semi.donor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedingindia_semi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SettingActivityDonor extends AppCompatActivity {


    private Toolbar mToolbar;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private ImageView mDisplayImage;
    private TextView mDonorName, mDonorProfession, mPhone, mStatus;
    private Button mImageButton, mEditButton;

    private StorageReference mImageStorage;
    private ProgressDialog mProgressDialog;


    private static final int GALLERY_PICK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_donor);

        mDisplayImage = findViewById(R.id.donor_profile_image);
        mDonorName = findViewById(R.id.donor_display_name);
        mDonorProfession = findViewById(R.id.donor_profession_name);
        mPhone = findViewById(R.id.donor_phone_number);
        mStatus = findViewById(R.id.donor_profile_status);
        mEditButton = findViewById(R.id.donor_edit_info_setting);
        mImageButton = findViewById(R.id.donor_edit_image_setting);

        mToolbar = findViewById(R.id.account_setting_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mImageStorage = FirebaseStorage.getInstance().getReference();
        //  USER OBJECT REFERENCE IN KEY //
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mCurrentUser != null;
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor").child(current_uid);
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String donor_name = dataSnapshot.child("donor_name").getValue().toString();
                String profession = dataSnapshot.child("profession").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                final String image = dataSnapshot.child("image").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mDonorName.setText(donor_name);
                mDonorProfession.setText(profession);
                mPhone.setText(phone);
                mStatus.setText(status);

                if (!image.equals("default")) {
                    Picasso.with(SettingActivityDonor.this).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.default_image)
                            .into(mDisplayImage, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {

                                    Picasso.with(SettingActivityDonor.this).load(image).placeholder(R.drawable.default_image).into(mDisplayImage);

                                }
                            });
                }
            }

            ;

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String donor_name = mDonorName.getText().toString();
                String donor_profession = mDonorProfession.getText().toString();
                String phone = mPhone.getText().toString();
                String status = mStatus.getText().toString();

                Intent status_intent = new Intent(SettingActivityDonor.this, SettingEditActivityDonor.class);
                status_intent.putExtra("donor_name", donor_name);
                status_intent.putExtra("profession", donor_profession);
                status_intent.putExtra("phone", phone);
                status_intent.putExtra("status", status);
                startActivity(status_intent);
            }
        });

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // start picker to get image for cropping and then use the image in cropping activity
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SettingActivityDonor.this);

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){

            assert data != null;
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(SettingActivityDonor.this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mProgressDialog = new ProgressDialog(SettingActivityDonor.this);
                mProgressDialog.setTitle("Uploading Image...");
                mProgressDialog.setMessage("Please wait while we upload and process the image.");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                assert result != null;
                Uri resultUri = result.getUri();

                String current_user_id = mCurrentUser.getUid();

                final StorageReference filepath = mImageStorage.child("donor_profile_images").child(current_user_id + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){

                            mProgressDialog.dismiss();
                            Toast.makeText(SettingActivityDonor.this, "Uploaded Successfully", Toast.LENGTH_LONG).show();

                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String download_url = uri.toString();
                                    mUserDatabase.child("image").setValue(download_url);
                                    mUserDatabase.child("thumb_image").setValue(download_url);
                                }
                            });

                        } else {

                            Toast.makeText(SettingActivityDonor.this, "Error in Uploading", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();

                        }

                    }
                });

            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    };

}
