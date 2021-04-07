package com.example.feedingindia_semi.charity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedingindia_semi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import kotlin.Result;

public class RegisterActivityCharity extends AppCompatActivity {

    EditText charityName, mEmail, mPhone, mPassword, charityRegistration;
    Button register,proof;
    private StorageReference mImageStorage;
    TextView login;
    String proof_url;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid, isRegistrationValid;
    TextInputLayout nameError, emailError, phoneError, passError, registrationError;
    private FirebaseAuth mAuth;
    private ProgressDialog mRegProgress;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_charity);
        mRegProgress = new ProgressDialog(this);

        charityName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPhone = findViewById(R.id.phone);
        charityRegistration =  findViewById(R.id.registration);
        mPassword = findViewById(R.id.password);
        mImageStorage = FirebaseStorage.getInstance().getReference();
        register = findViewById(R.id.register_charity_button);
        login = findViewById(R.id.register_to_login_charity);

        nameError = findViewById(R.id.nameError);
        emailError = findViewById(R.id.emailError);
        phoneError = findViewById(R.id.phoneError);
        passError = findViewById(R.id.passError);
        proof = findViewById(R.id.proof);
        registrationError =  findViewById(R.id.registrationError);

        // FIREBASE AUTH
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String charity_name = charityName.getText().toString();
                String email = mEmail.getText().toString();
                String phone = mPhone.getText().toString();
                String charityReg = charityRegistration.getText().toString();
                String password = mPassword.getText().toString();
                SetValidation();
                if (!TextUtils.isEmpty(charity_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(phone) || !TextUtils.isEmpty(charityReg) || !TextUtils.isEmpty(password)){
                    // PROGRESS BAR //
                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please wait while we create your account !");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    register_user(charity_name, email, phone, charityReg, password);
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivityCharity.class);
                startActivity(intent);
            }
        });

        proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(RegisterActivityCharity.this);
            }
        });
    }

    private void register_user(final String charity_name, final String email, final String phone, final String charityReg, final String password) {
        if(getProof_url() == null || getProof_url().isEmpty()){
            Toast.makeText(this, "Please add validity proof image", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    assert current_user != null;
                    String uid = current_user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(uid);
                    HashMap<String,String> userMap = new HashMap<>();
                    userMap.put("charity_name",charity_name);
                    userMap.put("email",email);
                    userMap.put("phone",phone);
                    userMap.put("charityReg",charityReg);
                    userMap.put("password", password);
                    userMap.put("charity_address","Hi, This is our charity address");
                    userMap.put("status","Hi there, I'm using Feeding India Application");
                    userMap.put("description","Hi, This is our charity description");
                    userMap.put("requirements","Currently no requirements are available from charity, check after uploading details");
                    userMap.put("post_description","No Description");
                    userMap.put("post_image","default");
                    userMap.put("image","default");
                    userMap.put("thumb_image","default");
                    userMap.put("proof_url",getProof_url());
                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mRegProgress.dismiss();
                                sendVerificationEmail();
                                Intent mainIntent = new Intent(RegisterActivityCharity.this, LoginActivityCharity.class);
                                Toast.makeText(RegisterActivityCharity.this,"Account created successfully, now login",Toast.LENGTH_LONG).show();
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });
                }
                else{
                    mRegProgress.hide();
                    Log.i("lol", Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()));
                    Toast.makeText(RegisterActivityCharity.this,"Cannot Sign in. Please check the form and try again",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void sendVerificationEmail() {

        // send verification link
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(RegisterActivityCharity.this, "Verification email has been sent", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("key","onFailure: Email not sent"+e.getMessage());
            }
        });


    }


    public void SetValidation() {
        // Check for a valid name.
        if (charityName.getText().toString().isEmpty()) {
            nameError.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else  {
            isNameValid = true;
            nameError.setErrorEnabled(false);
        }

        // Check for a valid email address.
        if (mEmail.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid phone number.
        if (mPhone.getText().toString().isEmpty()) {
            phoneError.setError(getResources().getString(R.string.phone_error));
            isPhoneValid = false;
        } else  {
            isPhoneValid = true;
            phoneError.setErrorEnabled(false);
        }

        // Check for a registration name.
        if (charityRegistration.getText().toString().isEmpty()) {
            registrationError.setError(getResources().getString(R.string.registration_error));
            isRegistrationValid = false;
        } else  {
            isRegistrationValid = true;
            registrationError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (mPassword.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (mPassword.getText().length() < 6) {
            passError.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid && isRegistrationValid) {
            Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){

            assert data != null;
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(RegisterActivityCharity.this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mProgressDialog = new ProgressDialog(RegisterActivityCharity.this);
                mProgressDialog.setTitle("Uploading Image...");
                mProgressDialog.setMessage("Please wait while we upload and process the image.");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                assert result != null;
                Uri resultUri = result.getUri();



                final StorageReference filepath = mImageStorage.child("charity_proof_image").child(getSaltString() + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){

                            mProgressDialog.dismiss();
                            Toast.makeText(RegisterActivityCharity.this, "Uploaded Successfully", Toast.LENGTH_LONG).show();

                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String download_url = uri.toString();
                                    setProof_url(download_url);
                                    proof.setText("IMAGE SAVED");
                                }
                            });

                        } else {
                            Log.i("exce",task.getException().getMessage());
                            Toast.makeText(RegisterActivityCharity.this, "Error in Uploading", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();

                        }

                    }
                });

            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

    public void setProof_url(String proof_url) {
        this.proof_url = proof_url;
    }

    public String getProof_url() {
        return proof_url;
    }
}

