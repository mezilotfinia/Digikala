package com.example.newdigikala.ShippingCart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.newdigikala.Detail.DetailActivity;
import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Basket;
import com.example.newdigikala.R;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class ShippingActivity extends AppCompatActivity {

    String basketCount;
    ImageView imgBasket;
    TextView txtTotalPrice,txtCardCount;
    RecyclerView recyclerView;
    Button btnFinalBuy;
    ShippingViewModel viewModel;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    ShippingAdapter shippingAdapter;
    private int totalPrice=0;
    FrameLayout frameLayout;
    RelativeLayout totalPriceParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        setupViews();
        basketCount=getIntent().getExtras().getString("count");
        if (!basketCount.equals("0")){
            frameLayout.setVisibility(View.GONE);
            txtCardCount.setVisibility(View.VISIBLE);
            txtCardCount.setText(basketCount);
        }else {
            frameLayout.setVisibility(View.VISIBLE);
            btnFinalBuy.setVisibility(View.GONE);
            totalPriceParent.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }
        observeForBasketList(viewModel.getUserEmail());
    }

    private void observeForBasketList(String email) {
        viewModel.getBasketList(email).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Basket>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Basket> baskets) {
                        shippingAdapter=new ShippingAdapter(ShippingActivity.this, baskets, new ShippingAdapter.OnBasketItemClick() {
                            @Override
                            public void onItemClick(String productId, String basketId) {
                                Intent intent = new Intent(ShippingActivity.this, DetailActivity.class);
                                intent.putExtra("id", productId);
                                startActivity(intent);
                            }
                        }, new ShippingAdapter.OnDeleteItem() {
                            @Override
                            public void onDelete(Basket basket) {
                                viewModel.deleteBasketItem(basket.getBasketId()).subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new SingleObserver<Message>() {
                                            @Override
                                            public void onSubscribe(@NonNull Disposable d) {
                                                compositeDisposable.add(d);
                                            }

                                            @Override
                                            public void onSuccess(@NonNull Message message) {
                                                shippingAdapter.deleteRow(basket);
                                                int count = Integer.parseInt(txtCardCount.getText().toString());
                                                count--;
                                                if (count == 0) {
                                                    txtCardCount.setVisibility(View.GONE);
                                                } else {
                                                    txtCardCount.setVisibility(View.VISIBLE);
                                                    txtCardCount.setText(count + "");
                                                }
                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {
                                                Log.i("LOG", "onError: " + e.toString());
                                            }
                                        });
                            }
                        }, new ShippingAdapter.OnPriceCallBack() {
                            @Override
                            public void onPriceBack(String price) {
                                totalPrice+=Integer.parseInt(price);
                                txtTotalPrice.setText(totalPrice+"");
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
        totalPriceParent=findViewById(R.id.rel_shipping_totalPriceParent);
        frameLayout=findViewById(R.id.frame_shipping_emptyBasket);
        viewModel=new ShippingViewModel(ShippingActivity.this);
        imgBasket=findViewById(R.id.img_shipping_basket);
        txtCardCount=findViewById(R.id.txt_shipping_cartCount);
        txtTotalPrice=findViewById(R.id.txt_shipping_totalPrice);
        recyclerView=findViewById(R.id.rv_shipping_productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnFinalBuy=findViewById(R.id.btn_shipping_buy);
        btnFinalBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://ns20.ir/Mvvm/clicksite/test.php"));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}