package com.example.newdigikala.Category;

import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;
import com.example.newdigikala.Model.Product;

import java.util.List;

import io.reactivex.Single;

public class CatApiService {
    ApiService apiService= ApiProvider.apiProvider();
    public Single<List<Product>> getTabItem(String cat){
        return apiService.getTabItem(cat);
    }
}
