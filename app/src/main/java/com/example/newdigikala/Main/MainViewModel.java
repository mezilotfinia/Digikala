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


public class MainViewModel {
    private Context context;
    MainRepository mainRepository;
    UserLoginInfo userLoginInfo;

    public MainViewModel(Context context){
        this.context=context;
        userLoginInfo=new UserLoginInfo(context);
        mainRepository=new MainRepository(context);
    }

    public Single<List<Product>> getProducts(){
       return mainRepository.getProducts();
    }
    public Single<List<Banner>> getBanners(){
        return mainRepository.getBanners();
    }
    public Single<MyTimer> getTimer(){
        return mainRepository.getTimer();
    }
    public void saveEmailData(String email){
        userLoginInfo.saveLoginData(email);
    }
    public String getUserEmail(){
        return mainRepository.getUserEmail();
    }
    public Single<List<Cat>> getCats(){
        return mainRepository.getCats();
    }

    public Single<Message> getCartCount(String email) {
        return mainRepository.getCartCount(email);
    }

    public Single<List<Product>> search(String search){
        return mainRepository.search(search);
    }

}
