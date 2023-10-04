package com.example.newdigikala.Main;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Banner;
import com.example.newdigikala.Model.Cat;
import com.example.newdigikala.Model.MyTimer;
import com.example.newdigikala.Model.Product;

import java.util.List;

import io.reactivex.Single;


public class LocalMainDataSource implements MainDataSource{
    @Override
    public Single<List<Product>> getProducts() {
        return null;
    }

    @Override
    public Single<List<Banner>> getBanners() {
        return null;
    }

    @Override
    public Single<MyTimer> getTimer() {
        return null;
    }

    @Override
    public String getUserEmail() {
        return null;
    }

    @Override
    public Single<List<Cat>> getCats() {
        return null;
    }

    @Override
    public Single<Message> getCartCount(String email) {
        return null;
    }

    @Override
    public Single<List<Product>> search(String search) {
        return null;
    }
}
