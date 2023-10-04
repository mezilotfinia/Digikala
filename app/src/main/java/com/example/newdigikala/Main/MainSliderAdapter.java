package com.example.newdigikala.Main;

import com.example.newdigikala.Model.Banner;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {
    List<Banner> banners;
    public MainSliderAdapter(List<Banner> banners) {
        this.banners=banners;
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        viewHolder.bindImageSlide(banners.get(position).getPic());
    }
}
