package com.example.toothscheduleproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class Statistic extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic);

        ImageButton ibtnBack = (ImageButton)findViewById(R.id.ibtnBack);

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
