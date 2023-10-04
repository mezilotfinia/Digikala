package com.example.newdigikala.Login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.newdigikala.Main.MainDataSource;
import com.example.newdigikala.Model.Banner;
import com.example.newdigikala.Model.Cat;
import com.example.newdigikala.Model.MyTimer;
import com.example.newdigikala.Model.Product;

import java.util.List;

import io.reactivex.Single;

public class UserLoginInfo implements MainDataSource {

    SharedPreferences sharedPreferences;

    public UserLoginInfo(Context context) {
        sharedPreferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
    }
    public void saveLoginData(String email){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("email",email);
        editor.apply();
    }

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
        return sharedPreferences.getString("email","");
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
