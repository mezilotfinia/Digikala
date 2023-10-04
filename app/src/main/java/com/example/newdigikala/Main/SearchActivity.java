package com.example.newdigikala.Main;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newdigikala.CompareProduct.SearchItemAdapter;
import com.example.newdigikala.Detail.DetailActivity;
import com.example.newdigikala.Model.Product;
import com.example.newdigikala.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imgMic;
    EditText edtSearch;
    MainViewModel mainViewModel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SearchItemAdapter searchItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
    }

    private void setupViews() {
        mainViewModel = new MainViewModel(SearchActivity.this);
        recyclerView = findViewById(R.id.rv_search_productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imgMic = findViewById(R.id.img_search_mic);
        imgMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "نام محصول را بگویید");

                try {
                    startActivityResult.launch(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(SearchActivity.this, "متاسفانه گوشی شما از این قابلیت پشتیبانی نمیکند", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edtSearch = findViewById(R.id.edt_search_search);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtSearch.getText().toString().isEmpty()) {
                    mainViewModel.search(String.valueOf(s))
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<List<Product>>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                    compositeDisposable.add(d);
                                }

                                @Override
                                public void onSuccess(@NonNull List<Product> products) {
                                    searchItemAdapter = new SearchItemAdapter(new SearchItemAdapter.OnSearchedItemClickListener() {
                                        @Override
                                        public void onClickListener(String properties, String image, String id) {
                                            Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                                            intent.putExtra("id", id);
                                            startActivity(intent);
                                        }
                                    });
                                    searchItemAdapter.onBind(products);
                                    recyclerView.setAdapter(searchItemAdapter);
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    Log.i("LOG", "onError: " + e.toString());
                                }
                            });
                } else {
                    searchItemAdapter.onBind(new ArrayList<Product>());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        ArrayList<String> data=result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        edtSearch.setText(data.get(0));
                    }
                }
            });
}