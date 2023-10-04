package com.example.newdigikala.ShippingCart;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Basket;

import java.util.List;

import io.reactivex.Single;

public class ShippingRepository {
    ApiShippingCartService apiShippingCartService=new ApiShippingCartService();

    public Single<Message> getCartCount(String email){
        return apiShippingCartService.getCartCount(email);
    }

    public Single<List<Basket>> getBasketList(String email){
        return apiShippingCartService.getBasketList(email);
    }

    public Single<Message> deleteBasketItem(String basketId){
        return apiShippingCartService.deleteBasketItem(basketId);
    }

    public Single<List<Basket>> updateBasket(String orderId,String email){
        return apiShippingCartService.updateBasket(orderId,email);
    }

}
