package com.example.newdigikala.Category;

import com.example.newdigikala.Model.Product;

import java.util.List;

import io.reactivex.Single;

public class CatRepository {
    CatApiService catApiService=new CatApiService();
    public Single<List<Product>> getTabItem(String cat){
        return catApiService.getTabItem(cat);
    }
}
