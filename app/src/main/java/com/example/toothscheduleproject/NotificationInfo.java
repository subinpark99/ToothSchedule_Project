package com.example.toothscheduleproject;

import java.util.ArrayList;

public class NotificationInfo {
    private String title;       // 알림이름
    private String time;        // 알림시간
    private int
            hour,       // 시
            minute;     // 분

    public NotificationInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NotificationInfo(String time) {
        this.time = time;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
