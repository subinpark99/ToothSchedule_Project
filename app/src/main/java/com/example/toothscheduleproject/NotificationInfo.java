package com.example.toothscheduleproject;

import java.util.ArrayList;

public class NotificationInfo {
    private String time;        // 알림시간

    public NotificationInfo() {
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
}
