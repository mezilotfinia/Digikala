package com.example.newdigikala.Detail;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Detail;

import java.util.List;

import io.reactivex.Single;

public class DetailRepository {
    private DetailApiService detailApiService=new DetailApiService();
    public Single<List<Detail>> getDetails(String id,String user){
        return detailApiService.getDetails(id,user);
    }
    public Single<Message> addFavorite(String email, String id, int parent, String title){
        return detailApiService.addFavorite(email,id,parent,title);
    }
    public Single<Message> addToBasket(String productId,String email){
        return detailApiService.addToBasket(productId,email);
    }
    public Single<Message> getBasketCount(String email){
        return detailApiService.getBasketCount(email);
    }
}
