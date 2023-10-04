package com.example.newdigikala.Filter;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Product;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;

public interface FilterDataSource {
    public Single<List<Product>> getTabItem(String cat);
    public Single<List<Product>> getSortedList(String cat,int sort);
    public Single<Message> sendFilterParam(@Body List<JSONObject> jsonObjects);
}
