package com.example.newdigikala.Properties;

import com.example.newdigikala.Model.Properties;

import java.util.List;

import io.reactivex.Single;

public interface PropertiesDataSource {
    Single<List<Properties>> getProperties();
}
