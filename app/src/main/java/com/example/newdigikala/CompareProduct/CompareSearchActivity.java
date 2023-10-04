package com.example.newdigikala.CompareProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.newdigikala.Model.Product;
import com.example.newdigikala.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class CompareSearchActivity extends AppCompatActivity {

    CompareViewModel viewModel = new CompareViewModel();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText edtSearch;
    ProgressBar progressBar;
    ImageView imgClose;
    RecyclerView recyclerView;
    SearchItemAdapter searchItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_search);
        setupViews();
    }

    private void setupViews() {
        recyclerView = findViewById(R.id.rv_compareSearch_searchedProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchItemAdapter = new SearchItemAdapter(new SearchItemAdapter.OnSearchedItemClickListener() {
            @Override
            public void onClickListener(String properties,String image,String id) {
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                intent.putExtra("properties",properties);
                intent.putExtra("image",image);
                finish();
            }
        });
        recyclerView.setAdapter(searchItemAdapter);
        imgClose = findViewById(R.id.img_compareSearch_close);
        progressBar = findViewById(R.id.progress_compareSearch);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edtSearch = findViewById(R.id.edt_compareSearch_serach);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtSearch.getText().toString().equals("")) {
                    viewModel.getSearchedProduct(String.valueOf(s)).subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<List<Product>>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                    compositeDisposable.add(d);
                                }

                                @Override
                                public void onSuccess(@NonNull List<Product> products) {
                                    progressBar.setVisibility(View.GONE);
                                    searchItemAdapter.onBind(products);
                                    YoYo.with(Techniques.FadeIn)
                                            .repeat(0)
                                            .duration(500)
                                            .playOn(findViewById(R.id.rv_compareSearch_searchedProduct));
                                    recyclerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    Log.i("LOG", "onError: " + e.toString());
                                }
                            });
                }else {
                    searchItemAdapter.onBind(new ArrayList<Product>());
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}