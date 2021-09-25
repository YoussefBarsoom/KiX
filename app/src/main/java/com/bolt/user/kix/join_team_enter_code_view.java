package com.bolt.user.kix;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class join_team_enter_code_view extends AppCompatActivity {
public boolean valid_code;
public boolean valid_user;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    EditText editText;
    EditText userName;
    TextView t;
    Button v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t = (TextView) findViewById(R.id.errors);

        setContentView(R.layout.activity_join_team_enter_code_view);
         v = (Button) findViewById(R.id.join_team);

    }
    public void move_to_main_view(View view) {
        v.setClickable(false);
          t = (TextView) findViewById(R.id.errors);
         editText = (EditText) findViewById(R.id.team_join_code);
         userName =(EditText) findViewById(R.id.user_name_field_join);
        checkAv_code(editText.getText().toString());
        checkAv_userName(userName.getText().toString());
        t.setText("Loading...");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
        if (valid_code && valid_user)
        {
            DatabaseReference mate = FirebaseDatabase.getInstance().getReference("teams").child(editText.getText().toString());
            DatabaseReference myRef_t_username = FirebaseDatabase.getInstance().getReference("usernames").child(userName.getText().toString());
            myRef_t_username.setValue(userName.getText().toString());
            mate.child("teamMates").child(userName.getText().toString()).setValue(userName.getText().toString());
            sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(KEY_USERNAME,userName.getText().toString() );
            editor.putString(KEY_PASS, editText.getText().toString());
            editor.commit();

            Button but =(Button) findViewById(R.id.button5);

            but.performClick();
        }


            }
        }, 5000);

    }
    public void back_to_first_view (View view)
    {
        Intent i = new Intent(this, first_view.class);
        startActivity(i);
    }
    public  void checkAv_code(final String code_created) {
        final DatabaseReference myRef_t = FirebaseDatabase.getInstance().getReference("teamcodes");
        myRef_t.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(code_created).exists()) {
                   valid_code=true;

                    }
                    else
                    {valid_code = false;
                        t.setText("Invalid Code");
                    v.setClickable(true);}
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public  void checkAv_userName(final String user_name) {
        final DatabaseReference myRef_t = FirebaseDatabase.getInstance().getReference("usernames");
        myRef_t.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(user_name).exists()) {
                        valid_user=false;
                        t.setText("Username Already exists");
                        v.setClickable(true);
                    }
                    else
                    {valid_user = true;}
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
        public void move_to_main (View view)
        {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

}
