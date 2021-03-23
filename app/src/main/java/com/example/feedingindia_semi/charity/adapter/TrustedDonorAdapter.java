package com.example.feedingindia_semi.charity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.datamodels.CommentData;
import com.example.feedingindia_semi.charity.datamodels.TrustedDonorData;

import java.util.List;

public class TrustedDonorAdapter extends RecyclerView.Adapter<TrustedDonorAdapter.ChatViewHolder>{

    private List<TrustedDonorData> trustedDonorDataList;
    Context context;

    public TrustedDonorAdapter(List<TrustedDonorData> trustedDonorDataList, Context context) {
        this.trustedDonorDataList = trustedDonorDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.trusted_donor_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        TrustedDonorData commentData = trustedDonorDataList.get(position);
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
        return trustedDonorDataList != null ? trustedDonorDataList.size() : 0;
    }
    public static class ChatViewHolder extends RecyclerView.ViewHolder{

        public TextView email,message,date;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.charity_email_trusted);
            message = itemView.findViewById(R.id.charity_info_trusted);
            date = itemView.findViewById(R.id.charity_date_trusted);
        }
    }


}
