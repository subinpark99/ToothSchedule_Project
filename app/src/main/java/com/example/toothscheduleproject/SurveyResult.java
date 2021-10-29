package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SurveyResult extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_result);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        TextView tvResult = (TextView)findViewById(R.id.tvResult);
        TextView tvComment = (TextView)findViewById(R.id.tvComment);

        Intent intent = getIntent();
        int number = intent.getIntExtra("sum",0);
        if(number <= 19){
            imageView.setImageResource(R.drawable.soso);
            tvResult.setText(Html.fromHtml("치아 상태가 괜찮네요. 조금 더 신경쓰면 멋쟁이!" + "<br/>" + "<br/>" +
                    "좋은 양치 방법은 어떤 것들이 있을까요??"));

            tvComment.setText(Html.fromHtml("※ 치아를 잘 관리하는 방법 ※"+ "<br/>" + "<br/>" + "1. 치약은 적당히" + "<br/>" + "<br/>" + "2. 칫솔에 물 묻히지 않기" + "<br/>"
            + "<br/>" + "3. 양치 시작은 어금니부터 시작" +"<br/>" + "<br/>" + "4. 하루 양치질은 최소 3번씩" + "<br/>" + "<br/>" + "또 어떤 관리 방법이 있을까요??" + "<br/>" + "<br/>"
                    +  "궁금하면 정보게시판으로!!"));
        }
        else if(number > 19){
            imageView.setImageResource(R.drawable.good);
            tvResult.setText(Html.fromHtml("치아 상태가 너무좋아요! 당신은 양치왕!" + "<br/>" +
                    "지금 이대로만 관리하면 아주 좋아요!" + "<br/>" + "<br/>" + "하지만 방심은 금물!"));
            //Html.formHtml => <br/> 사용시 개행 가능
            tvComment.setText(Html.fromHtml("※ 정말 잘하고 있나요? 기본기를 튼튼히 ※"+ "<br/>" + "<br/>" + "1. 입 물로 많이 행구기" + "<br/>" + "<br/>" + "2. 혀는 안쪽에서 바깥쪽" + "<br/>"
                    + "<br/>" + "3. 가글은 양치 30분 후 하루 1~2번" +"<br/>" + "<br/>" + "4. 칫솔은 통풍이 잘되고, 햇빛이 드는 곳으로" + "<br/>" + "<br/>" + "몰랐던 정보가 있나요??" + "<br/>" + "<br/>"
                    +  "좀 더 완벽해 지려면 시작해보세요!"));
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