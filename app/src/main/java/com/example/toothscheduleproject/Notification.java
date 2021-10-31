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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Text;

import java.util.Calendar;


public class Notification extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);


        ImageButton ibtnBack = findViewById(R.id.ibtnBack);
        TimePicker timePicker=(TimePicker)findViewById(R.id.time_picker);
        Button btnSave=(Button)findViewById(R.id.btnSave);
        TextView tvSaveTime= (TextView)findViewById(R.id.tvSaveTime);

        btnSave.setOnClickListener(x->{

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

            tvSaveTime.setText("설정된 알람 : "+hour+"시 "+minute+"분");
                }
        );

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

