package com.example.newdigikala.Filter;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.Product;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Single;

public class FilterViewModel {
    private FilterRepository filterRepository=new FilterRepository();
    public Single<List<Product>> getTabItem(String cat){
        return filterRepository.getTabItem(cat);
    }
    public Single<List<Product>> getSortedItem(String cat,int sort){
        return filterRepository.getSortedList(cat, sort);
    }
    public Single<Message> sendFilterParam(List<JSONObject> jsonObjects){
        return filterRepository.sendFilterParam(jsonObjects);
    }
}
