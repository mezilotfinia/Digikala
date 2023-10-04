package com.example.newdigikala.Comments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.newdigikala.Comments.AddComment.AddCommentActivity;
import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Comment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.service.controls.actions.FloatAction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newdigikala.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class CommentActivity extends AppCompatActivity {

    TextView txtTitle;
    RecyclerView recyclerView;
    CommentViewModel commentViewModel=new CommentViewModel();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    String id;
    CommentAdapter commentAdapter;
    FrameLayout progressFrame;
    Comment intentComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setupViews();
        //taqir rng status bar
/*        if (Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(ContextCompat.getColor(CommentActivity.this,R.color.colorPrimary));
        }*/
        id=getIntent().getExtras().getString("id");
        txtTitle.setText(getIntent().getExtras().getString("name"));
        observeForComments(id);
    }

    private void observeForComments(String id) {
        commentViewModel.getComments(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Comment>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Comment> comments) {
                        if (comments.size()>0) {
                            intentComment = comments.get(0);
                            progressFrame.setVisibility(View.GONE);
                            commentAdapter = new CommentAdapter(comments, CommentActivity.this, new CommentAdapter.OnLikeOrDislikeClick() {
                                @Override
                                public void onClick(Comment comment, String likeOrDislike) {
                                    commentViewModel.likeOrDislikeComment(comment, comment.getId(), likeOrDislike)
                                            .subscribeOn(Schedulers.newThread())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new SingleObserver<Message>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {
                                                    compositeDisposable.add(d);
                                                }

                                                @Override
                                                public void onSuccess(@NonNull Message message) {
                                                    if (message.getStatus().equals("error")) {
                                                        Toast.makeText(CommentActivity.this, "خطای ناشناخته", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        commentAdapter.changeLikeOrDislikeCount(comment, likeOrDislike);
                                                    }
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {
                                                    Log.i("LOG", "onError: " + e.toString());
                                                }
                                            });
                                }
                            });
                            recyclerView.setAdapter(commentAdapter);
                        }else {
                            Toast.makeText(CommentActivity.this, "نظری برای نمایش وجود ندارد", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("LOG", "onError: "+e.toString());
                    }
                });
    }

    private void setupViews() {
        progressFrame=findViewById(R.id.frame_comment_progressFrame);
        ImageView imgClose=findViewById(R.id.img_comment_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtTitle=findViewById(R.id.txt_comment_productName);
        recyclerView=findViewById(R.id.rv_comment_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CommentActivity.this, AddCommentActivity.class);
                intent.putExtra("comment",intentComment);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}