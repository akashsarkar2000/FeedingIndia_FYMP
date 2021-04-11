package com.example.feedingindia_semi.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.Users;
import com.example.feedingindia_semi.donor.CharityDescriptionActivityDonor;
import com.example.feedingindia_semi.donor.CommentActivityDonor;
import com.example.feedingindia_semi.charity.adapter.CommentAdapter;
import com.example.feedingindia_semi.charity.datamodels.CommentData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllCommentsByDonorForCharity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mUsersList;
    private AlertDialog.Builder builder;
    private DatabaseReference mUsersDatabase;
    private ProgressDialog mProgressDialog;
    private FirebaseUser mCurrentUser;
    private List<CommentData> commentDataList;
    private String charityKey;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private View view;
    public String key;
    private RecyclerView recyclerView;
    public static String deletekey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comments_by_donor_for_charity);

        recyclerView = findViewById(R.id.id_all_charity_comment_list_charity_side);
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete") .setTitle("Delete Comment");

        mToolbar = findViewById(R.id.charity_side_comment_all_users_appBar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Donor's Reviews");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view = this.getCurrentFocus();
        commentDataList = new ArrayList<CommentData>();
        charityKey = getIntent().getStringExtra("key");
        firebaseAuth = FirebaseAuth.getInstance();
        //Toast.makeText(this, "LOL NIGGA", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        key = intent.getStringExtra("user_id");

        Log.i("mykey",firebaseAuth.getCurrentUser().getEmail());


        //  USER OBJECT REFERENCE IN KEY //
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity").child(current_uid);
        databaseReference.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mProgressDialog = new ProgressDialog(AllCommentsByDonorForCharity.this);
        mProgressDialog.setTitle("Loading All Reviews");
        mProgressDialog.setMessage("Please wait while we load all the reviews...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        generateAlertBuilder();
        getAllComments();
    }

    private void getAllComments(){
        databaseReference.child("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentDataList.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    CommentData commentData = data.getValue(CommentData.class);
                    commentData.setKeyvalue(data.getKey());
                    commentDataList.add(commentData);
                    CommentAdapter commentAdapter = new CommentAdapter(commentDataList, AllCommentsByDonorForCharity.this,builder);
                    recyclerView.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mProgressDialog.dismiss();
    }

    public void generateAlertBuilder(){

        builder.setMessage("Do you really want to delete this comment ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        deleteComment(getDeletekey());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
    }

    private void deleteComment(String uid){
        mUsersDatabase.child(uid).removeValue();
    }

    public static void setDeletekey(String deletekey) {
        deletekey = deletekey;
    }

    public static String getDeletekey() {
        return deletekey;
    }
}
