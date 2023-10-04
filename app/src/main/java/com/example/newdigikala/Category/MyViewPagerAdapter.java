package com.example.newdigikala.Category;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    List<String> titles;

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
        titles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        return CatFragment.newInstance(titles.get(i));
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    public void addFragment(String title){
        titles.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}