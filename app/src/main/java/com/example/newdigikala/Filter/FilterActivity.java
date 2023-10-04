package com.example.newdigikala.Filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.newdigikala.Detail.DetailActivity;
import com.example.newdigikala.Model.Product;
import com.example.newdigikala.R;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class FilterActivity extends AppCompatActivity {

    String cat, id;
    RecyclerView recyclerView;
    FilterViewModel viewModel = new FilterViewModel();
    FilterItemAdapter filterItemAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    LinearLayout parent;
    String filterParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setupViews();
        id = getIntent().getExtras().getString("id");
        cat = getIntent().getExtras().getString("cat");

        viewModel.getTabItem(cat)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Product>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Product> products) {
                        filterItemAdapter = new FilterItemAdapter(products, new FilterItemAdapter.OnItemClick() {
                            @Override
                            public void onClick(String id) {
                                Intent intent=new Intent(FilterActivity.this, DetailActivity.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                            }
                        });
                        filterParams=products.get(0).getFilterItem();
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .repeat(0)
                                .playOn(findViewById(R.id.rv_filter_productList));
                        recyclerView.setAdapter(filterItemAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("LOG", "onError: " + e.toString());
                    }
                });
    }

    private void setupViews() {
        parent=findViewById(R.id.linear_filter_parent);
        recyclerView = findViewById(R.id.rv_filter_productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RelativeLayout sort = findViewById(R.id.rel_filter_sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortDialog sortDialog=new SortDialog();
                sortDialog.setOnDialogItemClick(new SortDialog.OnDialogItemClick() {
                    @Override
                    public void onClick(int sort) {
                        viewModel.getSortedItem(cat,sort)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<List<Product>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        compositeDisposable.add(d);
                                    }

                                    @Override
                                    public void onSuccess(List<Product> products) {
                                        filterItemAdapter=new FilterItemAdapter(products, new FilterItemAdapter.OnItemClick() {
                                            @Override
                                            public void onClick(String id) {
                                                Intent intent=new Intent(FilterActivity.this, DetailActivity.class);
                                                intent.putExtra("id",id);
                                                startActivity(intent);
                                            }
                                        });
                                        YoYo.with(Techniques.FadeIn)
                                                .duration(1000)
                                                .repeat(0)
                                                .playOn(findViewById(R.id.rv_filter_productList));
                                        filterItemAdapter.notifyDataSetChanged();
                                        recyclerView.setAdapter(filterItemAdapter);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.i("LOG", "onError: "+e.toString());
                                    }
                                });
                    }
                });
                sortDialog.show(getSupportFragmentManager(),null);
            }
        });
        RelativeLayout filter = findViewById(R.id.rel_filter_filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                FilterFragment filterFragment=new FilterFragment();
                transaction.add(android.R.id.content,filterFragment);
                YoYo.with(Techniques.SlideInRight)
                        .repeat(0)
                        .duration(500)
                        .playOn(findViewById(android.R.id.content));
                Bundle bundle=new Bundle();
                bundle.putString("params",filterParams);
                filterFragment.setArguments(bundle);
                transaction.commit();
            }
        });
    }

    public void filterProducts(List<JSONObject> jsonObjects){
        Toast.makeText(this, "filter product activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
