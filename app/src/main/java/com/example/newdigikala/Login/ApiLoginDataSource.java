package com.example.newdigikala.Login;

import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;

import io.reactivex.Single;

public class ApiLoginDataSource implements LoginDataSource{

    ApiService apiService= ApiProvider.apiProvider();

    @Override
    public Single<Message> login(String email,String pass) {
        return apiService.login(email,pass);
    }
}
