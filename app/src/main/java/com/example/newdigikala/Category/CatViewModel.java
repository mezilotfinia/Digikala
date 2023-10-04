package com.example.newdigikala.Category;

import com.example.newdigikala.Model.Product;

import java.util.List;

import io.reactivex.Single;

public class CatViewModel {
    CatRepository catRepository=new CatRepository();
    public Single<List<Product>> getTabItem(String cat){
        return catRepository.getTabItem(cat);
    }
}
