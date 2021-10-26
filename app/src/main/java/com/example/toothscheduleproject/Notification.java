package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Notification extends Activity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    private NotificationInfo list;
    private ArrayList<NotificationInfo> lstAlarmTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ToothSchedule");

        ImageButton ibtnBack = (ImageButton)findViewById(R.id.ibtnBack);
        Switch swAlarm = (Switch)findViewById(R.id.swAlarm);
        ImageButton ibtnAddAlarm = (ImageButton)findViewById(R.id.ibtnAddAlarm);
        ListView lvAlarm = (ListView) findViewById(R.id.lvAlarm);

        lstAlarmTime = new ArrayList<>();

        NotificationAdapter adapter = new NotificationAdapter(Notification.this, R.layout.notification_item, lstAlarmTime);
        lvAlarm.setAdapter(adapter);

        swAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // ON일때 알람보여줌
                if(isChecked) {
                    ibtnAddAlarm.setVisibility(View.VISIBLE);
                    lvAlarm.setVisibility(View.VISIBLE);

                    // 리스트뷰에 데이터 추가하기
                    ibtnAddAlarm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.addItem("9:00");
                            adapter.notifyDataSetChanged();
                        }
                    });

                    // 아이템 클릭시 작동
                    lvAlarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), NotificationDetail.class);
                            startActivity(intent);
                        }
                    });

                } else {    // OFF일땐 알람 보이지 않음
                    ibtnAddAlarm.setVisibility(View.INVISIBLE);
                    lvAlarm.setVisibility(View.INVISIBLE);
                }
            }
        });

        // 뒤로가기 버튼
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
