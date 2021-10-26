package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SurveyResult extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_result);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        TextView tvResult = (TextView)findViewById(R.id.tvResult);

        Intent intent = getIntent();
        int number = intent.getIntExtra("sum",0);
        if(number <= 19){
            imageView.setImageResource(R.drawable.soso);
            tvResult.setText(Html.fromHtml("치아 상태가 괜찮네요. 조금 더 신경쓰면 멋쟁이!" + "<br/>" +
                    "좋은 양치 방법은 정보게시판으로 가보세요!"));

        }
        else if(number > 19){
            imageView.setImageResource(R.drawable.good);
            tvResult.setText(Html.fromHtml("치아 상태가 너무좋아요! 당신은 양치왕!" + "<br/>" +
                    "지금 이대로만 관리하면 아주 좋아요!"));
            //Html.formHtml => <br/> 사용시 개행 가능
        }



        Button btnstart = (Button) findViewById(R.id.btnstart);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

}