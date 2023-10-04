package com.example.newdigikala.Properties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.newdigikala.Model.Properties;
import com.example.newdigikala.R;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class PropertiesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView txtTitle;
    PropertiesViewModel viewModel =new PropertiesViewModel();
    ImageView imgClose;
    CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);
        setupViews();
        String title=getIntent().getExtras().getString("title");
        txtTitle.setText(title);
        ObserveForProperties();
    }

    private void ObserveForProperties() {
        viewModel.getProperties().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Properties>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Properties> properties) {
                        YoYo.with(Techniques.SlideInRight)
                                .duration(1000)
                                .repeat(0)
                                .playOn(findViewById(R.id.rv_properties_propertiesList));
                        recyclerView.setAdapter(new PropertiesAdapter(properties,getApplicationContext()));

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("LOG", "onError: "+e.toString());
                    }
                });
    }

    private void setupViews() {
        imgClose=findViewById(R.id.img_properties_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtTitle=findViewById(R.id.txt_properties_title);
        recyclerView=findViewById(R.id.rv_properties_propertiesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}