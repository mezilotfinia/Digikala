package com.example.newdigikala.Main;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;
import com.example.newdigikala.Model.Banner;
import com.example.newdigikala.Model.Cat;
import com.example.newdigikala.Model.MyTimer;
import com.example.newdigikala.Model.Product;

import java.util.List;

import io.reactivex.Single;


public class ApiMainDataSource implements MainDataSource{
    public ApiService apiService=ApiProvider.apiProvider();
    @Override
    public Single<List<Product>> getProducts() {
        return apiService.getProduct();
    }

    @Override
    public Single<List<Banner>> getBanners() {
        return apiService.getBanners();
    }

    @Override
    public Single<MyTimer> getTimer() {
        return apiService.getTimer();
    }

    @Override
    public String getUserEmail() {
        return null;
    }

    @Override
    public Single<List<Cat>> getCats() {
        return apiService.getCats();
    }

    @Override
    public Single<Message> getCartCount(String email) {
        return apiService.getBasketCount(email);
    }

    @Override
    public Single<List<Product>> search(String search) {
        return apiService.getSearchedProduct(search);
    }
}
