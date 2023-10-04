package com.example.newdigikala.Category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.CompareProduct.SearchItemAdapter;
import com.example.newdigikala.Detail.DetailActivity;
import com.example.newdigikala.Filter.FilterActivity;
import com.example.newdigikala.Main.ProductAdapter;
import com.example.newdigikala.Model.Product;
import com.example.newdigikala.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CatFragment extends Fragment {

    View view;
    String cat;
    //type shod newInstance va ina omd
    CatViewModel viewModel=new CatViewModel();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    RecyclerView recyclerView;

    public static CatFragment newInstance(String title) {
        Bundle args = new Bundle();
        //ino dadim k title ha pooya bshe
        args.putString("title",title);
        CatFragment fragment = new CatFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view= inflater.inflate(R.layout.cat_fragment,container,false);
        }
        setupViews();
        viewModel.getTabItem(cat).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Product>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Product> products) {
                        recyclerView.setAdapter(new CatAdapter(getContext(), products, new CatAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(String id) {
                                Intent intent=new Intent(getContext(), FilterActivity.class);
                                intent.putExtra("id",id);
                                intent.putExtra("cat",cat);
                                startActivity(intent);
                            }
                        }));
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.i("LOG", "onError: "+e.toString());
                    }
                });
        return view;
    }

    private void setupViews() {
        cat=getArguments().getString("title");
        recyclerView=view.findViewById(R.id.rv_catFragment_catItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
