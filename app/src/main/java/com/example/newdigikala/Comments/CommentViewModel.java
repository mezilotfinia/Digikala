package com.example.newdigikala.Comments;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Comment;

import java.util.List;

import io.reactivex.Single;

public class CommentViewModel {
    CommentRepository commentRepository = new CommentRepository();

    public Single<List<Comment>> getComments(String id) {
        return commentRepository.getComments(id);
    }

    public Single<Message> likeOrDislikeComment(Comment comment,String id, String likeOrDislike) {
        if (likeOrDislike.equals("like"))
            return commentRepository.likeComment(comment.getId());
        else
            return commentRepository.dislikeComment(comment.getId());
    }

}
