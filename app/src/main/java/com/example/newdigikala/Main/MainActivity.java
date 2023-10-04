package com.example.newdigikala.Main;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newdigikala.Category.CatActivity;
import com.example.newdigikala.Detail.DetailActivity;
import com.example.newdigikala.Login.LoginActivity;
import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Banner;
import com.example.newdigikala.Model.Cat;
import com.example.newdigikala.Model.MyTimer;
import com.example.newdigikala.Model.Product;
import com.example.newdigikala.Profile.ProfileActivity;
import com.example.newdigikala.R;
import com.example.newdigikala.ShippingCart.ShippingActivity;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ImageView imgCart, imgSearch, imgMenu;
    RecyclerView recyclerViewWonderful, recyclerViewNewest,recyclerViewCats;
    MainViewModel mainViewModel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Slider slider;
    ImageView image1, image2, image3, image4;
    TextView txtHour, txtMin, txtSec,txtLogin,txtCartCount;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        getTimer();
        observeForWonderfulProducts();
        observeForBanners();
        observeForNewestProduct();
        observeForCats();
        checkLoginMode();
    }

    private void observeForCats() {
        mainViewModel.getCats().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Cat>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Cat> cats) {
                        recyclerViewCats.setAdapter(new CatsAdapter(cats, new CatsAdapter.OnCatClickListener() {
                            @Override
                            public void onClick(int position, String title) {
                                Intent intent=new Intent(MainActivity.this, CatActivity.class);
                                intent.putExtra("position",position);
                                intent.putExtra("title",title);
                                intent.putParcelableArrayListExtra("cats", (ArrayList<? extends Parcelable>) cats);
                                startActivity(intent);
                            }
                        }));
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.i("LOG", "onError: "+e.toString());
                    }
                });
    }

    private void checkLoginMode() {
        String email = mainViewModel.getUserEmail();
        if (!email.equals("")) {
            txtLogin.setText(email);
            mainViewModel.getCartCount(email)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Message>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(Message message) {
                            if (!message.getMessage().equals("0")) {
                                txtCartCount.setVisibility(View.VISIBLE);
                                txtCartCount.setText(message.getMessage());
                            }else{
                                txtCartCount.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
    }

    private void observeForNewestProduct() {
        mainViewModel.getProducts().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Product>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Product> products) {
                        recyclerViewNewest.setAdapter(new ProductAdapter(MainActivity.this, products, new ProductAdapter.OnProductClick() {
                            @Override
                            public void onClick(String id) {
                                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                            }
                        }));
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e("LOG", "onError: "+e.toString());
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawer.isDrawerOpen(Gravity.RIGHT)){
            drawer.closeDrawer(Gravity.RIGHT);
        }
        checkLoginMode();
    }

    private void setupViews() {
        txtCartCount=findViewById(R.id.txt_main_cartCount);
        imgCart = findViewById(R.id.img_main_cart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ShippingActivity.class);
                intent.putExtra("count",txtCartCount.getText().toString());
                startActivity(intent);
            }
        });

        recyclerViewCats=findViewById(R.id.rv_main_cats);
        recyclerViewCats.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        mainViewModel = new MainViewModel(this);
        txtLogin=findViewById(R.id.txt_navigation_login);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainViewModel.getUserEmail().equals("")){
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("email",mainViewModel.getUserEmail());
                    startActivity(intent);
                }

            }
        });
        txtHour = findViewById(R.id.txt_main_timerHour);
        txtMin = findViewById(R.id.txt_main_timerMin);
        txtSec = findViewById(R.id.txt_main_timerSec);
        image1 = findViewById(R.id.img_main_image1);
        image2 = findViewById(R.id.img_main_image2);
        image3 = findViewById(R.id.img_main_image3);
        image4 = findViewById(R.id.img_main_image4);
        slider = findViewById(R.id.slider_main);
        //tebqe khode ketab khone slider az site github
        Slider.init(new PicassoImageLoadingService());
        recyclerViewNewest = findViewById(R.id.rv_main_newestList);
        recyclerViewWonderful = findViewById(R.id.rv_main_wonderfulList);
        imgMenu = findViewById(R.id.img_toolbar_menu);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });
        imgSearch = findViewById(R.id.img_toolbar_search);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        drawer = findViewById(R.id.drawer_main_parent);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //View view=navigationView.getHeaderView(0); view haye header navigation view k ye ja dig hstn
        //TextView textView=(view)findViewById(R.id.)
        navigationView.setNavigationItemSelectedListener(this);
        //reverseLayout=true ta az rast scroll bkhore
        recyclerViewWonderful.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
        recyclerViewNewest.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
    }

    private void getTimer() {
        mainViewModel.getTimer().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MyTimer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(final MyTimer myTimer) {
                        if (myTimer.getHour() < 10) {
                            txtHour.setText("0" + myTimer.getHour());
                        } else {
                            txtHour.setText(myTimer.getHour() + "");
                        }
                        if (myTimer.getMin() < 10) {
                            txtMin.setText("0" + myTimer.getMin());
                        } else {
                            txtMin.setText(myTimer.getMin() + "");
                        }
                        if (myTimer.getSec() < 10) {
                            txtSec.setText("0" + myTimer.getSec());
                        } else {
                            txtSec.setText(myTimer.getSec() + "");
                        }


                        timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {

                                if (myTimer.getSec() != 0) {
                                    myTimer.setSec(myTimer.getSec() - 1);
                                    if (myTimer.getSec() < 10) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtSec.setText("0" + myTimer.getSec());
                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtSec.setText(myTimer.getSec() + "");
                                            }
                                        });

                                    }
                                } else {
                                    if (myTimer.getMin() != 0) {
                                        myTimer.setMin(myTimer.getMin() - 1);
                                        myTimer.setSec(59);

                                        if (myTimer.getMin() < 10) {
                                            myTimer.setMin(myTimer.getMin() - 1);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    txtMin.setText("0" + myTimer.getMin());
                                                    txtSec.setText(myTimer.getSec() + "");
                                                }
                                            });

                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    txtMin.setText(myTimer.getMin() + "");
                                                    txtSec.setText(myTimer.getSec() + "");
                                                }
                                            });

                                        }

                                    } else {
                                        myTimer.setMin(59);
                                        myTimer.setSec(59);
                                        myTimer.setHour(myTimer.getHour() - 1);

                                        if (myTimer.getHour() < 10) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    txtHour.setText("0" + myTimer.getHour());
                                                    txtMin.setText(myTimer.getMin() + "");
                                                    txtSec.setText(myTimer.getSec() + "");
                                                }
                                            });

                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    txtHour.setText(myTimer.getHour() + "");
                                                    txtMin.setText(myTimer.getMin() + "");
                                                    txtSec.setText(myTimer.getSec() + "");
                                                }
                                            });

                                        }
                                    }

                                }
                            }
                        }, 0, 1000);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("LOG", "onError: " + e.toString());
                    }
                });
    }


    private void observeForBanners() {
        mainViewModel.getBanners().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Banner>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Banner> banners) {
                        List<Banner> sliderBanner = new ArrayList<>();
                        List<Banner> innerBanner = new ArrayList<>();
                        for (int i = 0; i < banners.size(); i++) {
                            if (banners.get(i).getType().equals("0")) {
                                sliderBanner.add(banners.get(i));
                            } else {
                                innerBanner.add(banners.get(i));
                            }
                        }
                        slider.setAdapter(new MainSliderAdapter(sliderBanner));
                        Picasso.get().load(innerBanner.get(0).getPic()).into(image1);
                        Picasso.get().load(innerBanner.get(1).getPic()).into(image2);
                        Picasso.get().load(innerBanner.get(2).getPic()).into(image3);
                        Picasso.get().load(innerBanner.get(3).getPic()).into(image4);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void observeForWonderfulProducts() {
        mainViewModel.getProducts().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Product>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Product> products) {
                        recyclerViewWonderful.setAdapter(new ProductAdapter(MainActivity.this, products, new ProductAdapter.OnProductClick() {
                            @Override
                            public void onClick(String id) {
                                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                            }
                        }));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("LOG", "error:" + e.toString());
                    }
                });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_main_parent);
        drawer.closeDrawer(Gravity.RIGHT);
        return true;
    }

    /*ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (drawer.isDrawerOpen(Gravity.RIGHT)){
                            drawer.closeDrawer(Gravity.RIGHT);
                        }
                        Intent data = result.getData();
                        mainViewModel.saveEmailData(data.getExtras().getString("email"));
                        txtLogin.setText(data.getExtras().getString("email"));
                        //doSomeOperations();
                    }
                }
            });*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.purge();
        timer.cancel();
        compositeDisposable.dispose();
    }
}