package com.example.newdigikala.Properties;

import com.example.newdigikala.Model.Properties;

import java.util.List;

import io.reactivex.Single;

public class PropertiesViewModel {
    PropertiesRepository propertiesRepository=new PropertiesRepository();
    public Single<List<Properties>> getProperties(){
        return propertiesRepository.getProperties();
    }
}
