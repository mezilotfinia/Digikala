package com.example.newdigikala.Detail;

import android.content.Context;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Login.UserLoginInfo;
import com.example.newdigikala.Model.Detail;

import java.util.List;

import io.reactivex.Single;

public class DetailViewModel {
    Context context;
    UserLoginInfo userLoginInfo;
    public DetailViewModel(Context context){
        this.context=context;
        userLoginInfo=new UserLoginInfo(context);
    }
    private DetailRepository detailRepository=new DetailRepository();

    public Single<List<Detail>> getDetails(String id,String user){
        return detailRepository.getDetails(id,user);
    }

    public String getUserEmail(){
        return userLoginInfo.getUserEmail();
    }

    public Single<Message> addFavorite(String email, String id, int parent, String title){
        return detailRepository.addFavorite(email,id,parent,title);
    }

    public Single<Message> addToBasket(String productId,String email){
        return detailRepository.addToBasket(productId,email);
    }

    public Single<Message> getBasketCount(String email){
        return detailRepository.getBasketCount(email);
    }
}
