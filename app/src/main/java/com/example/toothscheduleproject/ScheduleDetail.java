package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScheduleDetail extends Activity implements View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    private RadioGroup
            mRgMealTime,
            mRgAfterMeal,
            mRgSubMachine;

    private String mTimeType;       // 아침 or 점심 or 저녁

    private int mYear = 0, mMonth = 0 , mDay = 0;

    private int
            mMealTimeType = -1,      // 양치 시간
            mAfterMealTimeType = -1, // 식후 양치 시간
            mSubMachineType = -1;    // 구강 보조기구 사용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_detail);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ToothSchedule");

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mTimeType = intent.getStringExtra("timeType");
            ((TextView)findViewById(R.id.tvTime)).setText(mTimeType);
            mYear = intent.getIntExtra("year", 0);
            mMonth = intent.getIntExtra("month", 0);
            mDay = intent.getIntExtra("day", 0);
            ((TextView)findViewById(R.id.tvCurentDate)).setText(mMonth + "월" + " " + mDay + "일");
        }


        mRgMealTime = findViewById(R.id.rg_meal_time);
        mRgAfterMeal = findViewById(R.id.rg_after_meal_time);
        mRgSubMachine = findViewById(R.id.rg_sub_machine);

        findViewById(R.id.ibtnback).setOnClickListener(this);
        findViewById(R.id.ibtncheck).setOnClickListener(this);

        getRecentWriteData();

    }

    private void getRecentWriteData() {
        // 이전에 서버에 저장한 이력이 있는지 확인해서 있으면 가지고와서 화면에 표시해준다..
        mDatabaseRef.child("UserInfo").orderByChild("idToken").equalTo(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo = null;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    userInfo = dataSnapshot.getValue(UserInfo.class);
                }
                if ( userInfo != null ) {
                    ArrayList<ToothTimeInfo> lstToothTimeInfo = userInfo.getLstToothTime();
                    if (lstToothTimeInfo != null) {
                        for (int i = 0; i < lstToothTimeInfo.size(); i++) {
                            if ( lstToothTimeInfo.get(i).getTimeType().equals(mTimeType) && lstToothTimeInfo.get(i).getDate().equals(mYear + "/" + mMonth + "/" + mDay)) {
                                mMealTimeType = lstToothTimeInfo.get(i).getmMealTimeType();
                                mAfterMealTimeType = lstToothTimeInfo.get(i).getmAfterMealTimeType();
                                mSubMachineType = lstToothTimeInfo.get(i).getmSubMachineType();

                                ((RadioButton) mRgMealTime.getChildAt(mMealTimeType)).setChecked(true);
                                ((RadioButton) mRgAfterMeal.getChildAt(mAfterMealTimeType)).setChecked(true);
                                ((RadioButton) mRgSubMachine.getChildAt(mSubMachineType)).setChecked(true);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnback:
                finish(); // 액티비티 종료
                break;

            case R.id.ibtncheck:
                // 작성완료 버튼

                mMealTimeType = mRgMealTime.indexOfChild(findViewById(mRgMealTime.getCheckedRadioButtonId()));
                mAfterMealTimeType = mRgAfterMeal.indexOfChild(findViewById(mRgAfterMeal.getCheckedRadioButtonId()));
                mSubMachineType = mRgSubMachine.indexOfChild(findViewById(mRgSubMachine.getCheckedRadioButtonId()));

                // 라디오 버튼들 선택되어있는지 검사
                if(mMealTimeType == -1 || mAfterMealTimeType == -1 || mSubMachineType == -1) {
                    // 선택된 날씨가 없음
                    Toast.makeText(this, "선택 옵션을 모두 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mDatabaseRef.child("UserInfo").orderByChild("idToken").equalTo(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInfo userInfo = null;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            userInfo = dataSnapshot.getValue(UserInfo.class);
                        }
                        if ( userInfo != null) {
                            ArrayList<ToothTimeInfo> lstToothTimeInfo = userInfo.getLstToothTime();
                            if (lstToothTimeInfo == null)
                                lstToothTimeInfo = new ArrayList<>();

                            int findIdx = 0;
                            boolean isAlreadyExist = false; // 기존 데이터 존재하는지 체크 불리언
                            String date = mYear + "/" + mMonth + "/" + mDay;
                            for (int i = 0; i < lstToothTimeInfo.size(); i++) {
                                // 기존 서버 db에서 기록 날짜와, 시간 타입까지 같이 일치하는 데이터가 있는지 검사한다.
                                if (lstToothTimeInfo.get(i).getDate().equals(date) && lstToothTimeInfo.get(i).getTimeType().equals(mTimeType)) {
                                    isAlreadyExist = true;
                                    findIdx = i;
                                    break;
                                }
                            }

                            ToothTimeInfo toothTimeInfo = new ToothTimeInfo();
                            toothTimeInfo.setTimeType(mTimeType);
                            toothTimeInfo.setDate(date);
                            toothTimeInfo.setmMealTimeType(mMealTimeType);
                            toothTimeInfo.setmAfterMealTimeType(mAfterMealTimeType);
                            toothTimeInfo.setmSubMachineType(mSubMachineType);
                            if (isAlreadyExist) {
                                // update before exist data
                                lstToothTimeInfo.set(findIdx, toothTimeInfo);
                            }
                            else {
                                // new data
                                lstToothTimeInfo.add(toothTimeInfo);
                            }
                            userInfo.setLstToothTime(lstToothTimeInfo);


                            // 서버 DB에 정보 Update
                            mDatabaseRef.child("UserInfo").child(mFirebaseAuth.getCurrentUser().getUid()).setValue(userInfo);

                            Toast.makeText(ScheduleDetail.this, "작성 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                break;
        }
    }
}
