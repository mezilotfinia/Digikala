package com.example.newdigikala.CompareProduct;

import com.example.newdigikala.Model.Product;
import com.example.newdigikala.Model.Properties;

import java.util.List;

import io.reactivex.Single;

public class CompareViewModel {
    private CompareRepository compareRepository=new CompareRepository();
    public Single<List<Properties>> getProperties(){
        return compareRepository.getProperties();
    }
    public Single<List<Product>> getSearchedProduct(String search){
        return compareRepository.getSearchProduct(search);
    }
}
