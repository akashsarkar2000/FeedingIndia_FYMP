package com.example.feedingindia_semi.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.adapter.TrustedDonorAdapter;
import com.example.feedingindia_semi.charity.datamodels.TrustedDonorData;
import com.example.feedingindia_semi.donor.CommentActivityDonor;
import com.example.feedingindia_semi.donor.adapters.CommentAdapter;
import com.example.feedingindia_semi.donor.datamodels.CommentData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrustedDonorCharity extends AppCompatActivity {

    private List<TrustedDonorData> trustedDonorDataList;
    private Toolbar mToolbar;
    private EditText editText;
    private ImageButton send;
    private String charityKey;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private View view;
    private RecyclerView recyclerView;
    String key;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_donor_charity);

        // TOOLBAR SET
        mToolbar = (Toolbar) findViewById(R.id.charity_trusted_donor_users_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Trusted Donor : Charity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        send = findViewById(R.id.charity_trusted_donor_info_send_btn);
        editText = findViewById(R.id.charity_trusted_donor_info_send_text);
        recyclerView = findViewById(R.id.id_all_charity_trusted_donor_users_charity_side);

        view = this.getCurrentFocus();
        trustedDonorDataList = new ArrayList<TrustedDonorData>();
        firebaseAuth = FirebaseAuth.getInstance();
        Log.i("mykey",firebaseAuth.getCurrentUser().getEmail());

        Intent intent = getIntent();
        key = intent.getStringExtra("user_id");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mCurrentUser != null;
        String current_uid = mCurrentUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(current_uid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        listeners();
        getAllComments();
    }





    private void pushComment() throws Exception{
        String message = editText.getText().toString();
        if(message == null || message.isEmpty()){
            Toast.makeText(this, "Please type a message first", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        TrustedDonorData commentData = new TrustedDonorData();
        commentData.setUid(firebaseAuth.getCurrentUser().getUid());
        commentData.setMessage(message);
        commentData.setDatetime(formatter.format(date));
        commentData.setEmail(firebaseAuth.getCurrentUser().getEmail());
        databaseReference.child("Trusted_Donor").child(firebaseAuth.getCurrentUser().getUid()+System.currentTimeMillis()).setValue(commentData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    editText.getText().clear();
                    hideKeyBoard();
                    Toast.makeText(TrustedDonorCharity.this, "Information saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void listeners(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pushComment();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(TrustedDonorCharity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    private void getAllComments(){
        databaseReference.child("Trusted_Donor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trustedDonorDataList.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    TrustedDonorData commentData = data.getValue(TrustedDonorData.class);
                    trustedDonorDataList.add(commentData);
                    TrustedDonorAdapter commentAdapter = new TrustedDonorAdapter(trustedDonorDataList,TrustedDonorCharity.this);
                    recyclerView.setAdapter(commentAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}