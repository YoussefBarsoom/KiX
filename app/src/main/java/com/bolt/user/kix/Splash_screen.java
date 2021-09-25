package com.bolt.user.kix;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_screen extends AppCompatActivity {
    SharedPreferences pass;
    SharedPreferences userName;

    boolean val_user;
    boolean val_code;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public int i =0;
    String savedUsername;


    private static final String PREF_NAME = "prefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkMateExist();
            }
        },4000);


    }
    public void checkAv_pass(final String password_created) {


            final DatabaseReference myRf_t_name = FirebaseDatabase.getInstance().getReference("teamcodes");

            myRf_t_name.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child(password_created).exists()) {
                            val_code = true;
                        }

                        else {
                        val_code=false;
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        public  void movetofirstview()
        {
            Intent i = new Intent(this, first_view.class);
            startActivity(i);
        }
    public  void movetomainview()
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void checkMateExist() {

        savedUsername = sharedPreferences.getString(KEY_USERNAME, "");
        if (savedUsername.equals("")) {
            movetofirstview();
        } else {
            movetomainview();

        //    final DatabaseReference myRf_t_name = FirebaseDatabase.getInstance().getReference().child("usernames");

          //  myRf_t_name.addListenerForSingleValueEvent(new ValueEventListener() {
            //    @Override
              //  public void onDataChange(DataSnapshot dataSnapshot) {
                //    for (DataSnapshot data : dataSnapshot.getChildren()) {
                //        if (data.child(savedUsername).exists()) {
                  //      } else {
                    //        movetofirstview();
                    //    }

                    }
                }


          //      @Override
            //    public void onCancelled(DatabaseError databaseError) {

              //  }
            //});

        }


