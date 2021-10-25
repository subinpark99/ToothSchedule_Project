package com.example.toothscheduleproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NotificationInfo> lists = new ArrayList<NotificationInfo>();

    public NotificationAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notification_item,parent,false);
        }

        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        ImageButton ibtnDeleteAlarm = (ImageButton) convertView.findViewById(R.id.ibtnDeleteAlarm);

        NotificationInfo list = lists.get(position);

        // 가져온 데이터 텍스트뷰에 입력
        tvTime.setText(list.getTime());

        // 리스트 아이템 삭제
        ibtnDeleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lists.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
    public void addItem(String time) {
        NotificationInfo list = new NotificationInfo();

        list.setTime(time);
        lists.add(list);
    }
}
