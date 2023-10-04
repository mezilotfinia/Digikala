package com.example.newdigikala.Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.newdigikala.Model.Cat;
import com.example.newdigikala.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class CatActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    List<Cat> cats;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        cats=getIntent().getParcelableArrayListExtra("cats");
        position=getIntent().getExtras().getInt("position");
        setupViews();
    }
    private void setupViews() {
        tabLayout=findViewById(R.id.tab_cat);
        viewPager=findViewById(R.id.viewPager_cat);
        tabLayout.setupWithViewPager(viewPager);
        MyViewPagerAdapter adapter=new MyViewPagerAdapter(getSupportFragmentManager());

        for (int i = cats.size()-1; i >=0; i--) {
            adapter.addFragment(cats.get(i).getTitle());
        }

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem((cats.size()-1)-position);
    }

}