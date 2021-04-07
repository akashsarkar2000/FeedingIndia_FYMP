package com.example.feedingindia_semi.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import com.example.feedingindia_semi.R;

import java.util.Objects;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordCharity extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText edtEmail;
    private Button btnResetPassword;
    private Button btnBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_charity);


        mToolbar = findViewById(R.id.forgot_password_charity_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Forgot Password : Charity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            edtEmail = (EditText) findViewById(R.id.charity_forgot_password_email);
            btnResetPassword = (Button) findViewById(R.id.charity_forgot_password_email_button);

            mAuth = FirebaseAuth.getInstance();

            btnResetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = edtEmail.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter your charity registered email!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPasswordCharity.this, "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ForgotPasswordCharity.this, "Fail to send reset password email or the email is not available!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });

        }

    }