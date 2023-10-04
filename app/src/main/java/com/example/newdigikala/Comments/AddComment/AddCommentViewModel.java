package com.example.newdigikala.Comments.AddComment;

import android.content.Context;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.RatingModel;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class AddCommentViewModel {
    AddCommentRepository addCommentRepository;
    Context context;
    public AddCommentViewModel(Context context){
        this.context=context;
        addCommentRepository=new AddCommentRepository(context);
    }
    
    public Single<Message> sendPoints(List<RatingModel> ratingModels){
        return addCommentRepository.sendPoints(ratingModels);
    }
    public Single<Message> sendParam(String title,String positive,String negative,String passage,String user){
        if (addCommentRepository.checkLogin().equals("")){
            Message message=new Message();
            message.setStatus("error");
            message.setMessage("user has not logged in");
            //inja ba just khodemon bhsh meqdar dadim k az server nagire
            return Single.just(message)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }else {
            String email=addCommentRepository.checkLogin();
            return addCommentRepository.sendParam(title,positive,negative,passage,email);
        }
    }
}
