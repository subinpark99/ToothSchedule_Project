
package com.example.toothscheduleproject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Notification extends Activity implements View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    String alarmTitle;
    int hour=9, minutes=0;


    private TimePicker timePicker;

    @RequiresApi(api = Build.VERSION_CODES.M)




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ToothSchedule");

        ImageButton ibtnBack = findViewById(R.id.ibtnBack);
        Button btnMorning = findViewById(R.id.btnMorning);
        Button btnLunch = findViewById(R.id.btnLunch);
        Button btnEvening = findViewById(R.id.btnEvening);

        Switch swAlarm = (Switch)findViewById(R.id.swAlarm);

        ibtnBack.setOnClickListener(this);
        btnMorning.setOnClickListener(this);
        btnLunch.setOnClickListener(this);
        btnEvening.setOnClickListener(this);

        swAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // ON일때 알람보여줌
                if(isChecked) {
                    btnMorning.setVisibility(View.VISIBLE);
                    btnLunch.setVisibility(View.VISIBLE);
                    btnEvening.setVisibility(View.VISIBLE);
                } else {    // OFF일땐 알람 보이지 않음
                    btnMorning.setVisibility(View.INVISIBLE);
                    btnLunch.setVisibility(View.INVISIBLE);
                    btnEvening.setVisibility(View.INVISIBLE);
                }
            }
        });



    }

    //버튼 별 처리
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ibtnBack:     // 뒤로가기 버튼
                finish();
                break;
            case R.id.btnMorning:
                alarmTitle="first";
                timePick(alarmTitle);
                break;


            case R.id.btnLunch:
                alarmTitle="second";
                timePick(alarmTitle);
                break;
            case R.id.btnEvening:
                alarmTitle="third";
                timePick(alarmTitle);
                break;

        }
    }

    // 시간 설정
    public void timePick(String title) {
        TimePickerDialog timePicker = new TimePickerDialog
                (Notification.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);

                        // 설정 시간 DB에 저장
                        mDatabaseRef.child("UserInfo").orderByChild("idToken").equalTo(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserInfo userInfo = null;
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    userInfo = dataSnapshot.getValue(UserInfo.class);
                                }
                                if (userInfo != null) {
                                    ArrayList<NotificationInfo> lstAlarmTimeInfo = userInfo.getLstAlarmTime();
                                    if (lstAlarmTimeInfo == null)
                                        lstAlarmTimeInfo = new ArrayList<>();

                                    int findIdx = 0;
                                    boolean isAlreadyExist = false; // 기존 데이터 존재하는지 체크 불리언
                                    for (int i = 0; i < lstAlarmTimeInfo.size(); i++) {
                                        // 기존 서버 db에서 기록 날짜와, 시간 타입까지 같이 일치하는 데이터가 있는지 검사한다.
                                        if (lstAlarmTimeInfo.get(i).getTitle().equals(title)) {
                                            isAlreadyExist = true;
                                            findIdx = i;
                                            break;
                                        }
                                    }

                                    NotificationInfo alarmTimeInfo = new NotificationInfo();
                                    alarmTimeInfo.setTitle(title);
                                    alarmTimeInfo.setHour(hourOfDay);
                                    alarmTimeInfo.setMinute(minute);

                                    if (isAlreadyExist) {
                                        // 데이터가 이미 존재하면 업데이트
                                        lstAlarmTimeInfo.set(findIdx, alarmTimeInfo);
                                        Toast.makeText(Notification.this, "알림 시간이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // 없다면 새로 알람을 맞춰준다.
                                        lstAlarmTimeInfo.add(alarmTimeInfo);
                                    }
                                    userInfo.setLstAlarmTime(lstAlarmTimeInfo);
                                }
                                // 서버 DB에 정보 Update
                                mDatabaseRef.child("UserInfo").child(mFirebaseAuth.getCurrentUser().getUid()).setValue(userInfo);


                                Toast.makeText(Notification.this, "알림이 설정되었습니다.", Toast.LENGTH_SHORT).show();
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }, hour, minutes, false);
        timePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePicker.show();

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);


            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);

        }

    }
}

