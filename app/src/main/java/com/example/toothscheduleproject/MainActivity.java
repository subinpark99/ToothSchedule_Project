package com.example.toothscheduleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton ibtnMypage = (ImageButton) findViewById(R.id.ibtnMypage);
        ImageButton ibtnNotification = (ImageButton)findViewById(R.id.ibtnNotification);
        Button btnInfo = (Button)findViewById(R.id.btnInfo);

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
}