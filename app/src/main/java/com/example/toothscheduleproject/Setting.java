package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class Setting extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        ImageButton ibtnBack = (ImageButton)findViewById(R.id.ibtnBack);
        Button btnUserInfo = (Button)findViewById(R.id.btnUserInfo);
        Button btnLogout = (Button)findViewById(R.id.btnLogout);

        // 뒤로가기 버튼 눌렀을 때
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 개인정보 확인 버튼을 눌렀을 때
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfoCheck.class);
            }
        });

        // 로그아웃 버튼을 눌렀을 때
    }
}
