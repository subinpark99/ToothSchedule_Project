package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Survey extends Activity {

    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스

    //전역변수로 라디오버튼 결과값을 받을 함수 선언언
    private int result1, result2, result3, result4, result5;
    private int sum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ToothSchedule");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ToothSchedule");

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        RadioGroup rg1 = (RadioGroup) findViewById(R.id.radiogroup1);
        RadioGroup rg2 = (RadioGroup) findViewById(R.id.radiogroup2);
        RadioGroup rg3 = (RadioGroup) findViewById(R.id.radiogroup3);
        RadioGroup rg4 = (RadioGroup) findViewById(R.id.radiogroup4);
        RadioGroup rg5 = (RadioGroup) findViewById(R.id.radiogroup5);


        //라디오버튼당 점수 할당 ex 1번선택 == 1점
        //질분이 5개 => 라디오 그룹 = 5개
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.rbtn1_1) {
                    result1 = 1;
                } else if (i == R.id.rbtn1_2) {
                    result1 = 2;
                } else if (i == R.id.rbtn1_3) {
                    result1 = 3;
                } else if (i == R.id.rbtn1_4) {
                    result1 = 4;
                } else if (i == R.id.rbtn1_5) {
                    result1 = 5;
                }
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.rbtn2_1) {
                    result2 = 1;
                } else if (i == R.id.rbtn2_2) {
                    result2 = 2;
                } else if (i == R.id.rbtn2_3) {
                    result2 = 3;
                }
            }
        });

        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.rbtn3_1) {
                    result3 = 1;
                } else if (i == R.id.rbtn3_2) {
                    result3 = 2;
                } else if (i == R.id.rbtn3_3) {
                    result3 = 3;
                } else if (i == R.id.rbtn3_4) {
                    result3 = 4;
                } else if (i == R.id.rbtn3_5) {
                    result3 = 5;
                }
            }
        });

        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.rbtn4_1) {
                    result4 = 1;
                } else if (i == R.id.rbtn4_2) {
                    result4 = 2;
                } else if (i == R.id.rbtn4_3) {
                    result4 = 3;
                } else if (i == R.id.rbtn4_4) {
                    result4 = 4;
                } else if (i == R.id.rbtn4_5) {
                    result4 = 5;
                }
            }
        });

        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.rbtn5_1) {
                    result5 = 1;
                } else if (i == R.id.rbtn5_2) {
                    result5 = 2;
                } else if (i == R.id.rbtn5_3) {
                    result5 = 3;
                } else if (i == R.id.rbtn5_4) {
                    result5 = 4;
                } else if (i == R.id.rbtn5_5) {
                    result5 = 5;
                }
            }
        });




        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sum = result1 + result2 + result3 + result4 + result5;

               result1= rg1.indexOfChild(findViewById(rg1.getCheckedRadioButtonId()));
                result2= rg2.indexOfChild(findViewById(rg2.getCheckedRadioButtonId()));
                result3= rg3.indexOfChild(findViewById(rg3.getCheckedRadioButtonId()));
                result4= rg4.indexOfChild(findViewById(rg4.getCheckedRadioButtonId()));
                result5= rg5.indexOfChild(findViewById(rg5.getCheckedRadioButtonId()));
                if(result1 == -1 || result2 == -1 || result3 == -1|| result5 == -1|| result5 == -1) {
                    // 선택된 날씨가 없음
                    Toast toast = Toast.makeText(getApplicationContext(), "선택 옵션을 모두 선택해주세요!", Toast.LENGTH_LONG);
                   toast.show();
                    return;
                }








                // 점수결과 DB에 저장
                mDatabaseRef.child("UserInfo").orderByChild("idToken").equalTo(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInfo userInfo = null;

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            userInfo = dataSnapshot.getValue(UserInfo.class);
                        }
                        if ( userInfo != null ) {
                            userInfo.setSurveySum(sum);
                        }
                        mDatabaseRef.child("UserInfo").child(mFirebaseAuth.getCurrentUser().getUid()).setValue(userInfo);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                //점수 총합이 12점 이하이면 치과추천으로 넘어감


                if (sum <= 12) {
                    Intent intent = new Intent(getApplicationContext(), SurveyResultBad.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), SurveyResult.class);
                    intent.putExtra("sum",sum);
                    startActivity(intent);
                }

            }
        });
    }
}
