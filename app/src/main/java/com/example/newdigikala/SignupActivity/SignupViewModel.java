package com.example.newdigikala.SignupActivity;

import android.content.Context;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Login.UserLoginInfo;

import io.reactivex.Single;

public class SignupViewModel {
    UserLoginInfo userLoginInfo;
    SignupRepository signupRepository=new SignupRepository();
    Context context;

    public SignupViewModel(Context context) {
        this.context=context;
        userLoginInfo=new UserLoginInfo(context);
    }

    public Single<Message> signup(String email, String pass){
        return signupRepository.signup(email,pass);
    }

    public void saveUserInfo(String email){
        userLoginInfo.saveLoginData(email);
    }
}
