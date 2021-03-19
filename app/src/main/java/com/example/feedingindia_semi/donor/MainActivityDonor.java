package com.example.feedingindia_semi.donor;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.feedingindia_semi.BaseActivity;
import com.example.feedingindia_semi.R;
import com.example.feedingindia_semi.UsersCharity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityDonor extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private Toolbar mToolbar;
    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_donor);

        mAuth = FirebaseAuth.getInstance();
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Feeding India : Donor");

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Charity");

        mUsersList = findViewById(R.id.id_all_charity_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));

        mProgressDialog = new ProgressDialog(MainActivityDonor.this);
        mProgressDialog.setTitle("Loading All Charity");
        mProgressDialog.setMessage("Please wait while we load all the charities...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor").child(mAuth.getCurrentUser().getUid());
        }
    }
        // LOGIN AND REDIRECT TO NEXT PAGE //

        @Override
        protected void onStart() {

            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser == null){
                sendToStart();
            }
            else {
                mUserRef.child("online").setValue("true");
            }
            // Check if user is signed in (non-null) and update UI accordingly.

            FirebaseRecyclerOptions<UsersCharity> options=
                    new FirebaseRecyclerOptions.Builder<UsersCharity>()
                            .setQuery(mUsersDatabase,UsersCharity.class)
                            .setLifecycleOwner(this)
                            .build();

            FirebaseRecyclerAdapter<UsersCharity, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UsersCharity, UsersViewHolder>(options) {


                @NonNull
                @Override
                public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    return new UsersViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.charity_single_layout, parent, false));
                }

                @Override
                protected void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int position, @NonNull UsersCharity users_charity) {

                    usersViewHolder.setName(users_charity.getName());
                    usersViewHolder.setUserStatus(users_charity.getStatus());
                    usersViewHolder.setUserImage(users_charity.getThumb_image(),getApplicationContext());

                    final String user_id = getRef(position).getKey();

                    mProgressDialog.dismiss();

                    usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent profileIntent = new Intent(MainActivityDonor.this, CharityDescriptionActivityDonor.class);
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

                TextView userNameView = mView.findViewById(R.id.charity_single_name);
                userNameView.setText(name);

            }

            public void setUserStatus(String status){

                TextView userStatusView = mView.findViewById(R.id.charity_single_address);
                userStatusView.setText(status);

            }

            public void setUserImage(String thumb_image, Context context){

                CircleImageView userImageView = mView.findViewById(R.id.charity_single_image);
                Picasso.get().load(thumb_image).placeholder(R.drawable.default_image).into(userImageView);
            }

        }


    // FUNCTION FOR LOGOUT AND LOGIN
    private void sendToStart() {
        Intent startIntent = new Intent (MainActivityDonor.this, LoginActivityDonor.class);
        startActivity(startIntent);
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
        }
    }

    // DISPLAY OF MAIN MENU //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    // SIGN OUT //
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.id_main_logout){
            FirebaseAuth.getInstance().signOut();
            sendToStart();  // If Logout Successful then redirect to First page
        }

        if (item.getItemId() == R.id.id_main_queries){

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"feedingindia2021@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback regarding Feeding India Application");
            startActivity(intent);

        }

        if (item.getItemId() == R.id.id_main_settings){

            Intent allUserIntent = new Intent (MainActivityDonor.this, SettingActivityDonor.class);
            startActivity(allUserIntent);

        }
        return true;

    }

}

