package com.example.newdigikala.Main;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Banner;
import com.example.newdigikala.Model.Cat;
import com.example.newdigikala.Model.MyTimer;
import com.example.newdigikala.Model.Product;

import java.util.List;

import io.reactivex.Single;


public interface MainDataSource {
    Single<List<Product>> getProducts();
    Single<List<Banner>> getBanners();
    Single<MyTimer> getTimer();
    String getUserEmail();
    Single<List<Cat>> getCats();
    Single<Message> getCartCount(String email);
    Single<List<Product>> search(String search);
}
