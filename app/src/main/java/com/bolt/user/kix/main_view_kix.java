package com.bolt.user.kix;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolt.user.kix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class main_view_kix extends AppCompatActivity {
    private LinearLayout mGallery;
    private LinearLayout mGallery_team;
    private HorizontalScrollView horizontalScrollView;
private LinearLayout chatBox;

   public ArrayList<String> field_names = new ArrayList<String>();
    public ArrayList<String> teamPhoneNum;
    private LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_kix);
        mInflater = LayoutInflater.from(this);

    }
    private void initData()
    {
        final DatabaseReference myRef_t = FirebaseDatabase.getInstance().getReference("teams");
        myRef_t.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Map singleUser = (Map) data.getValue();

                    field_names.add((String)( data.child("teamName").getValue()));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void initPhoneNum()
    {
    }

   // private void initView_field() {
     //   mGallery = (LinearLayout) findViewById(R.id.id_gallery);
       // for (int i = 0; i < field_names.length; i++) {
//
          //          View view = mInflater.inflate(R.layout.field_tab, mGallery, false);
         //        RelativeLayout relativeLayout = view.findViewById(R.id.plz_work);
         //      relativeLayout.setBackgroundResource(R.drawable.oectangle);
         //    ImageView img = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
         //   img.setImageResource(R.drawable.fake_logo_kix_2);
         //   TextView txt = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
         //   txt.setText("Field " + field_names[i]);
         //   Button but = (Button) view.findViewById(R.id.call_button);
         //   mGallery.addView(view);
       // }

    public void call_num(String phoneNum) {
        String num = "+2"+phoneNum;
        Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",num,null));
        startActivity(callIntent);
    }
        private void initView_team()
        {
            mGallery_team = (LinearLayout) findViewById(R.id.sd);
            for (int i = 0; i < field_names.size(); i++)
            {
                final int z=i;
                View view = mInflater.inflate(R.layout.team_view, mGallery_team, false);

                TextView txt = (TextView) view.findViewById(R.id.team_names);
                txt.setText("Team "+field_names.get(i));
                Button but = (Button) view.findViewById(R.id.call_button);
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String num = "+2"+"01000842877";
                        Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",num,null));
                        startActivity(callIntent);
                    }
                });
                mGallery_team.addView(view);
            }
    }
}

