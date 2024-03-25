package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.fragment.FragmentAccount;
import com.example.myapplication.fragment.FragmentService;
import com.example.myapplication.fragment.FragmentHistory;
import com.example.myapplication.fragment.FragmentHome;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentHistory();
            case 2:
                return new FragmentService();
            case 3:
                return new FragmentAccount();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
