package com.example.newdigikala.Detail;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;
import com.example.newdigikala.Model.Detail;

import java.util.List;

import io.reactivex.Single;

public class DetailApiService {
    private ApiService apiService= ApiProvider.apiProvider();
    public Single<List<Detail>> getDetails(String id,String user){
        return apiService.getDetail(id,user);
    }
    public Single<Message> addFavorite(String email,String id,int parent,String title){
        return apiService.addFavorite(email,id,parent,title);
    }
    public Single<Message> addToBasket(String productId,String email){
        return apiService.addToBasket(productId,email);
    }
    public Single<Message> getBasketCount(String email){
        return apiService.getBasketCount(email);
    }
}
