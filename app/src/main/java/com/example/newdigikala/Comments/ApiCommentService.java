package com.example.newdigikala.Comments;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;
import com.example.newdigikala.Model.Comment;

import java.util.List;

import io.reactivex.Single;

public class ApiCommentService {
    ApiService apiService= ApiProvider.apiProvider();
    public Single<List<Comment>> getComments(String id){
        return apiService.getComments(id);
    }
    public Single<Message> likeComment(String id){
        return apiService.likeComment(id);
    }
    public Single<Message> dislikeComment(String id){
        return apiService.likeComment(id);
    }
}
