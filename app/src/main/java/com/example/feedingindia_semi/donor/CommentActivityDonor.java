package com.example.feedingindia_semi.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.donor.adapters.ChatAdapter;
import com.example.feedingindia_semi.donor.datamodels.CommentData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentActivityDonor extends AppCompatActivity {

    private List<CommentData> commentDataList;
    private Toolbar mToolbar;
    private EditText editText;
    private ImageButton send;
    private String charityKey;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private View view;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_donor);


        send = findViewById(R.id.chat_send_btn);
        editText = findViewById(R.id.chat_send_text);
        recyclerView = findViewById(R.id.id_all_charity_comment_list_donor_side);

        view = this.getCurrentFocus();
        commentDataList = new ArrayList<CommentData>();
        charityKey = getIntent().getStringExtra("key");
        firebaseAuth = FirebaseAuth.getInstance();
        Log.i("mykey",firebaseAuth.getCurrentUser().getEmail());


        mToolbar = findViewById(R.id.donor_comment_all_users_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Charity Reviews");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(charityKey);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        listeners();
        getAllComments();
    }


    // ye function hai action bar ke back press ko override karne ke liye
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //dekh ye same methodology se teko har jagah back ki functionality set karni padegi
                Intent intent = new Intent(CommentActivityDonor.this,CharityDescriptionActivityDonor.class);
                intent.putExtra("user_id",charityKey);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void pushComment() throws Exception{
        String message = editText.getText().toString();
        if(message == null || message.isEmpty()){
            Toast.makeText(this, "Please type a message first", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        CommentData commentData = new CommentData();
        commentData.setUid(firebaseAuth.getCurrentUser().getUid());
        commentData.setMessage(message);
        commentData.setDatetime(formatter.format(date));
        commentData.setEmail(firebaseAuth.getCurrentUser().getEmail());
        databaseReference.child("Comments").child(firebaseAuth.getCurrentUser().getUid()+System.currentTimeMillis()).setValue(commentData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    editText.getText().clear();
                    hideKeyBoard();
                    Toast.makeText(CommentActivityDonor.this, "Comment saved", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CommentActivityDonor.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    private void getAllComments(){
        databaseReference.child("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentDataList.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    CommentData commentData = data.getValue(CommentData.class);
                    commentDataList.add(commentData);
                    ChatAdapter chatAdapter = new ChatAdapter(commentDataList,CommentActivityDonor.this);
                    recyclerView.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}