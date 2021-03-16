package com.example.feedingindia_semi.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.datamodels.ChatData;
import com.example.feedingindia_semi.donor.CharityDescriptionActivityDonor;
import com.example.feedingindia_semi.donor.adapters.ChatAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private List<ChatData> chatDataList;
    private String charityKey;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText editText;
    private ImageButton send;
    private RecyclerView recyclerView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();

        mToolbar = findViewById(R.id.charity_chat_all_users_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Personal Messages");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void init(){
        chatDataList = new ArrayList<>();
        send = findViewById(R.id.chat_send_btn);
        editText = findViewById(R.id.chat_send_text);
        recyclerView = findViewById(R.id.chats);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        firebaseAuth = FirebaseAuth.getInstance();
        charityKey = getIntent().getStringExtra("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(firebaseAuth.getCurrentUser().getUid()).child("Chats").child(getIntent().getStringExtra("key"));
        listeners();
        getAllComments();
    }


    private void pushMessage() throws Exception{
        String message = editText.getText().toString();
        if(message == null || message.isEmpty()){
            Toast.makeText(this, "Please type a message first", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        ChatData commentData = new ChatData();
        commentData.setUid(firebaseAuth.getCurrentUser().getUid());
        commentData.setMessage(message);
        commentData.setDatetime(formatter.format(date));

        databaseReference.child(firebaseAuth.getCurrentUser().getUid()+System.currentTimeMillis()).setValue(commentData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    editText.getText().clear();
                    hideKeyBoard();
                    Toast.makeText(ChatActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
    private void listeners(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pushMessage();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(ChatActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void getAllComments(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatDataList.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    ChatData commentData = data.getValue(ChatData.class);

                    String dtStart = commentData.getDatetime();
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    try {
                        Date date = format.parse(dtStart);
                        commentData.setDate(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    chatDataList.add(commentData);
                }
                Collections.sort(chatDataList, new Comparator<ChatData>() {
                    public int compare(ChatData m1, ChatData m2) {
                        return m1.getDate().compareTo(m2.getDate());
                    }
                });
                ChatAdapter commentAdapter = new ChatAdapter(ChatActivity.this,chatDataList,recyclerView);
                recyclerView.setAdapter(commentAdapter);
                recyclerView.scrollToPosition(chatDataList.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}