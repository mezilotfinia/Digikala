package com.example.newdigikala.CompareProduct;

import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;
import com.example.newdigikala.Model.Product;
import com.example.newdigikala.Model.Properties;

import java.util.List;

import io.reactivex.Single;

public class CompareRepository implements CompareDataSource{

    CompareApiService compareApiService=new CompareApiService();

    @Override
    public Single<List<Properties>> getProperties() {
        return compareApiService.getProperties();
    }

    @Override
    public Single<List<Product>> getSearchProduct(String search) {
        return compareApiService.getSearchProduct(search);
    }
}
