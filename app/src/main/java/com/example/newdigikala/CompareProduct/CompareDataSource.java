package com.example.newdigikala.CompareProduct;

import com.example.newdigikala.Model.Product;
import com.example.newdigikala.Model.Properties;

import java.util.List;

import io.reactivex.Single;

public interface CompareDataSource {
    Single<List<Properties>> getProperties();
    Single<List<Product>> getSearchProduct(String search);
}