package com.example.feedingindia_semi.donor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.datamodels.TrustedDonorData;
import com.example.feedingindia_semi.donor.datamodels.TrustedDonorByCharityData;

import java.util.List;

public class TrustedDonorByCharityAdapter extends RecyclerView.Adapter<TrustedDonorByCharityAdapter.ChatViewHolder>{

    private List<TrustedDonorByCharityData> trustedDonorByCharityData;
    Context context;

    public TrustedDonorByCharityAdapter(List<TrustedDonorByCharityData> trustedDonorByCharityData, Context context) {
        this.trustedDonorByCharityData = trustedDonorByCharityData;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.trusted_donor_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        TrustedDonorByCharityData commentData = trustedDonorByCharityData.get(position);
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
        return trustedDonorByCharityData != null ? trustedDonorByCharityData.size() : 0;
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
