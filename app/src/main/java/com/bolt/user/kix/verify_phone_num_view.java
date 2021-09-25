package com.bolt.user.kix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class verify_phone_num_view extends AppCompatActivity {
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    String verifyCodeEntered;
    String Url;
    String verifycode;
    Button verifyButton;
    Button backbutton;
    int i =0;
    Button xi;
    WebView sm;
    SharedPreferences sharedPreferences;
    private DatabaseReference myRef;

    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_num_view);
        backbutton =(Button) findViewById(R.id.back_button2);
        randCode();
        Url = "https://www.smsmisr.com/api/send?username=P2GM4H9K&password=P2GM4H&language=2&sender=MilliM&mobile=" +create_tem.cap_phoneNum_final + "&message=Your KiX Verification Code is: " + verifycode;
        sm = (WebView)findViewById(R.id.sms_webview);
       sm.setWebViewClient(new WebViewClient() {
            @Override
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
               view.loadUrl(url);

                return true;
            }
        });
       verifyButton =(Button) findViewById(R.id.verify_button);
        sm.loadUrl( Url);
        e1 = (EditText) findViewById(R.id.editText9);
        e2 = (EditText) findViewById(R.id.editText7);
        e3 = (EditText) findViewById(R.id.editText6);
        e4 = (EditText) findViewById(R.id.editText5);
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (e1.getText().toString().length() == 0) {
                    e2.requestFocus();

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (e2.getText().toString().length() == 0) {
                    e3.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (e3.getText().toString().length() == 0) {
                    e4.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (e4.getText().toString().length() == 0) {

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void click_verify(View view) {
        verifyButton.setClickable(false);
        verifyCodeEntered = e1.getText().toString() + "" + e2.getText().toString() + "" + e3.getText().toString() + "" + e4.getText().toString();
        final DatabaseReference myRef_t = FirebaseDatabase.getInstance().getReference("teamPhoneNum");
        final DatabaseReference myRef_t_name = FirebaseDatabase.getInstance().getReference("teamNames");
        final DatabaseReference myRef_t_code = FirebaseDatabase.getInstance().getReference("teamcodes");
        final DatabaseReference myRef_t_username = FirebaseDatabase.getInstance().getReference("usernames").child(create_tem.userName_final);
        final Handler handler = new Handler();
         xi = (Button) findViewById(R.id.button7);

        if (verifyCodeEntered.equals(verifycode)) {


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


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    i++;
                    xi.performClick();
                }
            }, 3000);
        }
        else {
            TextView t =(TextView) findViewById(R.id.error_dis);
            t.setText("Incorrect code");
            verifyButton.setClickable(true);
        }
    }
    public void back_to_first_view (View view)
    {
        Intent i = new Intent(this, create_tem.class);
        startActivity(i);
    }
    public void move_to_main_view (View view)
    {
        Intent i = new Intent(this, created_team_code_view.class);
        startActivity(i);
    }

    public void editUrl() {
        Url = "https://www.smsmisr.com/api/send?username=P2GM4H9K&password=P2GM4H&language=2&sender=MilliM&mobile=" + create_tem.cap_phoneNum_final + "&message=Your KiX Verification Code is: " + verifycode;
    }

    public void randCode() {
        Random ran = new Random();
        verifycode = "";
        for (int i = 0; i < 4; i++) {
            int ran_num = ran.nextInt(2);
            if (ran_num == 1) {
                char x1 = (char) (ran.nextInt(91 - 65) + 65);
                verifycode += x1 + "";
            } else {
                char x3 = (char) (ran.nextInt(58 - 48) + 48);
                verifycode += x3 + "";

            }
        }
    }
}
