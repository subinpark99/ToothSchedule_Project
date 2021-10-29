package com.example.toothscheduleproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Statistic extends Activity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic);



        ImageButton ibtnBack = (ImageButton)findViewById(R.id.ibtnBack);
        TextView tvAvgCount = (TextView)findViewById(R.id.tvAvgCount);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ToothSchedule");

        mDatabaseRef.child("UserInfo").orderByChild("idToken").equalTo(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo = null;
                ToothTimeInfo toothTimeInfo = null;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    userInfo = dataSnapshot.getValue(UserInfo.class);
                    toothTimeInfo = dataSnapshot.getValue(ToothTimeInfo.class);
                }

                if ( userInfo != null ) {

                    ArrayList try_brush = userInfo.getLstToothTime();
                    int count = try_brush.size();
                    if(count != 0) {
                        tvAvgCount.setText("현재 양치 횟수는 : " + (count) + "회");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
