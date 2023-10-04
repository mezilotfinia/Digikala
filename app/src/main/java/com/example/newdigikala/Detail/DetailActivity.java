package com.example.newdigikala.Detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newdigikala.Chart.ChartActivity;
import com.example.newdigikala.Comments.CommentActivity;
import com.example.newdigikala.CompareProduct.CompareActivity;
import com.example.newdigikala.Login.LoginActivity;
import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Detail;
import com.example.newdigikala.Model.DetailFavorite;
import com.example.newdigikala.Model.Favorite;
import com.example.newdigikala.Model.RatingModel;
import com.example.newdigikala.Properties.PropertiesActivity;
import com.example.newdigikala.R;
import com.example.newdigikala.ShippingCart.ShippingActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    DetailViewModel detailViewModel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String id, title, imgUrl;
    TextView txtTitle,txtCardCount, txtName, txtColor, txtGuarantee, txtPrice, txtPoints, txtMore, txtDescription, txtToolbarTitle;
    ImageView imgImage, imgShare, imgFav, imgBack, imgCart, imgMore;
    CardView properties, comments;
    Button btnAddToBasket;
    RecyclerView recyclerView;
    List<RatingModel> ratingModels;
    RatingBar ratingBar;
    NestedScrollView nestedScrollView;
    //bad az scroll rang toolbar avaz bshe bad az yek adad khas residn scroll(X,Y)
    //check krdn in b barname lag mide pas thread joda az asli
    Thread thread;
    Handler handler;
    RelativeLayout toolbar;
    int toolbarMergeColor, drawableMergeColor, toolbarTitleYPosition = -1, startHeight;
    List<DetailFavorite> favorites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupViews();
        getIntentId();
        txtToolbarTitle.setTranslationY(150);
        observeForDetail();
        observeForBasketCount();
    }

    private void observeForBasketCount() {
        detailViewModel.getBasketCount(detailViewModel.getUserEmail())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Message>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Message message) {
                        if (message.getMessage().equals("0")){

                        }else {
                            txtCardCount.setVisibility(View.VISIBLE);
                            txtCardCount.setText(message.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("LOG", "onError: "+e.toString());
                    }
                });
    }

    private void getIntentId() {
        id = getIntent().getExtras().getString("id");
    }

    private void observeForDetail() {
        detailViewModel.getDetails(id, detailViewModel.getUserEmail().equals("") ? "" : detailViewModel.getUserEmail()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Detail>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Detail> details) {
                        favorites = details.get(0).getFavList();
                        imgUrl = details.get(0).getImage();
                        Picasso.get().load(details.get(0).getImage()).into(imgImage);
                        title = details.get(0).getTitle();
                        txtTitle.setText(details.get(0).getTitle());
                        txtToolbarTitle.setText(details.get(0).getTitle());
                        txtName.setText(details.get(0).getTitle());
                        txtColor.setText(details.get(0).getColors());
                        txtPrice.setText(" تومان " + details.get(0).getPrice());
                        txtDescription.setText(Html.fromHtml(details.get(0).getIntroduction()));
                        txtGuarantee.setText(details.get(0).getGarantee());
                        txtPoints.setText(details.get(0).getRating());
                        ratingBar.setRating(Float.parseFloat(details.get(0).getRating()));

                        try {
                            JSONArray ratingItemArray = new JSONArray(details.get(0).getRatingItem());
                            for (int i = 0; i < ratingItemArray.length(); i++) {
                                RatingModel ratingModel = new RatingModel();
                                JSONObject jsonObject = ratingItemArray.getJSONObject(i);
                                String title = jsonObject.getString("title");
                                String value = jsonObject.getString("value");

                                ratingModel.setTitle(title);
                                ratingModel.setValue(value);
                                ratingModels.add(ratingModel);
                            }
                            recyclerView.setAdapter(new RatingAdapter(ratingModels));

                            //JSONArray propertiesArray=new JSONArray(details.get(0).getProperties());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("LOG", "onError: " + e.toString());
                    }
                });
    }

    private void setupViews() {
        txtCardCount=findViewById(R.id.txt_detail_cardCount);
        detailViewModel = new DetailViewModel(DetailActivity.this);
        txtToolbarTitle = findViewById(R.id.txt_detail_toolbarTitle);
        imgCart = findViewById(R.id.img_detail_cart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailActivity.this, ShippingActivity.class);
                intent.putExtra("count",txtCardCount.getText().toString());
                startActivity(intent);
            }
        });
        imgMore = findViewById(R.id.img_detail_more);
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailActivity.this, imgMore);
                popupMenu.getMenuInflater().inflate(R.menu.detail_more_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_more_chart) {
                            Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                            intent.putExtra("id", id);
                            intent.putExtra("title", title);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), CompareActivity.class);
                            intent.putExtra("image_url", imgUrl);
                            startActivity(intent);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        imgBack = findViewById(R.id.img_detail_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar = findViewById(R.id.rel_detail_toolbar);
        ratingModels = new ArrayList<>();
        imgImage = findViewById(R.id.img_detail_image);
        imgShare = findViewById(R.id.img_detail_share);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"");
                startActivity(Intent.createChooser(intent,"اشتراک گذاری در"));
            }
        });
        imgFav = findViewById(R.id.img_detail_favorite);
        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailViewModel.getUserEmail().equals("")) {
                    Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    FavoriteBottomSheet favoriteBottomSheet = new FavoriteBottomSheet();
                    Bundle bundle = new Bundle();
                    bundle.putString("url",favorites.get((favorites.size()) - 1).getFavImage());
                    bundle.putInt("count",favorites.size());
                    favoriteBottomSheet.setArguments(bundle);

                    favoriteBottomSheet.setOnTextClick(new FavoriteBottomSheet.OnTextClick() {
                        @Override
                        public void onClick() {
                            detailViewModel.addFavorite(detailViewModel.getUserEmail(),id,0,"")
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new SingleObserver<Message>() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {
                                            compositeDisposable.add(d);
                                        }

                                        @Override
                                        public void onSuccess(@NonNull Message message) {
                                            Log.i("LOG", "onSuccess: "+message.getStatus());
                                            Toast.makeText(DetailActivity.this, "محصول اضافه شد", Toast.LENGTH_SHORT).show();
                                            favoriteBottomSheet.dismiss();
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {
                                            Log.i("LOG", "onError: "+e.toString());
                                        }
                                    });
                        }
                    });

                    favoriteBottomSheet.setOnAddImageClick(new FavoriteBottomSheet.OnAddImageClick() {
                        @Override
                        public void onClick() {
                            NewFolderDialog newFolderDialog=new NewFolderDialog();
                            newFolderDialog.setOnSubmitClick(new NewFolderDialog.OnSubmitClick() {
                                @Override
                                public void onClick(String folderName) {
                                    newFolderDialog.dismiss();
                                    detailViewModel.addFavorite(detailViewModel.getUserEmail(),id,1,folderName)
                                            .subscribeOn(Schedulers.newThread())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new SingleObserver<Message>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {
                                                    compositeDisposable.add(d);
                                                }

                                                @Override
                                                public void onSuccess(@NonNull Message message) {
                                                    Log.i("LOG", "onSuccess: "+message.getStatus());
                                                    Toast.makeText(DetailActivity.this, "محصول اضافه شد", Toast.LENGTH_SHORT).show();
                                                    favoriteBottomSheet.dismiss();
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {
                                                    Log.i("LOG", "onError: "+e.toString());
                                                }
                                            });
                                }
                            });
                            newFolderDialog.show(getSupportFragmentManager(),null);
                        }
                    });
                    favoriteBottomSheet.show(getSupportFragmentManager(), null);
                }
            }
        });
        txtTitle = findViewById(R.id.txt_detail_title);
        txtName = findViewById(R.id.txt_detail_name);
        txtColor = findViewById(R.id.txt_detail_color);
        txtGuarantee = findViewById(R.id.txt_detail_guarantee);
        txtPrice = findViewById(R.id.txt_detail_price);
        txtPoints = findViewById(R.id.txt_detail_points);
        txtMore = findViewById(R.id.txt_detail_more);
        txtDescription = findViewById(R.id.txt_detail_description);

        txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtMore.getText().toString().equals("ادامه مطلب")) {
                    startHeight = txtDescription.getHeight();
                    txtDescription.setMaxLines(Integer.MAX_VALUE);
                    int widthSpec = View.MeasureSpec.makeMeasureSpec(txtDescription.getWidth(), View.MeasureSpec.EXACTLY);
                    int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    txtDescription.measure(widthSpec, heightSpec);
                    int targetHeight = txtDescription.getMeasuredHeight();
                    final int heightSpan = targetHeight - startHeight;
                    txtDescription.getLayoutParams().height = startHeight;
                    txtDescription.setLayoutParams(txtDescription.getLayoutParams());
                    Animation animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            txtDescription.getLayoutParams().height = (int) (startHeight + heightSpan * interpolatedTime);
                            txtDescription.setLayoutParams(txtDescription.getLayoutParams());
                        }
                    };
                    animation.setDuration(300);
                    txtDescription.startAnimation(animation);
                    txtMore.setText("بستن");
                } else {
                    startHeight = txtDescription.getHeight();
                    txtDescription.setMaxLines(8);
                    int widthSpec = View.MeasureSpec.makeMeasureSpec(txtDescription.getWidth(), View.MeasureSpec.EXACTLY);
                    int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    txtDescription.measure(widthSpec, heightSpec);
                    int targetHeight = txtDescription.getMeasuredHeight();
                    final int heightSpan = targetHeight - startHeight;
                    txtDescription.getLayoutParams().height = startHeight;
                    txtDescription.setLayoutParams(txtDescription.getLayoutParams());
                    Animation animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            txtDescription.getLayoutParams().height = (int) (startHeight + heightSpan * interpolatedTime);
                            txtDescription.setLayoutParams(txtDescription.getLayoutParams());
                        }
                    };
                    animation.setDuration(300);
                    txtDescription.startAnimation(animation);
                    txtMore.setText("ادامه مطلب");
                }

            }
        });

        properties = findViewById(R.id.card_detail_properties);
        properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, PropertiesActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
        comments = findViewById(R.id.card_detail_comments);
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, CommentActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", txtName.getText().toString());
                startActivity(intent);
            }
        });

        btnAddToBasket = findViewById(R.id.btn_detail_addToBasket);
        btnAddToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailViewModel.addToBasket(id,detailViewModel.getUserEmail())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Message>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(@NonNull Message message) {
                                if (message.getStatus().equals("success")){
                                    Toast.makeText(DetailActivity.this, "محصول با موفقیت به سبد خرید اضافه شد", Toast.LENGTH_SHORT).show();
                                    int currentCount=Integer.parseInt(txtCardCount.getText().toString());
                                    currentCount++;
                                    txtCardCount.setText(currentCount+"");
                                    txtCardCount.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.i("LOG", "onError: "+e.toString());
                            }
                        });
            }
        });

        recyclerView = findViewById(R.id.rv_detail_points);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ratingBar = findViewById(R.id.rating_detail);
        nestedScrollView = findViewById(R.id.nestedScroll_detail);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //txtToolbar animation
                        if (scrollY > 600 && scrollY > oldScrollY && scrollY < 1000) {
                            if (toolbarTitleYPosition > -47) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtToolbarTitle.setTranslationY(toolbarTitleYPosition - 5);
                                        toolbarTitleYPosition = toolbarTitleYPosition - 5;
                                    }
                                });
                            }
                        } else if (scrollY < 600 && scrollY < oldScrollY && scrollY > 400) {
                            if (toolbarTitleYPosition < 150) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtToolbarTitle.setTranslationY(toolbarTitleYPosition + 5);
                                        toolbarTitleYPosition = toolbarTitleYPosition + 5;
                                    }
                                });
                            }
                        } else if (scrollY < 400) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toolbarTitleYPosition = 150;
                                    txtToolbarTitle.setTranslationY(toolbarTitleYPosition);
                                }
                            });
                        } else if (scrollY > 1000) {
                            toolbarTitleYPosition = -47;
                            txtToolbarTitle.setTranslationY(toolbarTitleYPosition);
                        }
                        //-----------------
                        //colorToolbar animation
                        if (scrollY > 50 && scrollY < 1000) {
                            //tarkib rang
                            //ratio harchi bishtar tamayol b rang dovom
                            float ratio = (scrollY / 1000f);
                            toolbarMergeColor = ColorUtils.blendARGB(ContextCompat.getColor(getApplicationContext(), R.color.white), ContextCompat.getColor(getApplicationContext(), R.color.red), ratio);
                            //taqir rang icon ha
                            drawableMergeColor = ColorUtils.blendARGB(ContextCompat.getColor(getApplicationContext(), R.color.gray500), ContextCompat.getColor(getApplicationContext(), R.color.white), ratio);
                            //vase in k crash kard va kam avord barname inaro avordim ro asli
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DrawableCompat.setTint(imgBack.getDrawable(), drawableMergeColor);
                                    DrawableCompat.setTint(imgCart.getDrawable(), drawableMergeColor);
                                    DrawableCompat.setTint(imgMore.getDrawable(), drawableMergeColor);
                                    toolbar.setBackgroundColor(toolbarMergeColor);
                                }
                            });
                            //harekat ro b bala bod sefid kon qabl az 50
                        } else if (scrollY < 50 && oldScrollY > scrollY) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                                }
                            });
                        } else if (scrollY > 1000) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                                }
                            });
                        }
                        //alpha animation
/*                      if (scrollY<1000){
                          float alpha=(scrollY/1000f);
                          toolbar.setAlpha(alpha);
                      }*/
                    }
                });
                thread.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}