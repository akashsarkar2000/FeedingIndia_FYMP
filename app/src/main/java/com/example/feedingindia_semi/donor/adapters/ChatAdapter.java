package com.example.feedingindia_semi.donor.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.datamodels.ChatData;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder>{

    private Context context;
    private List<ChatData> chatDataList;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    public ChatAdapter(Context context, List<ChatData> chatDataList,RecyclerView recyclerView) {
        this.context = context;
        this.chatDataList = chatDataList;
        this.recyclerView = recyclerView;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_card,parent,false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        ChatData chatData = chatDataList.get(position);
        setText(chatData.getMessage(),holder.message);
        setText(chatData.getDatetime(),holder.datetime);
        if(chatData.getUid().equals(firebaseAuth.getCurrentUser().getUid())){
            Log.i("true","same");
            holder.message.setGravity(Gravity.END);
            holder.datetime.setGravity(Gravity.END);
        }else{
            holder.message.setGravity(Gravity.START);
            holder.datetime.setGravity(Gravity.START);
        }
    }

    @Override
    public int getItemCount() {
        return chatDataList.size();
    }

    class ChatHolder extends RecyclerView.ViewHolder{

        private TextView message,datetime;
        private CardView cardView;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            message = itemView.findViewById(R.id.message);
            datetime = itemView.findViewById(R.id.datetime);
        }
    }

    private void setText(String s,TextView t){
        try {
            t.setText(s);
        }catch (Exception e){
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
