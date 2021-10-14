package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Schedule extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        ImageButton imbtnBack = (ImageButton) findViewById(R.id.imbtnBack);
        imbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //아침 스케줄 이동 버튼
        Button btnMorning  = (Button) findViewById(R.id.btnMorning);
        btnMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheduleDetail.class);
                startActivity(intent);
            }
        });

        //점심 스케줄 이동 버튼
        Button btnLunch  = (Button) findViewById(R.id.btnLunch);
        btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheduleDetail.class);
                startActivity(intent);
            }
        });

        //저녁 스케줄 이동 버튼
        Button btnDinner  = (Button) findViewById(R.id.btnDinner);
        btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheduleDetail.class);
                startActivity(intent);
            }
        });
    }

}
