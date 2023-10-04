package com.example.newdigikala.Filter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Model.Value;
import com.example.newdigikala.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LeftFilterItemAdapter extends RecyclerView.Adapter<LeftFilterItemAdapter.LeftViewHolder> {
    Context context;
    List<Value> values;
    OnLeftItemClickListener onLeftItemClickListener;
    String parent;

    public LeftFilterItemAdapter(Context context, OnLeftItemClickListener onLeftItemClickListener) {
        this.context = context;
        this.onLeftItemClickListener = onLeftItemClickListener;
        this.values = new ArrayList<>();
    }

    public void onBindValues(List<Value> values, String parent) {
        this.values = values;
        this.parent = parent;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public LeftViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.left_item, viewGroup, false);
        return new LeftViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final LeftViewHolder leftViewHolder, @SuppressLint("RecyclerView") final int i) {

        final Value value = values.get(i);
        leftViewHolder.txtTitle.setText(value.getTitle());
        leftViewHolder.checkBox.setChecked(value.isChecked());
        leftViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value.setChecked(!value.isChecked());
                values.set(i, value);
                leftViewHolder.checkBox.setChecked(value.isChecked());
                JSONObject jsonObject=new JSONObject();
                try {
                    if(value.isChecked()){
                        jsonObject.put("parent",parent);
                        jsonObject.put("title",value.getTitle());
                    }
/*                    jsonObject.put("parent",parent);
                    jsonObject.put("title",value.getTitle());*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                onLeftItemClickListener.onLeftItemClick(values, parent,jsonObject);
            }
        });

        leftViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    //set check by user
                    value.setChecked(!value.isChecked());
                    values.set(i, value);
                    leftViewHolder.checkBox.setChecked(value.isChecked());
                    JSONObject jsonObject=new JSONObject();
                    try {
                        if(value.isChecked()){
                            jsonObject.put("parent",parent);
                            jsonObject.put("title",value.getTitle());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    onLeftItemClickListener.onLeftItemClick(values, parent,jsonObject);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class LeftViewHolder extends RecyclerView.ViewHolder {
        AppCompatCheckBox checkBox;
        TextView txtTitle;
        RelativeLayout parent;

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.check_leftItem);
            txtTitle = itemView.findViewById(R.id.txt_leftItem_tilte);
            parent = itemView.findViewById(R.id.rel_leftItem_parent);
        }
    }

    public interface OnLeftItemClickListener {
        void onLeftItemClick(List<Value> values, String parent, JSONObject jsonObject);
    }
}
