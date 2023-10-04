package com.example.newdigikala.CompareProduct;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.newdigikala.Model.Properties;
import com.example.newdigikala.R;
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

public class CompareActivity extends AppCompatActivity {

    ImageView imgOriginal,imgSecond;
    RecyclerView recyclerView;
    String imageUrl;
    CompareViewModel viewModel=new CompareViewModel();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    CompareProductAdapter compareProductAdapter;
    List<Properties> secondList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        setupViews();
        imageUrl=getIntent().getExtras().getString("image_url");
        Picasso.get().load(imageUrl).into(imgOriginal);
        viewModel.getProperties().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Properties>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Properties> properties) {
                        compareProductAdapter=new CompareProductAdapter(CompareActivity.this,properties,secondList);
                        recyclerView.setAdapter(compareProductAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("LOG", "onError: "+e.toString());
                    }
                });
    }


    private void setupViews() {
        secondList=new ArrayList<>();
        imgOriginal=findViewById(R.id.img_compare_orginal);
        imgSecond=findViewById(R.id.img_compare_second);
        imgSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CompareSearchActivity.class);
                startActivityResult.launch(intent);
            }
        });
        recyclerView=findViewById(R.id.rv_compare_compareList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        secondList.clear();
                        Picasso.get().load(data.getExtras().getString("image")).into(imgSecond);
                        String propertiesList=data.getExtras().getString("properties");
                        try {
                            JSONArray jsonArray=new JSONArray(propertiesList);
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                Properties properties=new Properties();
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                properties.setTitle(jsonObject.getString("title"));
                                properties.setValue(jsonObject.getString("value"));
                                properties.setSecond(jsonObject.getString("second"));
                                secondList.add(properties);
                            }

                            Log.i("LOG", "onActivityResult: "+secondList.size());
                            compareProductAdapter.bindSecondProduct(secondList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}