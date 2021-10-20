package com.example.toothscheduleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

public class Notification extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        ImageButton ibtnBack = (ImageButton)findViewById(R.id.ibtnBack);
        Switch swAlarm = (Switch)findViewById(R.id.swAlarm);
        ImageButton ibtnAddAlarm = (ImageButton)findViewById(R.id.ibtnAddAlarm);

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    ibtnAddAlarm.setVisibility(View.VISIBLE);
                    ibtnAddAlarm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), NotificationDetail.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    ibtnAddAlarm.setVisibility(View.INVISIBLE);
                }
            }
        });


    }
}
