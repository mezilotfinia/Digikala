package com.example.newdigikala.Properties;

import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;
import com.example.newdigikala.Model.Properties;

import java.util.List;

import io.reactivex.Single;

public class PropertiesApiService implements PropertiesDataSource{

    ApiService apiService= ApiProvider.apiProvider();

    @Override
    public Single<List<Properties>> getProperties() {
        return apiService.getProperties();
    }
}
