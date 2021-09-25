package com.bolt.user.kix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class first_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_view);
    }
    public void openCreateTeam (View view)
    {
        Intent i = new Intent(this, create_tem.class);
        startActivity(i);
    } public void openJoinTeam (View view)
    {
        Intent i = new Intent(this,join_team_enter_code_view.class);
        startActivity(i);
    }

}
