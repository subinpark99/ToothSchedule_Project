package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notification extends Activity implements View.OnClickListener{

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    NotificationAdapter adapter;
    private ArrayList<NotificationInfo> lstAlarmTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ToothSchedule");
        ImageButton ibtnBack = findViewById(R.id.ibtnBack);
        ImageButton ibtnAddAlarm = findViewById(R.id.ibtnAddAlarm);

        Switch swAlarm = (Switch)findViewById(R.id.swAlarm);
        ListView lvAlarm = (ListView) findViewById(R.id.lvAlarm);

        ibtnBack.setOnClickListener(this);
        ibtnAddAlarm.setOnClickListener(this);

        lstAlarmTime = new ArrayList<>();

        // Adapter 생성
        adapter = new NotificationAdapter(Notification.this, R.layout.notification_item, lstAlarmTime);
        lvAlarm.setAdapter(adapter);

        swAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // ON일때 알람보여줌
                if(isChecked) {
                    ibtnAddAlarm.setVisibility(View.VISIBLE);
                    lvAlarm.setVisibility(View.VISIBLE);

                    // 아이템 클릭시 작동
                    lvAlarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), NotificationDetail.class);
                            intent.putExtra("title","title");
                            startActivity(intent);
                        }
                    });

                } else {    // OFF일땐 알람 보이지 않음
                    ibtnAddAlarm.setVisibility(View.INVISIBLE);
                    lvAlarm.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBack:
                finish();
                break;
            case R.id.ibtnAddAlarm:         // 리스트뷰 추가
                String title = "알람";
                mDatabaseRef.child("UserInfo").orderByChild("idToken").equalTo(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInfo userInfo = null;

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            userInfo = dataSnapshot.getValue(UserInfo.class);
                        }
                        if ( userInfo != null ) {
                            NotificationInfo AlarmTime = new NotificationInfo();

                            AlarmTime.setTitle(title);
                            lstAlarmTime.add(AlarmTime);
                            adapter.notifyDataSetChanged();
                        }
                        userInfo.setLstAlarmTime(lstAlarmTime);

                        // 서버 DB에 정보 Update
                        mDatabaseRef.child("UserInfo").child(mFirebaseAuth.getCurrentUser().getUid()).setValue(userInfo);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
        }
    }
}
