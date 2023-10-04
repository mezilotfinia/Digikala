package com.example.newdigikala.Profile;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Favorite;

import java.util.List;

import io.reactivex.Single;

public class EditProfileRepository {
    EditProfileApiService apiService=new EditProfileApiService();
    public Single<Message> updateProfile(String email,String name,String family,String code,String home,String mobile,String birthday,int jensiat,int khabarname,int level){
        return apiService.updateProfile(email,name,family,code,home,mobile,birthday,jensiat,khabarname,level);
    }
    public Single<List<Favorite>> getFavorite(String email){
        return apiService.getFavorite(email);
    }
    public Single<Message> deleteFav(String id){
        return apiService.deleteFav(id);
    }
}
