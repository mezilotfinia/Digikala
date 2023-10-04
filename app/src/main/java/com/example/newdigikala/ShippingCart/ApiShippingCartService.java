package com.example.newdigikala.ShippingCart;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;
import com.example.newdigikala.Model.Basket;

import java.util.List;

import io.reactivex.Single;

public class ApiShippingCartService {
    ApiService apiService= ApiProvider.apiProvider();

    public Single<Message> getCartCount(String email){
       return apiService.getBasketCount(email);
    }

    public Single<List<Basket>> getBasketList(String email){
        return apiService.getBasketList(email);
    }

    public Single<Message> deleteBasketItem(String basketId){
        return apiService.deleteBasketItem(basketId);
    }

    public Single<List<Basket>> updateBasket(String orderId,String email){
        return apiService.updateBasket(orderId,email);
    }

}
