package com.example.feedingindia_semi.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.ForgotPasswordCharity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class ForgotPasswordDonor extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText edtEmail;
    private Button btnResetPassword;
    private Button btnBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_donor);

        mToolbar = findViewById(R.id.forgot_password_donor_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Forgot Password : Donor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edtEmail = (EditText) findViewById(R.id.donor_forgot_password_email);
        btnResetPassword = (Button) findViewById(R.id.donor_forgot_password_email_button);

        mAuth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter your donor registered email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordDonor.this, "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgotPasswordDonor.this, "Fail to send reset password email or the email is not available!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

}