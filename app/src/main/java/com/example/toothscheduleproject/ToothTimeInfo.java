package com.example.toothscheduleproject;

public class ToothTimeInfo {

    private String date;        // 달력 기록 날짜

    private String timeType;    // 아침 or 점심 or 저녁

    private int
            mMealTimeType,      // 양치 시간
            mAfterMealTimeType, // 식후 양치 시간
            mSubMachineType;    // 구강 보조기구 사용

    public ToothTimeInfo() { }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public int getmMealTimeType() {
        return mMealTimeType;
    }

    public void setmMealTimeType(int mMealTimeType) {
        this.mMealTimeType = mMealTimeType;
    }

    public int getmAfterMealTimeType() {
        return mAfterMealTimeType;
    }

    public void setmAfterMealTimeType(int mAfterMealTimeType) {
        this.mAfterMealTimeType = mAfterMealTimeType;
    }

    public int getmSubMachineType() {
        return mSubMachineType;
    }

    public void setmSubMachineType(int mSubMachineType) {
        this.mSubMachineType = mSubMachineType;
    }
}
