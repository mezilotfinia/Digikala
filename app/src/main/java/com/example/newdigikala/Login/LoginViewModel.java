package com.example.newdigikala.Login;

import android.content.Context;

import io.reactivex.Single;

public class LoginViewModel {
    private UserLoginInfo userLoginInfo;
    LoginRepository loginRepository=new LoginRepository();
    public LoginViewModel(Context context) {
        userLoginInfo = new UserLoginInfo(context);
    }

    public Single<Message> login(String email,String pass){
        return loginRepository.login(email,pass);
    }
    public void saveUserEmail(String email){
        userLoginInfo.saveLoginData(email);
    }

}
