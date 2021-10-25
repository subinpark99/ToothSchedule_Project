package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;

public class Notification extends Activity {

    private NotificationInfo list;
    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        ImageButton ibtnBack = (ImageButton)findViewById(R.id.ibtnBack);
        Switch swAlarm = (Switch)findViewById(R.id.swAlarm);
        ImageButton ibtnAddAlarm = (ImageButton)findViewById(R.id.ibtnAddAlarm);
        ListView lvAlarm = findViewById(R.id.lvAlarm);

        adapter = new NotificationAdapter(Notification.this);
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
