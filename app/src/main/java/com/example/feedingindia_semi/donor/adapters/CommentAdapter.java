package com.example.feedingindia_semi.donor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.donor.datamodels.CommentData;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ChatViewHolder>{

    private List<CommentData> commentDataList;
    Context context;

    public CommentAdapter(List<CommentData> commentDataList, Context context) {
        this.commentDataList = commentDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        CommentData commentData = commentDataList.get(position);
        try {
            holder.date.setText(commentData.getDatetime());
            holder.email.setText(commentData.getEmail());
            holder.message.setText(commentData.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return commentDataList != null ? commentDataList.size() : 0;
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{

        public TextView email,message,date;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);
        }
    }
}
