package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MyPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        ImageButton ibtnBack = (ImageButton)findViewById(R.id.ibtnBack);
        Button btnGraph = (Button)findViewById(R.id.btnGraph);


        // 뒤로가기 버튼 눌렀을 때
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 그래프와 기록 버튼을 눌렀을 때
        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Statistic.class);
                startActivity(intent);
            }
        });

    }


}
