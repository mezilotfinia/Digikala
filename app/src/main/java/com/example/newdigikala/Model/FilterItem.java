package com.example.newdigikala.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterItem  {

    @SerializedName("title")
    private String title;
    @SerializedName("values")
    private List<Value> value;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Value> getValues() {
        return value;
    }

    public void setValues(List<Value> values) {
        value = values;
    }

}
