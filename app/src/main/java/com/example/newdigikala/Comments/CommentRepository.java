package com.example.newdigikala.Comments;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Comment;

import java.util.List;

import io.reactivex.Single;

public class CommentRepository {
    ApiCommentService apiCommentService=new ApiCommentService();
    public Single<List<Comment>> getComments(String id){
        return apiCommentService.getComments(id);
    }
    public Single<Message> likeComment(String id){
        return apiCommentService.likeComment(id);
    }
    public Single<Message> dislikeComment(String id){
        return apiCommentService.dislikeComment(id);
    }
}
