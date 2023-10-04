package com.example.newdigikala.Chart;

import com.example.newdigikala.Model.HistoryModel;

import java.util.List;

import io.reactivex.Single;

public class ChartViewModel {
    ChartRepository chartRepository=new ChartRepository();
    public Single<List<HistoryModel>> getHistory(String id){
        return chartRepository.getHistory(id);
    }
}
