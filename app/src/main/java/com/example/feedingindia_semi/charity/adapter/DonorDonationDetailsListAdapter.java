package com.example.feedingindia_semi.charity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.datamodels.DonorDonationData;
import java.util.List;

public class DonorDonationDetailsListAdapter extends RecyclerView.Adapter<DonorDonationDetailsListAdapter.ChatViewHolder>{

    private List<DonorDonationData> donorDonationDataList;
    Context context;

    public DonorDonationDetailsListAdapter(List<DonorDonationData> donorDonationDataList, Context context) {
        this.donorDonationDataList = donorDonationDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.donor_donation_list_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        DonorDonationData donationData = donorDonationDataList.get(position);
        try {
            holder.email.setText(donationData.getDonationDonorEmail());
            holder.name.setText(donationData.getDonorName());
            holder.phone.setText(donationData.getDonorPhone());
            holder.food_name.setText(donationData.getFoodName());
            holder.food_quantity.setText(donationData.getFoodQuantityKg());
            holder.food_exp.setText(donationData.getFoodExpiryDate());
            holder.food_don_date.setText(donationData.getFoodDonationDate());
            holder.money_rs.setText(donationData.getMoneyAmount());
            holder.money_purpose.setText(donationData.getMoneyPurpose());
            holder.req_date.setText(donationData.getRequirementsDate());
            holder.req_items.setText(donationData.getRequirementsItems());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return donorDonationDataList != null ? donorDonationDataList.size() : 0;
    }
    public static class ChatViewHolder extends RecyclerView.ViewHolder{

        public TextView email, name, phone, food_name, food_quantity, food_exp, food_don_date, money_rs, money_purpose, req_date, req_items;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.charity_donation_donor_list_email);
            name = itemView.findViewById(R.id.charity_donation_donor_list_name);
            phone = itemView.findViewById(R.id.charity_donation_donor_list_phone);
            food_name = itemView.findViewById(R.id.charity_donation_donor_list_food_name);
            food_quantity = itemView.findViewById(R.id.charity_donation_donor_list_food_quantity);
            food_exp = itemView.findViewById(R.id.charity_donation_donor_list_food_exp_date);
            food_don_date = itemView.findViewById(R.id.charity_donation_donor_list_food_don_date);
            money_rs = itemView.findViewById(R.id.charity_donation_donor_list_money_rs);
            money_purpose = itemView.findViewById(R.id.charity_donation_donor_list_money_rs_purose);
            req_date = itemView.findViewById(R.id.charity_donation_donor_list_req_date);
            req_items = itemView.findViewById(R.id.charity_donation_donor_list_req_items);
        }
    }


}
