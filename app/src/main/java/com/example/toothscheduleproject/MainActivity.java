package com.example.toothscheduleproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ToothSchedule");

        ImageButton ibtnMypage = findViewById(R.id.ibtnMypage);
        ImageButton ibtnNotification = findViewById(R.id.ibtnNotification);
        Button btnInfo = findViewById(R.id.btnInfo);

        // 달력
        mCalendarView = findViewById(R.id.calendarView);
        try {
            mCalendarView.setDate(Calendar.getInstance());
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        // 기존 서버 데이터 존재하면 달력에 이벤트 뿌려주기
        setCalendarEventFromServer();


        // 달력 날짜 클릭 리스너
        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar calendar = eventDay.getCalendar();
                int year = calendar.get(Calendar.YEAR);
                int month = (calendar.get(Calendar.MONTH)) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                Intent scheduleIntent = new Intent(MainActivity.this, Schedule.class);
                scheduleIntent.putExtra("year", year);
                scheduleIntent.putExtra("month", month);
                scheduleIntent.putExtra("day", day);
                startActivity(scheduleIntent);
            }
        });

        // 마이페이지 버튼 눌렀을 때
        ibtnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
            }
        });

        // 종버튼 눌렀을 때
        ibtnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Notification.class);
                startActivity(intent);
            }
        });

        // 정보게시판 버튼 눌렀을 때 정보게시판 페이지로 이동
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InfoBoard.class);
                startActivity(intent);
            }
        });
    }

    private void setCalendarEventFromServer() {
        List<EventDay> events = new ArrayList<>(); // 달력 이벤트 존재날짜 모아두는 배열

        ArrayList<ToothTimeInfo> lstToothTime = new ArrayList<>(); // 서버 데이터 받아내는 배열
        mDatabaseRef.child("UserInfo").child(mFirebaseAuth.getCurrentUser().getUid()).child("lstToothTime").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ToothTimeInfo toothTimeInfo = dataSnapshot.getValue(ToothTimeInfo.class);
                    lstToothTime.add(toothTimeInfo);
                }

                HashMap<Long, Integer> map = new HashMap<>();
                for (int i = 0; i < lstToothTime.size(); i++) {
                    String[] splitDate = lstToothTime.get(i).getDate().split("/");
                    int year = Integer.parseInt(splitDate[0]);
                    int month = Integer.parseInt(splitDate[1]) - 1;
                    int day = Integer.parseInt(splitDate[2]);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);

                    events.add(new EventDay(calendar, R.drawable.dot));
                    Long asd = events.get(i).getCalendar().getTimeInMillis();
                    Integer count = map.get(asd);
                    if (count == null)
                        map.put(asd, 1);
                    else
                        map.put(asd, count + 1);

                }

                // check count for same day data
                for (int i = 0; i < events.size(); i++) {
                    for (Map.Entry<Long, Integer> entry : map.entrySet()) {
                        if (events.get(i).getCalendar().getTimeInMillis() == entry.getKey()) {
                            if (entry.getValue() == 2) {
                                events.set(i, new EventDay(events.get(i).getCalendar(), R.drawable.dot_2));
                            } else if (entry.getValue() == 3) {
                                events.set(i, new EventDay(events.get(i).getCalendar(), R.drawable.dot_3));
                            } else {
                                events.set(i, new EventDay(events.get(i).getCalendar(), R.drawable.dot));
                            }
                        }
                    }
                }



                mCalendarView.setEvents(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}