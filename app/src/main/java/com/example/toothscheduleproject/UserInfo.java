package com.example.toothscheduleproject;

import java.util.ArrayList;

/**
 * 사용자 계정 정보 모델 클래스
 */
public class UserInfo {
    private String idToken;     // Firebase Uid (고유 토큰정보)
    private String emailId;     // 이메일 아이디
    private String password;    // 비밀번호
    private String userName;    // 이름
    private String gender;      // 성별
    private String birth;       // 생년월일

    private ArrayList<ToothTimeInfo> lstToothTime; // 양치 시간 작성정보

    public UserInfo() { }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public ArrayList<ToothTimeInfo> getLstToothTime() {
        return lstToothTime;
    }

    public void setLstToothTime(ArrayList<ToothTimeInfo> lstToothTime) {
        this.lstToothTime = lstToothTime;
    }
}
