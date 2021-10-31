package com.example.toothscheduleproject.InfoBoard;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter extends FragmentStateAdapter {

    public int mCount;

    public MyAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new Ex01Fragment();
        else if(index==1) return new Ex02Fragment();
        else if(index==2) return new Ex03Fragment();
        else if(index==3) return new Ex04Fragment();
        else if(index==4) return new Ex05Fragment();
        else return new Ex06Fragment();
    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position) { return position % mCount; }

}