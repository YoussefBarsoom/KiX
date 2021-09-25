package com.bolt.user.kix;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class global_chat_view extends AppCompatActivity {
    public ArrayList<String> all_Chat = new ArrayList<String>();
    private LinearLayout chatBox;
    private LayoutInflater mInflater;
    SharedPreferences sharedPreferences;
    ScrollView x;
    SharedPreferences.Editor editor;
    public int i =0;
    private static final String KEY_USERNAME = "username";
    Button sendButton;
    public int num_message=0;
    private static final String PREF_NAME = "prefs";

    public ArrayList<String> new_messages = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_chat_view);
        mInflater = LayoutInflater.from(this);
         x = (ScrollView) findViewById(R.id.global_chat);
        x.fullScroll(View.FOCUS_DOWN);
        //getMessage();
        sendButton = (Button) findViewById(R.id.send);
        sendButton.setClickable(false);
        updateChat();

    }
    public void back_to_main_view (View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void send() {
        final DatabaseReference myRef_chat = FirebaseDatabase.getInstance().getReference("GlobalChat");
        //getLastValue();
        EditText tc = (EditText) findViewById(R.id.editMess);
        String message = tc.getText().toString();
        chatBox = (LinearLayout) findViewById(R.id.chat_sent);
        View view = mInflater.inflate(R.layout.message_sent, chatBox, false);
        TextView txt = (TextView) view.findViewById(R.id.textView2);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedUsername=sharedPreferences.getString(KEY_USERNAME,"");
        txt.setText(savedUsername+":"+message);
        view.setBackgroundResource(R.drawable.chat_base1x);
        chatBox.addView(view);
        all_Chat.add(message);
        myRef_chat.child(new Integer(num_message+1).toString()).child("code").setValue(new Integer(num_message+1).toString());
        myRef_chat.child(new Integer(num_message+1).toString()).child("message").setValue(savedUsername+":"+message);
        num_message++;
    }

    public void excuteSend(View view) {
        mInflater = LayoutInflater.from(this);
        send();
        EditText tc = (EditText) findViewById(R.id.editMess);
        tc.setText("");
        ScrollView sc = (ScrollView) findViewById(R.id.global_chat);
    }
    public void getMessage() {
        final DatabaseReference myRef_mess = FirebaseDatabase.getInstance().getReference("GlobalChat");
        myRef_mess.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    chatBox = (LinearLayout) findViewById(R.id.chat_sent);
                    View view = mInflater.inflate(R.layout.message_sent, chatBox, false);
                    TextView txt = (TextView) view.findViewById(R.id.textView2);
                    view.setBackgroundResource(R.drawable.chat_base_pink1x);
                    txt.setText(data.child("message").getValue().toString());
                    chatBox.addView(view);
                    num_message=Integer.parseInt(data.child("code").getValue().toString());

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }
    public void get_newMessage()
    {

        final DatabaseReference myRef_mess = FirebaseDatabase.getInstance().getReference("GlobalChat");
        myRef_mess.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if((Integer.parseInt(data.child("code").getValue().toString()))>num_message)
                    {
                        new_messages.add(data.child("message").getValue().toString());
                        num_message= (Integer.parseInt(data.child("code").getValue().toString()));
                    }}

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }
    public void postMessage() {
        while (!new_messages.isEmpty()) {
            chatBox = (LinearLayout) findViewById(R.id.chat_sent);
            View view = mInflater.inflate(R.layout.message_sent, chatBox, false);
            TextView txt = (TextView) view.findViewById(R.id.textView2);
            view.setBackgroundResource(R.drawable.chat_base_pink1x);
            txt.setText(new_messages.get(0));
            new_messages.remove(0);
            chatBox.addView(view);
            num_message++;
            x.fullScroll(View.FOCUS_DOWN);
        }
        ScrollView x = (ScrollView) findViewById(R.id.global_chat);
        new_messages.clear();
        i=0;

    }

    public void updateChat()
    {
        get_newMessage();
        postMessage();
        final Handler hand = new Handler();
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendButton.setClickable(true);

            }
        }, 3500);
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                get_newMessage();
                postMessage();
                handler.postDelayed(this,3500);
            }
        }, 1000);

    }

}
