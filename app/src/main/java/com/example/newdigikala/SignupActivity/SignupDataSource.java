package com.example.newdigikala.SignupActivity;

import com.example.newdigikala.Login.Message;

import io.reactivex.Single;

public interface SignupDataSource {
    Single<Message> signup(String email,String pass);
}
