package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPage extends Activity {

    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        ImageButton ibtnBack = (ImageButton)findViewById(R.id.ibtnBack);
        Button btnGraph = (Button)findViewById(R.id.btnGraph);
        TextView tvUserName = (TextView)findViewById(R.id.tvUserName);
        TextView tvID = (TextView)findViewById(R.id.tvID);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ToothSchedule");

        // 로그인 되어있는 사용자 정보를 보여준다.
        mDatabaseRef.child("UserInfo").orderByChild("idToken").equalTo(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo = null;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    userInfo = dataSnapshot.getValue(UserInfo.class);
                }
                if ( userInfo != null ) {
                    String userId = userInfo.getEmailId();
                    String userName = userInfo.getUserName();

                    tvID.setText(userId);
                    tvUserName.setText(userName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
