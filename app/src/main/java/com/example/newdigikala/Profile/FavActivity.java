package com.example.newdigikala.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.newdigikala.Detail.DetailActivity;
import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Favorite;
import com.example.newdigikala.R;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class FavActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String email;
    EditProfileViewModel viewModel=new EditProfileViewModel();
    CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        setupViews();
        email=getIntent().getExtras().getString("email");
        viewModel.getFavorite(email).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Favorite>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Favorite> favorites) {
                        recyclerView.setAdapter(new FavAdapter(favorites, FavActivity.this, new FavAdapter.OnFavoriteClick() {
                            @Override
                            public void onFavClick(String productId) {
                                Intent intent=new Intent(FavActivity.this, DetailActivity.class);
                                intent.putExtra("id",productId);
                                startActivity(intent);
                            }
                        }, new FavAdapter.OnDeleteClick() {
                            @Override
                            public void onDelete(String favId) {
                                viewModel.deleteFav(favId).subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new SingleObserver<Message>() {
                                            @Override
                                            public void onSubscribe(@NonNull Disposable d) {
                                                compositeDisposable.add(d);
                                            }

                                            @Override
                                            public void onSuccess(@NonNull Message message) {
                                                Toast.makeText(FavActivity.this, "حذف شد", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {
                                                Log.i("LOG", "onError: "+e.toString());
                                            }
                                        });
                            }
                        }));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("LOG", "onError: "+e.toString());
                    }
                });
    }

    private void setupViews() {
        recyclerView=findViewById(R.id.rv_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}