package com.example.feedingindia_semi.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedingindia_semi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class RegisterActivityDonor extends AppCompatActivity {

    EditText donorName, mEmail, mPhone, mPassword, mProfession;
    Button register;
    TextView login;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid, isProfessionValid;
    TextInputLayout nameError, emailError, phoneError, passError, professionError;
    private FirebaseAuth mAuth;
    private ProgressDialog mRegProgress;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_donor);
        mRegProgress = new ProgressDialog(this);

        donorName = findViewById(R.id.donor_name);
        mEmail = findViewById(R.id.email);
        mPhone = findViewById(R.id.phone);
        mProfession =  findViewById(R.id.profession);
        mPassword = findViewById(R.id.password);
        register = findViewById(R.id.register_donor_button);

        login = findViewById(R.id.register_to_login_donor);

        nameError = findViewById(R.id.nameError);
        emailError = findViewById(R.id.emailError);
        phoneError = findViewById(R.id.phoneError);
        passError = findViewById(R.id.passError);
        professionError =  findViewById(R.id.professionError);

        // FIREBASE AUTH
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String donor_name = donorName.getText().toString();
                String email = mEmail.getText().toString();
                String phone = mPhone.getText().toString();
                String profession = mProfession.getText().toString();
                String password = mPassword.getText().toString();
                SetValidation();
                if (!TextUtils.isEmpty(donor_name) || !TextUtils.isEmpty(profession) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(phone) || !TextUtils.isEmpty(password)){
                    // PROGRESS BAR //
                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please wait while we create your account !");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    register_user(donor_name, profession, email, phone, password);
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivityDonor.class);
                startActivity(intent);
            }
        });
    }

    private void register_user(final String donor_name, final String profession, final String email, final String phone, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    assert current_user != null;
                    String uid = current_user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor").child(uid);
                    HashMap<String,String> userMap = new HashMap<>();
                    userMap.put("donor_name",donor_name);
                    userMap.put("profession",profession);
                    userMap.put("email",email);
                    userMap.put("phone",phone);
                    userMap.put("password", password);
                    userMap.put("status","Hi there, I'm using Feeding India Application");
                    userMap.put("image","default");
                    userMap.put("thumb_image","default");
                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mRegProgress.dismiss();
                                Intent mainIntent = new Intent(RegisterActivityDonor.this, LoginActivityDonor.class);
                                Toast.makeText(RegisterActivityDonor.this,"Account created successfully, now login",Toast.LENGTH_LONG).show();
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });
                }
                else{
                    mRegProgress.hide();
                    Toast.makeText(RegisterActivityDonor.this,"Cannot Register. Please check the form and try again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void SetValidation() {
        // Check for a valid name.
        if (donorName.getText().toString().isEmpty()) {
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
        if (mProfession.getText().toString().isEmpty()) {
            professionError.setError(getResources().getString(R.string.profession_error));
            isProfessionValid = false;
        } else  {
            isProfessionValid = true;
            professionError.setErrorEnabled(false);
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

        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid && isProfessionValid) {
            Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
        }

    }

}


