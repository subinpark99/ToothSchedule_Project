package com.example.toothscheduleproject;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class Schedule extends Activity implements View.OnClickListener {


    private int mYear = 0, mMonth = 0 , mDay = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);









        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mYear = intent.getIntExtra("year", 0);
            mMonth = intent.getIntExtra("month", 0);
            mDay = intent.getIntExtra("day", 0);
            // set current date textview
            ((TextView)findViewById(R.id.tvDate)).setText(mMonth + "월" + " " + mDay + "일");
        }

        findViewById(R.id.btnMorning).setOnClickListener(this);
        findViewById(R.id.btnLunch).setOnClickListener(this);
        findViewById(R.id.btnDinner).setOnClickListener(this);
        findViewById(R.id.ibtnBack).setOnClickListener(this);
        findViewById(R.id.btnAdd).setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        boolean isScheduleDetail = false;
        String strTimeType = "";
        switch (view.getId()) {

            case R.id.ibtnBack:
                finish(); // 현재 액티비티 종료
                break;

            case R.id.btnMorning:
                isScheduleDetail = true;
                strTimeType = "아침";
                break;

            case R.id.btnLunch:
                isScheduleDetail = true;
                strTimeType = "점심";
                break;

            case R.id.btnDinner:
                isScheduleDetail = true;
                strTimeType = "저녁";
                break;

            case R.id.btnAdd:
                isScheduleDetail = true;
                strTimeType = "추가";
                break;
        }


        // 스케쥴 상세 이동
        if (isScheduleDetail) {
            Intent intent = new Intent(Schedule.this , ScheduleDetail.class);
            intent.putExtra("timeType", strTimeType);
            intent.putExtra("year", mYear);
            intent.putExtra("month", mMonth);
            intent.putExtra("day", mDay);
            startActivity(intent);
        }

    }




}
