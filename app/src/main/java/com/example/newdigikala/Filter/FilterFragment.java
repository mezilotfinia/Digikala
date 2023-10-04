package com.example.newdigikala.Filter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Login.Message;
import com.example.newdigikala.Model.FilterItem;
import com.example.newdigikala.Model.Value;
import com.example.newdigikala.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FilterFragment extends Fragment {
    View view;
    Button btnFilter;
    FilterViewModel viewModel=new FilterViewModel();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    List<FilterItem> filterItems;
    RecyclerView rightRecyclerview, leftRecyclerview;
    RightFilterItemAdapter rightFilterItemAdapter;
    LeftFilterItemAdapter leftFilterItemAdapter;
    List<JSONObject> jsonObjects=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.filter_fragment, container, false);
        }

        setupViews();
        convertParamsToJSON(getArguments().getString("params"));

        rightFilterItemAdapter = new RightFilterItemAdapter(getActivity(), filterItems, new RightFilterItemAdapter.OnFilterItemClick() {
            @Override
            public void onItemClick(List<Value> values, String title) {
                leftFilterItemAdapter.onBindValues(values, title);
            }
        });

        leftFilterItemAdapter = new LeftFilterItemAdapter(getActivity(), new LeftFilterItemAdapter.OnLeftItemClickListener() {
            @Override
            public void onLeftItemClick(List<Value> values, String parent,JSONObject jsonObject) {
                jsonObjects.add(jsonObject);
                rightFilterItemAdapter.onBindCount(values, parent);
            }
        });
        rightRecyclerview.setAdapter(rightFilterItemAdapter);
        leftFilterItemAdapter.onBindValues(filterItems.get(0).getValues(), filterItems.get(0).getTitle());
        leftRecyclerview.setAdapter(leftFilterItemAdapter);

        return view;
    }

    private void setupViews() {
        filterItems = new ArrayList<>();
        btnFilter = view.findViewById(R.id.btn_filterFragment_filter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.sendFilterParam(jsonObjects).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Message>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(Message message) {
                                Log.i("LOG", "onSuccess: "+message.getStatus());
                                FilterActivity filterActivity= (FilterActivity) getActivity();
                                filterActivity.filterProducts(jsonObjects);
                                getActivity().getSupportFragmentManager().beginTransaction().remove(FilterFragment.this).commit();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("LOG", "onError: "+e.toString());
                            }
                        });
            }
        });
        rightRecyclerview = view.findViewById(R.id.rv_filterFragment_right);
        rightRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        leftRecyclerview = view.findViewById(R.id.rv_filterFragment_left);
        leftRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void convertParamsToJSON(String param) {
        try {
            JSONArray jsonArray = new JSONArray(param);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FilterItem filterItem = new FilterItem();
                filterItem.setTitle(jsonObject.getString("title"));
                List<Value> myValues = new ArrayList<>();

                JSONArray valuesArray = jsonObject.getJSONArray("values");
                for (int j = 0; j < valuesArray.length(); j++) {
                    JSONObject valueObject = valuesArray.getJSONObject(j);
                    String value = valueObject.getString("title");
                    Value myValue = new Value();
                    myValue.setTitle(value);
                    myValue.setChecked(false);
                    myValues.add(myValue);
                }
                filterItem.setValues(myValues);
                filterItems.add(filterItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}

