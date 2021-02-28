package com.example.feedingindia_semi.donor;

import androidx.appcompat.app.AppCompatActivity;
import com.example.feedingindia_semi.R;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class CommentActivityDonor extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_donor);

        mToolbar = findViewById(R.id.donor_comment_all_users_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Charity Reviews");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}