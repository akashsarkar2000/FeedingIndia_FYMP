package com.example.feedingindia_semi.charity.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.charity.datamodels.CommentData;
import com.example.feedingindia_semi.charity.datamodels.TrustedDonorData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TrustedDonorAdapter extends RecyclerView.Adapter<TrustedDonorAdapter.ChatViewHolder>{

    private List<TrustedDonorData> trustedDonorDataList;
    Context context;
    private AlertDialog.Builder builder;
    private DatabaseReference databaseReference;
    private String key;
    private FirebaseAuth firebaseAuth;

    public TrustedDonorAdapter(List<TrustedDonorData> trustedDonorDataList, Context context, AlertDialog.Builder builder) {
        this.trustedDonorDataList = trustedDonorDataList;
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        this.builder = new AlertDialog.Builder(context);
        this.builder.setMessage("Delete") .setTitle("Delete Details");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(firebaseAuth.getCurrentUser().getUid()).child("Trusted_Donor");
        generateAlertBuilder();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.trusted_donor_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, final int position) {
        TrustedDonorData commentData = trustedDonorDataList.get(position);
        try {
            holder.date.setText(commentData.getDatetime());
            holder.email.setText(commentData.getEmail());
            holder.message.setText(commentData.getMessage());
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    log(1);
                    setKey(trustedDonorDataList.get(position).getKeyvalue());
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                    alert.setTitle("Delete Details");
                    alert.show();
                    return true;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return trustedDonorDataList != null ? trustedDonorDataList.size() : 0;
    }
    public static class ChatViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public TextView email,message,date;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.charity_email_trusted);
            message = itemView.findViewById(R.id.charity_info_trusted);
            date = itemView.findViewById(R.id.charity_date_trusted);
            cardView = itemView.findViewById(R.id.card_trusted_donor);
        }
    }

    public void generateAlertBuilder(){
        builder.setMessage("Do you really want to delete this details ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        log(3);
                        deleteComment(getKey(),dialog);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        log(2);
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
    }

    private void deleteComment(String uid, DialogInterface dialog){
        log(4);
        Log.i("delete uid", uid);
        databaseReference.child(uid).removeValue();
        dialog.dismiss();
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    private void log(int i){
        Log.i("logram", i+"");
    }
}
