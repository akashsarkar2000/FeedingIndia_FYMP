package com.example.feedingindia_semi.charity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.Users;
import com.example.feedingindia_semi.UsersCharity;
import com.example.feedingindia_semi.charity.ChatActivity;
import com.example.feedingindia_semi.charity.datamodels.ChatListModel;
import com.example.feedingindia_semi.charity.datamodels.DonorModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListHolder>{

    private Context context;
    private List<ChatListModel> chatListModelList;
    private DatabaseReference databaseReference;

    public ChatListAdapter(Context context, List<ChatListModel> chatListModelList) {
        this.context = context;
        this.chatListModelList = chatListModelList;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor");
    }

    @NonNull
    @Override
    public ChatListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListHolder(LayoutInflater.from(context).inflate(R.layout.chat_list_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListHolder holder, final int position) {
            ChatListModel chatListModel = chatListModelList.get(position);

            getUserInfo(chatListModel.getName(),holder.name);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("key",chatListModelList.get(position).getName());
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return chatListModelList.size();
    }

    class ChatListHolder extends RecyclerView.ViewHolder{
        TextView name;
        CardView cardView;
        public ChatListHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.card);
        }
    }

    //dekh bhai ye function use karra mi donor ka data lane ke liye, ab maine bas name laya, tu chahe toh photo waghere bhi la skta usko cool dikhane ke liye
    // tu bas firebase ke child ki chronology samaz baki apne aap tu figure out kar lega
    private void getUserInfo(String key, final TextView textView){
        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DonorModel donorModel = snapshot.getValue(DonorModel.class);
                textView.setText(donorModel.getDonor_name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
