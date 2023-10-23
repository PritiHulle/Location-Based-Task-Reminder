package com.example.taskmanagerpro.ui;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.taskmanagerpro.fragments.Fragment1;
import com.example.taskmanagerpro.fragments.Fragment2;
import com.example.taskmanagerpro.fragments.Fragment3;


public class YourPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public YourPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs  
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment1 homeFragment = new Fragment1();
                return homeFragment;
            case 1:
                Fragment2 sportFragment = new Fragment2();
                return sportFragment;
            case 2:
                Fragment3 movieFragment = new Fragment3();
                return movieFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs  
    @Override
    public int getCount() {
        return totalTabs;
    }
}  