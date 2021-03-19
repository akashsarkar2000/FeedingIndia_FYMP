package com.example.feedingindia_semi.charity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        return new ChatListHolder(LayoutInflater.from(context).inflate(R.layout.chat_list_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListHolder holder, final int position) {
            ChatListModel chatListModel = chatListModelList.get(position);

        getUserInfoName(chatListModel.getName(),holder.name);
        getUserInfoProfession(chatListModel.getProfession(),holder.profession);
        getUserInfoImages(chatListModel.getThumb_image(),holder.image);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("key",chatListModelList.get(position).getName());
                    intent.putExtra("key",chatListModelList.get(position).getProfession());
                    intent.putExtra("key",chatListModelList.get(position).getThumb_image());
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return chatListModelList.size();
    }

    static class ChatListHolder extends RecyclerView.ViewHolder{
        TextView name, profession;
        ConstraintLayout cardView;
        CircleImageView image;
        public ChatListHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.charity_chat_donor_name);
            profession = itemView.findViewById(R.id.charity_chat_donor_profession);
            image = itemView.findViewById(R.id.charity_chat_donor_images);
            cardView = itemView.findViewById(R.id.charity_chat_donor_card);
        }
    }

    private void getUserInfoName(String key, final TextView textView){
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

    private void getUserInfoProfession(String key, final TextView textView){
        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DonorModel donorModel = snapshot.getValue(DonorModel.class);
                textView.setText(donorModel.getProfession());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserInfoImages(String key, final CircleImageView imageView){
        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DonorModel donorModel = snapshot.getValue(DonorModel.class);
                Picasso.get().load(donorModel.getImage()).placeholder(R.drawable.default_image).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
