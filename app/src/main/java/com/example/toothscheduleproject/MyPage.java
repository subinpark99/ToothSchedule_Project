package com.example.toothscheduleproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyPage extends Activity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        ImageButton ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);
        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        Button btnAppVer=(Button) findViewById(R.id.btnAppVer);
        TextView tvID = findViewById(R.id.tvID);
        TextView tvUserName = findViewById(R.id.tvUserName);
        ImageView ivUser = findViewById(R.id.ivUser);

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
                    int surveysum = userInfo.getSurveySum();

                    tvID.setText(userId);
                    tvUserName.setText(userName);

                    if(surveysum <= 12){
                        ivUser.setImageResource(R.drawable.bad);
                    }
                    else if(surveysum <= 19){
                        ivUser.setImageResource(R.drawable.soso);
                    }
                    else
                        ivUser.setImageResource(R.drawable.good);
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

        // 로그아웃
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MyPage.this, "로그아웃", Toast.LENGTH_SHORT).show();

                //로그아웃 시 로그인 페이지로 이동
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });


    }

    // 버전 정보 보여줌
    public void OnClickHandler(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("버전 정보").setMessage("v1.0");

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
}


