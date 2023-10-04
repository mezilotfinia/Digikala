package com.example.newdigikala.ShippingCart;

import android.content.Context;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Login.UserLoginInfo;
import com.example.newdigikala.Model.Basket;

import java.util.List;

import io.reactivex.Single;

public class ShippingViewModel {

    public UserLoginInfo userLoginInfo;

    public ShippingViewModel(Context context){
        userLoginInfo=new UserLoginInfo(context);
    }


    ShippingRepository repository=new ShippingRepository();

    public Single<Message> getCartCount(String email){
        return repository.getCartCount(email);
    }

    public Single<List<Basket>> getBasketList(String email){
        return repository.getBasketList(email);
    }

    public String getUserEmail(){
        return userLoginInfo.getUserEmail();
    }

    public Single<Message> deleteBasketItem(String basketId){
        return repository.deleteBasketItem(basketId);
    }

    public Single<List<Basket>> updateBasket(String orderId,String email){
        return repository.updateBasket(orderId,email);
    }

}
