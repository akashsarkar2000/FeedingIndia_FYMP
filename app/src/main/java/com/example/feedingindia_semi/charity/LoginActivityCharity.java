package com.example.feedingindia_semi.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.donor.LoginActivityDonor;
import com.example.feedingindia_semi.donor.datamodels.CharityData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivityCharity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    Button login;
    TextView registerCharity, loginDonor;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;
    private DatabaseReference databaseReference;
    private ProgressDialog mLoginProgress;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;
    public boolean isEmailExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_charity);

        mEmail = findViewById(R.id.email);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity");
        mPassword = findViewById(R.id.password);
        registerCharity = findViewById(R.id.login_to_register_charity);
        emailError = findViewById(R.id.emailError);
        passError = findViewById(R.id.passError);
        mLoginProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login_charity_button);
        loginDonor = findViewById(R.id.to_login_donor);
        preferences = getSharedPreferences("login",MODE_PRIVATE);
        editor = preferences.edit();
        registerCharity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), RegisterActivityCharity.class);
                startActivity(intent);
            }
        });

        loginDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivityDonor.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                SetValidation();
                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    if(!isEmailExist()){
                        Toast.makeText(LoginActivityCharity.this, "You are not registered as Charity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mLoginProgress.setTitle("Login In");
                    mLoginProgress.setMessage("Please wait while we check your credentials !");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    loginUser(email, password);
                }
            }
        });
        listeners();
    }

    // Email Password Validation Check //
    public void SetValidation() {
        // Check for a valid email address.
        if (mEmail.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (mPassword.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (mPassword.getText().length() < 6) {
            passError.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }
        if (isEmailValid && isPasswordValid) {
            isEmailValid = true;
            isPasswordValid = true;
        }

    }

    // Login Button Function //
    private void loginUser(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mLoginProgress.dismiss();
                    Intent mainIntent = new Intent(LoginActivityCharity.this, MainActivityCharity.class);
                    Toast.makeText(LoginActivityCharity.this, "Login Successful, Welcome to Charity Section", Toast.LENGTH_LONG).show();
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // this line is to stick to main page after login
                    editor.putBoolean("charity",true);
                    editor.apply();
                    editor.putBoolean("donor",false);
                    editor.apply();
                    startActivity(mainIntent);
                    finish();
                } else {
                    mLoginProgress.hide();
                    Toast.makeText(LoginActivityCharity.this, "Cannot Sign in. Please check the details and try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkEmail(String email){
        databaseReference.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CharityData charityData = snapshot.getValue(CharityData.class);
                if(charityData == null){
                    setEmailExist(false);
                }else{
                    setEmailExist(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void listeners(){
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEmail(s.toString());
            }
        });
    }

    public void setEmailExist(boolean emailExist) {
        isEmailExist = emailExist;
    }

    public boolean isEmailExist() {
        return isEmailExist;
    }
}