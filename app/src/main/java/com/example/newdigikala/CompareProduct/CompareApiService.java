package com.example.newdigikala.CompareProduct;

import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;
import com.example.newdigikala.Model.Product;
import com.example.newdigikala.Model.Properties;

import java.util.List;

import io.reactivex.Single;

public class CompareApiService implements CompareDataSource{

    ApiService apiService= ApiProvider.apiProvider();

    @Override
    public Single<List<Properties>> getProperties() {
        return apiService.getProperties();
    }

    @Override
    public Single<List<Product>> getSearchProduct(String search) {
        return apiService.getSearchedProduct(search);
    }
}
