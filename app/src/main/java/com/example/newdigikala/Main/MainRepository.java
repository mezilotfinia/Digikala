package com.example.newdigikala.Main;

import android.content.Context;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Login.UserLoginInfo;
import com.example.newdigikala.Model.Banner;
import com.example.newdigikala.Model.Cat;
import com.example.newdigikala.Model.MyTimer;
import com.example.newdigikala.Model.Product;

import java.util.List;

import io.reactivex.Single;


public class MainRepository implements MainDataSource{
    ApiMainDataSource apiMainDataSource=new ApiMainDataSource();
    LocalMainDataSource localMainDataSource=new LocalMainDataSource();
    UserLoginInfo userLoginInfo;
    private Context context;
    public MainRepository(Context context) {
        this.context=context;
        userLoginInfo=new UserLoginInfo(context);
    }


    @Override
    public Single<List<Product>> getProducts() {
        return apiMainDataSource.getProducts();
    }

    @Override
    public Single<List<Banner>> getBanners() {
        return apiMainDataSource.getBanners();
    }

    @Override
    public Single<MyTimer> getTimer() {
        return apiMainDataSource.getTimer();
    }

    @Override
    public String getUserEmail() {
        return userLoginInfo.getUserEmail();
    }

    @Override
    public Single<List<Cat>> getCats() {
        return apiMainDataSource.getCats();
    }

    @Override
    public Single<Message> getCartCount(String email) {
        return apiMainDataSource.getCartCount(email);
    }

    @Override
    public Single<List<Product>> search(String search) {
        return apiMainDataSource.search(search);
    }
}
