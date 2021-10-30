package com.example.toothscheduleproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SurveyResultBad extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_result_bad);

        Button btnstart = (Button) findViewById(R.id.btnstart);
        Button btnsearch = (Button) findViewById(R.id.btnsearch);
        TextView tvComment = (TextView)findViewById(R.id.tvComment);

        tvComment.setText(Html.fromHtml("※ 기본적인 3.3.3 양치 방법 ※"+ "<br/>" + "<br/>" + "1. 하루 3번 이상 양치하기" + "<br/>" + "<br/>" + "2. 3분 이상 꼼꼼하게 양치하기" + "<br/>"
                + "<br/>" + "3. 식후 3분안에 양치하기" +"<br/>" + "<br/>" + "4. 치약은 칫솔의 1/3만 묻히기" + "<br/>" + "<br/>" + "귀찮다고 안하실 건가요?" + "<br/>" + "<br/>"
                +  "빨리 관리 시작해봐요!"));

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.map.naver.com/search2/search.naver?query=%EA%B7%BC%EC%B2%98%EC%B9%98%EA%B3%BC&sm=sug&style=v5")); {
                    startActivity(intent);
                };
            }
        });

    }

}