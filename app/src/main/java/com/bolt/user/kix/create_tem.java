package com.bolt.user.kix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class create_tem extends AppCompatActivity {
    public static String team_name_final;
    public static String cap_phoneNum_final;
    public static String userName_final;
    Button createButton;
    public EditText team_name;
    public EditText user_name;
    public EditText phone_num;
    public boolean valid_name;
    public boolean valid_user_name;
    public boolean valid_num;
    private DatabaseReference myRef;
    public static String team_code = "";
    public TextView tv;
    public Button t;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tem);
        Button v = (Button) findViewById(R.id.create_team_button2);
        tv=(TextView) findViewById(R.id.error_view);
        tv.setText("");

         t = (Button) findViewById(R.id.button6);
        createButton =(Button) findViewById(R.id.create_team_button2);



    }

    public static boolean check_name_valid(String team_name) {
        if (team_name.length() <= 20 && team_name.length() >= 5) {
            return true;
        }
        return false;
    }

    public static boolean check_num_valid(String phone_num) {
        if (phone_num.length() == 11) {

            return true;
        }
        return false;
    }

    public void display_error(String error) {
        TextView error_display = (TextView) findViewById(R.id.error_view);
        error_display.setText(error);
    }

    public void create_team_clicked(View view) {

        team_name = (EditText) findViewById(R.id.team_name_field);
        phone_num = (EditText) findViewById(R.id.team_captain_phone_num);
        user_name = (EditText) findViewById(R.id.user_name_field);
        createButton.setClickable(false);
        if (!(check_num_valid(phone_num.getText().toString()))) {

                display_error("Invalid Phone Number");
                createButton.setClickable(true);

        } else if (!(check_name_valid(team_name.getText().toString())))

        {
            display_error("Team name must be between 5 to 20 letters.");
            createButton.setClickable(true);

        } else {
            final DatabaseReference myRef_t = FirebaseDatabase.getInstance().getReference("teamPhoneNum");
            final DatabaseReference myRef_t_name = FirebaseDatabase.getInstance().getReference("teamNames");
            final DatabaseReference myRef_t_code = FirebaseDatabase.getInstance().getReference("teamcodes");
            final DatabaseReference myRef_t_username = FirebaseDatabase.getInstance().getReference("usernames").child(user_name.getText().toString());



            //while (!checkAv_ranCode(team_code)) {
            //}


            checkAv_teamNme(team_name.getText().toString());
            checkAv_phoneNum(phone_num.getText().toString());
            checkAv_userNme(user_name.getText().toString());
            display_error("Loading...");
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (valid_name && valid_num&&valid_user_name) {
                        randCode();
                      //  checkAv_code(team_code);
                        display_error("");
                        cap_phoneNum_final = phone_num.getText().toString();
                        userName_final = user_name.getText().toString();
                        team_name_final = team_name.getText().toString();
                      //  myRef.child("teamName").setValue((team_name.getText().toString()));
                      //  myRef_t_name.child("teamName").child((team_name.getText().toString())).setValue((team_name.getText().toString()));
                      //  myRef = FirebaseDatabase.getInstance().getReference("teams").child(team_code);
                      //  myRef.child("capPhoneNum").setValue(phone_num.getText().toString());
                      //  myRef_t.child("phoneNumber").child(phone_num.getText().toString()).setValue(phone_num.getText().toString());
                        // myRef_t_code.child("codes").child(team_code.toString()).setValue(team_code.toString());
                      //  myRef.child("teamMates").child("teamMateName").setValue(user_name.getText().toString());
                      //  myRef_t_username.setValue(user_name.getText().toString());
                      //  sharedPreferences = getSharedPreferences(PREF_NAME, 0);
                      //  editor = sharedPreferences.edit();
                      //  editor.putString(KEY_USERNAME, user_name.getText().toString());
                       // editor.putString(KEY_PASS, team_code.toString());
                      //  editor.apply();
                      //  editor.commit();
                        t.performClick();
                    }
                }
            }, 6000);
            if (valid_name && valid_num&&valid_user_name) {
                cap_phoneNum_final = phone_num.getText().toString();

                t.performClick();
            }


        }

    }
    public void move_to_main (View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }public void back_to_first_view (View view)
    {
        Intent i = new Intent(this, first_view.class);
        startActivity(i);
    }

    public void click(View view)
    {
        Intent i = new Intent(this, created_team_code_view.class);
        startActivity(i);}

    public void randCode() {
        Random ran = new Random();
        team_code="";
        for (int i = 0; i < 5;i++) {
            int ran_num = ran.nextInt(2);
            if (ran_num == 1) {
                char x1 = (char) (ran.nextInt(91 - 65) + 65);
                team_code += x1 + "";
            } else {
                char x3 = (char) (ran.nextInt(58 - 48) + 48);
                team_code += x3 + "";

            }
        }
        // TextView tv = findViewById(R.id.team_code_view);
        //  tv.setText(team_code);
        //65 to91,48to58
    }



    public void checkAv_teamNme(final String team_name_created) {


        final DatabaseReference myRf_t_name = FirebaseDatabase.getInstance().getReference("teamNames");

        myRf_t_name.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(team_name_created).exists()) {
                        display_error("Team Name already exists /n Try again");
                        valid_name=false;
                        createButton.setClickable(true);

                    } else {

                        valid_name=true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        team_name_final=team_name_created;

    }
    public void checkAv_userNme(final String user_name_created) {


        final DatabaseReference myRf_t_name = FirebaseDatabase.getInstance().getReference("usernames");

        myRf_t_name.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(user_name_created).exists()) {
                        display_error("User Name already exists /n Try again");
                        valid_user_name=false;
                        createButton.setClickable(true);

                    } else {
                        valid_user_name=true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void checkAv_phoneNum(final String phoneNum_created) {
        final DatabaseReference myRef_t = FirebaseDatabase.getInstance().getReference("teamPhoneNum");
        myRef_t.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(phoneNum_created).exists()) {
                        display_error("Phone Number already exists /n Try again");
                        createButton.setClickable(true);

                        valid_num  =false;
                    } else {
                        valid_num=true;

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void checkAv_code(final String code_created) {
        final DatabaseReference myRef_t = FirebaseDatabase.getInstance().getReference("teamcodes");
        myRef_t.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (!data.child(code_created).exists()) {
                        valid_num=true;

                    } else {
                        team_code ="";
                        randCode();
                        checkAv_code(team_code);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
