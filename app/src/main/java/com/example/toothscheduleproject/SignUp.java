package com.example.toothscheduleproject;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스



    private EditText
            mEtEmail,       // 이메일
            mEtPwd,         // 비밀번호
            mEtPwdCheck,    // 비밀번호 확인
            mEtName;        // 이름

    private String
            strYear,        // 생년
            strMonth,       // 월
            strDay,         // 일
            strGender;      // 성별

    private boolean isCheckSafeAccount = false; // 중복확인 결과 값


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        findViewById(R.id.ibtnback).setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ToothSchedule");

        Spinner spinner = findViewById(R.id.spYear);
        Spinner spinner2 = findViewById(R.id.spDay);
        Spinner spinner3 = findViewById(R.id.spMonth);

        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);

        mEtEmail = findViewById(R.id.edtID);
        mEtPwd = findViewById(R.id.edtPW);
        mEtPwdCheck = findViewById(R.id.edtPwCheck);
        mEtName = findViewById(R.id.edtname);


        RadioGroup rg_gender = findViewById(R.id.rg_gender);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                strGender = (i == 1 ? "남" : "여");
            }
        });

        findViewById(R.id.btnNext).setOnClickListener(this);
        findViewById(R.id.btnDoubleCheck).setOnClickListener(this);

        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(monthAdapter);

        ArrayAdapter monthAdapter2 = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_dropdown_item);
        monthAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(monthAdapter2);

        ArrayAdapter monthAdapter3 = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_dropdown_item);
        monthAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(monthAdapter3);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnback:
                // 뒤로가기 버튼
                finish();
                break;

            case R.id.btnNext:
                // 가입 완료 버튼

                // 빈 입력 값 체크
                if(     mEtEmail.getText().length() == 0 ||
                        mEtPwd.getText().length() == 0 ||
                        mEtPwdCheck.getText().length() == 0 ||
                        mEtName.getText().length() == 0 ||
                        strYear == null ||
                        strMonth == null ||
                        strDay == null ||
                        strGender == null) {

                    Toast.makeText(SignUp.this, "비어있는 입력값이 존재합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 입력 vs 입력확인 다름
                if(!mEtPwd.getText().toString().equals(mEtPwdCheck.getText().toString())) {
                    Toast.makeText(SignUp.this, "비밀번호 입력 확인과 다릅니다.", Toast.LENGTH_SHORT).show();
                    return;
                }


                // 중복 확인 진행 여부
                if (!isCheckSafeAccount) {
                    Toast.makeText(SignUp.this, "아직 중복확인을 진행하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 회원가입 수행
                setSignUpProcess();

                break;

            case R.id.btnDoubleCheck:
                // 중복확인
                setCheckDuplicatedAccount();
                break;
        }
    }

    private void setCheckDuplicatedAccount() {
        // 아이디 중복검사
        mDatabaseRef.child("UserInfo").orderByChild("emailId").equalTo(mEtEmail.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() != 0) {
                    // 중복 account 존재
                    isCheckSafeAccount = false;
                    Toast.makeText(SignUp.this, "이미 가입된 아이디 입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    isCheckSafeAccount = true;
                    Toast.makeText(SignUp.this, "사용 가능한 계정입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FIREBASE_DB_ERROR", error.getMessage());
            }
        });
    }

    private void setSignUpProcess() {
        // 회원가입 진행
        // 회원가입 처리 시작
        String strEmail = mEtEmail.getText().toString().trim();
        String strPwd = mEtPwd.getText().toString().trim();

        // Firebase Auth 진행
        mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                    UserInfo account = new UserInfo();
                    account.setIdToken(firebaseUser.getUid());
                    account.setEmailId(firebaseUser.getEmail());
                    account.setPassword(strPwd);
                    account.setUserName(mEtName.getText().toString());
                    account.setGender(strGender);
                    account.setBirth(strYear + "/" + strMonth + "/" + strDay);


                    // setValue : database에 insert (삽입) 행위
                    mDatabaseRef.child("UserInfo").child(firebaseUser.getUid()).setValue(account);



                    Toast.makeText(SignUp.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                    // 회원가입 성공 후 설문조사 페이지로 넘어감
                    Intent intent = new Intent(getApplicationContext(), Survey.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUp.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.spYear:
                // 생년
                String[] yearArry = getResources().getStringArray(R.array.year);
                strYear = yearArry[position];
                break;

            case R.id.spMonth:
                // 월
                String[] monthArry = getResources().getStringArray(R.array.month);
                strMonth = monthArry[position];
                break;

            case R.id.spDay:
                // 일
                String[] dayArry = getResources().getStringArray(R.array.day);
                strDay = dayArry[position];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}



