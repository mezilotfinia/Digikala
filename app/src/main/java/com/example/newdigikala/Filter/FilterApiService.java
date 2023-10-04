package com.example.newdigikala.Filter;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;
import com.example.newdigikala.Model.Product;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Single;

public class FilterApiService implements FilterDataSource{
    ApiService apiService= ApiProvider.apiProvider();
    @Override
    public Single<List<Product>> getTabItem(String cat) {
        return apiService.getTabItem(cat);
    }

    @Override
    public Single<List<Product>> getSortedList(String cat, int sort) {
        return apiService.getSortedList(cat,sort);
    }
    @Override
    public Single<Message> sendFilterParam(List<JSONObject> jsonObjects) {
        return apiService.sendFilterParam(jsonObjects);
    }
}
