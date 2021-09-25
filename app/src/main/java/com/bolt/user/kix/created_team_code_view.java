package com.bolt.user.kix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class created_team_code_view extends AppCompatActivity {
    String code;
    SharedPreferences sharedPreferences;
    private DatabaseReference myRef;

    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_created_team_code_view);
        TextView tv = findViewById(R.id.team_code_view);
         code = create_tem.team_code;
        tv.setText(code);
        final DatabaseReference myRef_t = FirebaseDatabase.getInstance().getReference("teamPhoneNum");
        final DatabaseReference myRef_t_name = FirebaseDatabase.getInstance().getReference("teamNames");
        final DatabaseReference myRef_t_code = FirebaseDatabase.getInstance().getReference("teamcodes");
        final DatabaseReference myRef_t_username = FirebaseDatabase.getInstance().getReference("usernames").child(create_tem.userName_final);
        final Handler handler = new Handler();
        myRef = FirebaseDatabase.getInstance().getReference("teams").child(create_tem.team_code);
        myRef.child("teamName").setValue(create_tem.team_name_final);
        myRef_t_name.child("teamName").child(create_tem.team_name_final).setValue(create_tem.team_name_final);
        myRef = FirebaseDatabase.getInstance().getReference("teams").child(create_tem.team_code);
        myRef.child("capPhoneNum").setValue(create_tem.cap_phoneNum_final);
        myRef_t.child("phoneNumber").child(create_tem.cap_phoneNum_final).setValue(create_tem.cap_phoneNum_final);
        myRef_t_code.child("codes").child(create_tem.team_code.toString()).setValue(create_tem.team_code.toString());
        myRef.child("teamMates").child("teamMateName").setValue(create_tem.userName_final);
        myRef_t_username.setValue(create_tem.userName_final);
        sharedPreferences = getSharedPreferences(PREF_NAME, 0);
        editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, create_tem.userName_final);
        editor.putString(KEY_PASS, create_tem.team_code.toString());
        editor.apply();
        editor.commit();
    }

    public void sendbywhatsapp(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, " Hey! We're using KiX! Download now and join our Team " + create_tem.team_name_final + ". Team Code is " + code);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);

    }

    public void move_to_main_view(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


}
