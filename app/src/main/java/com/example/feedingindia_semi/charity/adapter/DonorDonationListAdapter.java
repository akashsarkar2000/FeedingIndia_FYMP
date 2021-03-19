package com.example.feedingindia_semi.charity.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.DonorDonationDetailsActivity;
import com.example.feedingindia_semi.charity.datamodels.DonorDonationListModel;
import com.example.feedingindia_semi.charity.datamodels.DonorModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonorDonationListAdapter extends RecyclerView.Adapter<DonorDonationListAdapter.DonorDonationListHolder>{

    private Context context;
    private List<DonorDonationListModel> donorDonationListModelList;
    private DatabaseReference databaseReference;

    public DonorDonationListAdapter(Context context, List<DonorDonationListModel> donorDonationListModelList) {
        this.context = context;
        this.donorDonationListModelList = donorDonationListModelList;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor");
    }


    @NonNull
    @Override
    public DonorDonationListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonorDonationListHolder(LayoutInflater.from(context).inflate(R.layout.donor_donation_list_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonorDonationListHolder holder, final int position) {
        DonorDonationListModel donorDonationListModel = donorDonationListModelList.get(position);

        getUserInfoName(donorDonationListModel.getName(),holder.name);
        getUserInfoProfession(donorDonationListModel.getProfession(),holder.profession);
        getUserInfoEmail(donorDonationListModel.getEmail(),holder.email);
        getUserInfoImages(donorDonationListModel.getThumb_image(),holder.image);
        holder.cardView_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DonorDonationDetailsActivity.class);
                intent.putExtra("key",donorDonationListModelList.get(position).getName());
                intent.putExtra("key",donorDonationListModelList.get(position).getProfession());
                intent.putExtra("key",donorDonationListModelList.get(position).getEmail());
                intent.putExtra("key",donorDonationListModelList.get(position).getThumb_image());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donorDonationListModelList.size();
    }

    static class DonorDonationListHolder extends RecyclerView.ViewHolder{
        TextView name, profession, email;
        ConstraintLayout cardView_donation;
        CircleImageView image;
        public DonorDonationListHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.charity_donation_donor_list_name);
            email = itemView.findViewById(R.id.charity_donation_donor_list_email);
            profession = itemView.findViewById(R.id.charity_donation_donor_list_phone);
            cardView_donation = itemView.findViewById(R.id.charity_donation_donor_list_card);
        }
    }

    private void getUserInfoName(final String key, final TextView textView){
        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DonorModel donorDonationModel = snapshot.getValue(DonorModel.class);
                    textView.setText(donorDonationModel.getDonor_name());
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
                DonorModel donorDonationModel = snapshot.getValue(DonorModel.class);
                textView.setText(donorDonationModel.getProfession());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }

        private void getUserInfoEmail(String key, final TextView textView){
            databaseReference.child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DonorModel donorDonationModel = snapshot.getValue(DonorModel.class);
                    textView.setText(donorDonationModel.getEmail());
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
                    DonorModel donorDonationModel = snapshot.getValue(DonorModel.class);
                    Picasso.get().load(donorDonationModel.getImage()).placeholder(R.drawable.default_image).into(imageView);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
