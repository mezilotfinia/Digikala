package com.example.newdigikala.Chart;

import com.example.newdigikala.Model.HistoryModel;

import java.util.List;

import io.reactivex.Single;

public class ChartRepository {
    ChartApiService chartApiService=new ChartApiService();
    public Single<List<HistoryModel>> getHistory(String id){
        return chartApiService.getHistory(id);
    }
}
