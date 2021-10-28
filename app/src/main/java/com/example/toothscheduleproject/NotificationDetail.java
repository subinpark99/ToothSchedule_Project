package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;

public class NotificationDetail extends Activity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    private int hour;       // 시
    private int minute;     // 분


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_detail);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ToothSchedule");

        ImageButton ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);
        ImageButton ibtncheck = (ImageButton) findViewById(R.id.ibtncheck);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        Intent intent = getIntent();

        // 저장버튼 누르면 알림 저장
        ibtncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 데이터 베이스에 시간 저장, setTime()해야함
                // 데이터 베이스에서 시간 가져와서 보여주기

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                String time = hour + " : " + minute;
                Intent intent = new Intent(getApplicationContext(), Notification.class);
                intent.putExtra("time",time);
                startActivity(intent);
            }
        });

        // 뒤로가기
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
