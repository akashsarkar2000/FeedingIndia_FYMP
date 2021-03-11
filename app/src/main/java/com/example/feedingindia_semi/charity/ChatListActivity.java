package com.example.feedingindia_semi.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.adapter.ChatListAdapter;
import com.example.feedingindia_semi.charity.datamodels.ChatListModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private List<ChatListModel> chatListModelList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        init();
    }

    private void init(){
        chatListModelList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(firebaseAuth.getCurrentUser().getUid());
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatListActivity.this));
        getAllChatList();
    }

    private void getAllChatList(){
        databaseReference.child("Chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatListModelList.clear();
                for(DataSnapshot dp : snapshot.getChildren()){
                    ChatListModel chatListModel = dp.getValue(ChatListModel.class);
                    chatListModel.setName(dp.getKey());
                    chatListModelList.add(chatListModel);
                }
                recyclerView.setAdapter(new ChatListAdapter(ChatListActivity.this,chatListModelList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}