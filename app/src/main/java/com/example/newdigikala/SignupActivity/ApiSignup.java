package com.example.newdigikala.SignupActivity;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;

import io.reactivex.Single;

public class ApiSignup implements SignupDataSource{
    ApiService apiService= ApiProvider.apiProvider();
    @Override
    public Single<Message> signup(String email, String pass) {
        return apiService.signup(email,pass);
    }
}
