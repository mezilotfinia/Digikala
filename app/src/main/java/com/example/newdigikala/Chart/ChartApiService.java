package com.example.newdigikala.Chart;

import com.example.newdigikala.Model.ApiProvider;
import com.example.newdigikala.Model.ApiService;
import com.example.newdigikala.Model.HistoryModel;

import java.util.List;

import io.reactivex.Single;

public class ChartApiService {
    public ApiService apiService= ApiProvider.apiProvider();
    public Single<List<HistoryModel>> getHistory(String id){
        return apiService.getHistory(id);
    }
}
