package com.example.newdigikala.Comments.AddComment;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newdigikala.Login.LoginActivity;
import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Comment;
import com.example.newdigikala.Model.RatingModel;
import com.example.newdigikala.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class AddCommentActivity extends AppCompatActivity {

    Comment comment;
    ImageView imgBack;
    RecyclerView recyclerView;
    Button btnSetPoint,btnWriteComment;
    List<RatingModel> ratingModels;
    AddCommentAdapter addCommentAdapter;
    AddCommentViewModel viewModel;
    CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        setupViews();
        comment=getIntent().getParcelableExtra("comment");
        getParams(comment);

    }

    private void setupViews() {
        viewModel=new AddCommentViewModel(this);
        ratingModels=new ArrayList<>();
        imgBack=findViewById(R.id.img_addComment_back);
        recyclerView=findViewById(R.id.rv_addComment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnWriteComment=findViewById(R.id.btn_addComment_writeComment);
        btnWriteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddCommentActivity.this,WriteCommentActivity.class);
                startActivityResult.launch(intent);
            }
        });
        btnSetPoint=findViewById(R.id.btn_addComment_setPoint);
        btnSetPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.sendPoints(addCommentAdapter.getRatingModels())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Message>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(@NonNull Message message) {
                                finish();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.i("LOG", "onError: "+e.toString());
                            }
                        });
            }
        });
    }
    public void getParams(Comment comment){
        String param=comment.getParam();
        try {
            JSONArray jsonArray=new JSONArray(param);
            for (int i = 0; i <jsonArray.length(); i++) {
                RatingModel ratingModel=new RatingModel();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                ratingModel.setTitle(jsonObject.getString("title"));
                ratingModel.setValue(jsonObject.getString("value"));
                ratingModels.add(ratingModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addCommentAdapter=new AddCommentAdapter(ratingModels,this);
        recyclerView.setAdapter(addCommentAdapter);
    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        String title=data.getExtras().getString("title");
                        String positive=data.getExtras().getString("positive");
                        String negative=data.getExtras().getString("negative");
                        String passage=data.getExtras().getString("passage");
                        viewModel.sendParam(title,positive,negative,passage,viewModel.addCommentRepository.checkLogin())
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<Message>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                        compositeDisposable.add(d);
                                    }

                                    @Override
                                    public void onSuccess(@NonNull Message message) {
                                        if (message.getStatus().equals("error")){
                                            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(AddCommentActivity.this, "نظر شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.i("LOG", "onError: "+e.toString());
                                    }
                                });
                    }
                }
            });

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

}