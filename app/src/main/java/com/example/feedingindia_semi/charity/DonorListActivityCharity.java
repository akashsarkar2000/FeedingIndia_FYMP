package com.example.feedingindia_semi.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonorListActivityCharity extends AppCompatActivity {


    private Toolbar mToolbar;
    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list_charity);

        mToolbar = findViewById(R.id.all_users_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Connected Donors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor");
        mUsersList = findViewById(R.id.id_all_donors_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));


        mProgressDialog = new ProgressDialog(DonorListActivityCharity.this);
        mProgressDialog.setTitle("Loading All Donors");
        mProgressDialog.setMessage("Please wait while we load all the donors...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();


    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Users> options=
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(mUsersDatabase,Users.class)
                    .setLifecycleOwner(this)
                    .build();

            FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {


                @NonNull
                @Override
                public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    return new UsersViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.donor_single_layout, parent, false));
                }

                @Override
                protected void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int position, @NonNull Users users) {


                    usersViewHolder.setName(users.getName());
                    usersViewHolder.setUserStatus(users.getStatus());
                    usersViewHolder.setUserImage(users.getThumb_image(),getApplicationContext());

                    final String user_id = getRef(position).getKey();

                    mProgressDialog.dismiss();

                    usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent profileIntent = new Intent(DonorListActivityCharity.this, MainActivityCharity.class);
                            profileIntent.putExtra("user_id",user_id);
                            startActivity(profileIntent);

                        }
                    });

                }
            };
            mUsersList.setAdapter(firebaseRecyclerAdapter);
        }

        public static class UsersViewHolder extends RecyclerView.ViewHolder{

            View mView;

            public UsersViewHolder(View itemView){
                super(itemView);
                mView = itemView;
            }

            public void setName(String name){

                TextView userNameView = mView.findViewById(R.id.user_single_name);
                userNameView.setText(name);

            }

            public void setUserStatus(String status){

                TextView userStatusView = mView.findViewById(R.id.user_single_address);
            userStatusView.setText(status);

        }

        public void setUserImage(String thumb_image, Context context){

            CircleImageView userImageView = mView.findViewById(R.id.user_single_image);
            Picasso.get().load(thumb_image).placeholder(R.drawable.default_image).into(userImageView);
        }

    }
}
