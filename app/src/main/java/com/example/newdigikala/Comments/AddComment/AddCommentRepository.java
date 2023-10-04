package com.example.newdigikala.Comments.AddComment;

import android.content.Context;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Login.UserLoginInfo;
import com.example.newdigikala.Model.RatingModel;

import java.util.List;

import io.reactivex.Single;

public class AddCommentRepository {
    Context context;
    UserLoginInfo userLoginInfo;

    public AddCommentRepository(Context context) {
        this.context = context;
        userLoginInfo = new UserLoginInfo(context);
    }

    AddCommentApiService addCommentApiService=new AddCommentApiService();
    public Single<Message> sendPoints(List<RatingModel> ratingModels){
        return addCommentApiService.sendPoint(ratingModels);
    }

    public String checkLogin(){
        return userLoginInfo.getUserEmail();
    }

    public Single<Message> sendParam(String title, String positive, String negative, String passage,String  user) {
        return addCommentApiService.sendParam(title,positive,negative,passage,user);
    }
}
