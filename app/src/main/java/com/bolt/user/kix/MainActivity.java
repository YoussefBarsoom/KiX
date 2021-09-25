package com.bolt.user.kix;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout chatBox;
    public int i =0;
    public  int current_pos_field=1;
    public ArrayList<String> field_names = new ArrayList<String>();
    public ArrayList<String> field_phoneNum = new ArrayList<String>();



    // public ArrayList<String> teamPhoneNum;
       LinearLayout x;
    private LinearLayout mGallery_team;

    public int num_message;
    RelativeLayout mLinearLayout ;


    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   //     TextView textView = (TextView) findViewById(R.id.id_index_gallery_item_text1);
//     textView.setText(field_names[0]);

        x = (LinearLayout) findViewById(R.id.sd);
        mLinearLayout = (RelativeLayout) findViewById(R.id.test_animate);
        mInflater = LayoutInflater.from(this);
        initData();
        initfield();
        //initView_team();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_field_view(view);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
      //  navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


public void next_field_view(View view) {
    if (current_pos_field == field_names.size()-1) {
    } else {
        current_pos_field += 1;

        TextView textView = (TextView) findViewById(R.id.id_index_gallery_item_text1);
        textView.setText(field_names.get(current_pos_field));
       Button but = (Button) findViewById(R.id.call_button_field1);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = "+2" + field_phoneNum.get(current_pos_field);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", num, null));
                startActivity(callIntent);
           }
        });
    }
}
public void prev_field_view(View view) {
    if (current_pos_field == 0) {
    } else {
        current_pos_field -= 1;

        TextView textView = (TextView) findViewById(R.id.id_index_gallery_item_text1);
        textView.setText(field_names.get(current_pos_field));
        Button but = (Button) findViewById(R.id.call_button_field1);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                String num = "+2" + field_phoneNum.get(current_pos_field);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", num, null));
                startActivity(callIntent);
            }
    });
    }
    }
    public void gotochat(View view)
    {
        Intent i = new Intent(this, global_chat_view.class);
        startActivity(i);
    }


    private void initData()
    {
        final DatabaseReference myRef_t = FirebaseDatabase.getInstance().getReference("teams");
        myRef_t.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mGallery_team = (LinearLayout) findViewById(R.id.sd);

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    try {
                        View view = mInflater.inflate(R.layout.team_view, mGallery_team, false);
                        TextView txt = (TextView) view.findViewById(R.id.team_names);
                        txt.setText((data.child("teamName").getValue().toString()));
                        Button but = (Button) view.findViewById(R.id.call_button);
                        final String c = data.child("capPhoneNum").getValue().toString();
                        but.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String num = "+2" + c;
                                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", num, null));
                                startActivity(callIntent);
                            }
                        });
                        mGallery_team.addView(view);
                    }
                    catch(NullPointerException c){}

                }
            }
            public void onCancelled(DatabaseError databaseError) { }

        });
    }
            private void initfield()
            {
                final DatabaseReference myRef_t = FirebaseDatabase.getInstance().getReference("fields");
                myRef_t.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            field_names.add(data.child("fieldName").getValue().toString());
                            field_phoneNum.add(data.child("fphoneNum").getValue().toString());

                        }
                    }


            @Override
            public void onCancelled(DatabaseError databaseError) { }

        });
    }


    private void expand() {
        //set Visible

        mLinearLayout.setVisibility(View.VISIBLE);

    }

    private void collapse() {
        //set Visible
        mLinearLayout.setVisibility(View.GONE);




    }



    public void show_field_view(View view) {
        if (mLinearLayout.getVisibility() == View.GONE) {
            expand();
                }
            else {
            collapse();
        }
    }
}