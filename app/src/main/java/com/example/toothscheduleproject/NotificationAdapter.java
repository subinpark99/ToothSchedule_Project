package com.example.toothscheduleproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<NotificationInfo> lstAlarmTime = new ArrayList<NotificationInfo>(); // Adapter에 추가된 데이터 저장하기 위한 ArrayList

    public NotificationAdapter(Context context, int layout, ArrayList<NotificationInfo> lstAlarmTime) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        this.lstAlarmTime = lstAlarmTime;
        this.context = context;
    }

    @Override
    // Adapter에 사용되는 데이터의 개수를 리턴
    public int getCount() {
        return lstAlarmTime.size();
    }

    @Override
    public Object getItem(int position) {
        return lstAlarmTime.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    // posistion에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent,false);
        }
        NotificationInfo AlarmTime = lstAlarmTime.get(position);


        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        ImageButton ibtnDeleteAlarm = (ImageButton) convertView.findViewById(R.id.ibtnDeleteAlarm);

        // 가져온 데이터 텍스트뷰와 연동
        //tvTime.setText(AlarmTime.getTime());

        // 리스트 아이템 삭제
        ibtnDeleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstAlarmTime.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public void addItem(String time) {
        NotificationInfo AlarmTime = new NotificationInfo();

        AlarmTime.setTime(time);
        lstAlarmTime.add(AlarmTime);
    }
}
