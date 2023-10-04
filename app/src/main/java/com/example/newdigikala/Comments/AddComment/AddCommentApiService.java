package com.example.newdigikala.Comments.AddComment;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;
import com.example.newdigikala.Model.RatingModel;

import java.util.List;

import io.reactivex.Single;

public class AddCommentApiService {
    ApiService apiService= ApiProvider.apiProvider();
    public Single<Message> sendPoint(List<RatingModel> ratingModels){
        return apiService.sendPoint(ratingModels);
    }

    public Single<Message> sendParam(String title, String positive, String negative, String passage,String user) {
        return apiService.sendParam(title,positive,negative,passage,user);
    }
}
