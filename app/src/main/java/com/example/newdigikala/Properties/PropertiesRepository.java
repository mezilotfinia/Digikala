package com.example.newdigikala.Properties;

import com.example.newdigikala.Model.Properties;

import java.util.List;

import io.reactivex.Single;

public class PropertiesRepository implements PropertiesDataSource{

    PropertiesApiService propertiesApiService=new PropertiesApiService();

    @Override
    public Single<List<Properties>> getProperties() {
        return propertiesApiService.getProperties();
    }
}
