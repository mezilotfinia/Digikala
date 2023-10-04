package com.example.newdigikala.ShippingCart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newdigikala.Model.Basket;
import com.example.newdigikala.R;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class CheckoutActivity extends AppCompatActivity {

    String orderId;
    RecyclerView recyclerView;
    TextView txtName,txtTransActionCode;
    ShippingViewModel viewModel;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    ShippingAdapter shippingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setupViews();
        Uri uri=getIntent().getData();
        orderId=uri.getQueryParameter("order_id");
        txtTransActionCode.setText(orderId);
        observeForUpdateBasket();
    }

    private void observeForUpdateBasket() {
        viewModel.updateBasket(orderId,viewModel.getUserEmail())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Basket>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Basket> baskets) {
                        shippingAdapter=new ShippingAdapter(CheckoutActivity.this, baskets, new ShippingAdapter.OnBasketItemClick() {
                            @Override
                            public void onItemClick(String productId, String basketId) {

                            }
                        }, new ShippingAdapter.OnDeleteItem() {
                            @Override
                            public void onDelete(Basket basket) {

                            }
                        }, new ShippingAdapter.OnPriceCallBack() {
                            @Override
                            public void onPriceBack(String price) {

                            }
                        });
                        recyclerView.setAdapter(shippingAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("LOG", "onError: "+e.toString());
                    }
                });
    }

    private void setupViews() {
        viewModel=new ShippingViewModel(this);
        recyclerView=findViewById(R.id.rv_checkOut_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtName=findViewById(R.id.txt_checkOut_userName);
        txtTransActionCode=findViewById(R.id.txt_checkOut_trancactionCode);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}