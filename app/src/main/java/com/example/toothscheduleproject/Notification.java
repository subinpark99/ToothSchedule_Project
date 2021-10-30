package com.example.toothscheduleproject;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Calendar;


public class Notification extends Activity implements View.OnClickListener {


    private Button save;
    private TimePicker timePicker;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);


        ImageButton ibtnBack = findViewById(R.id.ibtnBack);
        Button btnMorning = findViewById(R.id.btnMorning);
        Button btnLunch = findViewById(R.id.btnLunch);
        Button btnEvening = findViewById(R.id.btnEvening);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch swAlarm = findViewById(R.id.swAlarm);

        ibtnBack.setOnClickListener(this);
        btnMorning.setOnClickListener(this);
        btnLunch.setOnClickListener(this);
        btnEvening.setOnClickListener(this);

        swAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // ON일때 알람보여줌
            if (isChecked) {
                btnMorning.setVisibility(View.VISIBLE);
                btnLunch.setVisibility(View.VISIBLE);
                btnEvening.setVisibility(View.VISIBLE);
            } else {    // OFF일땐 알람 보이지 않음
                btnMorning.setVisibility(View.INVISIBLE);
                btnLunch.setVisibility(View.INVISIBLE);
                btnEvening.setVisibility(View.INVISIBLE);
            }
        });


    }

    //버튼 별 처리
    @SuppressLint({"ResourceType", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ibtnBack:     // 뒤로가기 버튼
                finish();
                break;


            case R.id.btnMorning:
            case R.id.btnLunch:
            case R.id.btnEvening:

               setContentView(R.layout.nonfic_alarm);
                timePicker=(TimePicker)findViewById(R.id.time_picker);
                save=(Button)findViewById(R.id.save);

                save.setOnClickListener(x->{

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    int hour=timePicker.getHour();
                    int minute=timePicker.getMinute();
                    calendar.set(Calendar.HOUR_OF_DAY,hour);
                    calendar.set(Calendar.MINUTE,minute);

                    if (calendar.before(Calendar.getInstance())) {
                        calendar.add(Calendar.DATE, 1);
                    }

                    AlarmManager alarmManager=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
                    if (alarmManager != null) {
                        Intent intent = new Intent(this, AlarmReceiver.class);
                        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                AlarmManager.INTERVAL_DAY, alarmIntent);

                        Toast.makeText(Notification.this,"알람이 저장되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });


                break;

        }


    }

}