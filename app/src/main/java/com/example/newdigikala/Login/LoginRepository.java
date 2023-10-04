package com.example.newdigikala.Login;

import io.reactivex.Single;

public class LoginRepository implements LoginDataSource{

    ApiLoginDataSource apiLoginDataSource=new ApiLoginDataSource();

    @Override
    public Single<Message> login(String email,String pass) {
        return apiLoginDataSource.login(email,pass);
    }
}
