package com.example.feedingindia_semi.charity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.feedingindia_semi.R;

public class SelectionPostRequirementActivityCharity extends AppCompatActivity {
    private Toolbar mToolbar;
    LottieAnimationView requirementSelect, postSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_post_requirement_charity);

        // TOOLBAR SET
        mToolbar = findViewById(R.id.post_requirements_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Post & Requirements");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requirementSelect = findViewById(R.id.selection_requirements_pic);
        postSelect = findViewById(R.id.selection_posts_pic);

        requirementSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RequirementActivityCharity.class);
                startActivity(intent);
            }
        });

        postSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PostActivityCharity.class);
                startActivity(intent);
            }
        });
    }
}
