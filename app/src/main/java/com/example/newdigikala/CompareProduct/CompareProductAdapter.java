package com.example.newdigikala.CompareProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Model.Properties;
import com.example.newdigikala.R;

import java.util.List;

public class CompareProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TITLE = 1000;
    private static final int ITEM = 1003;
    Context context;
    List<Properties> originalProperties;
    List<Properties> secondProperties;

    public CompareProductAdapter(Context context, List<Properties> originalProperties, List<Properties> secondProperties) {
        this.context = context;
        this.originalProperties = originalProperties;
        this.secondProperties = secondProperties;
    }

    public void bindSecondProduct(List<Properties> secondProperties){
        originalProperties=secondProperties;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (originalProperties.get(position).getValue().equals("")){
            return TITLE;
        }else {
            return ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        if (viewType==TITLE){
            View view=layoutInflater.inflate(R.layout.properties_item_title,parent,false);
            return new TitleViewHolder(view);
        }else {
            View view=layoutInflater.inflate(R.layout.compare_item,parent,false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Properties properties = originalProperties.get(position);
        if (getItemViewType(position) == TITLE) {
            ((TitleViewHolder) holder).txtTitle.setText(properties.getTitle());
        } else {
            ((ItemViewHolder) holder).txtOriginal.setText(properties.getValue());
            ((ItemViewHolder) holder).txtOriginalTitle.setText(properties.getTitle());

            if(properties.getSecond().equals("")){
                ((ItemViewHolder) holder).txtSecond.setText("");
            }else{
                ((ItemViewHolder) holder).txtSecond.setText(properties.getSecond());
            }

        }
    }

    @Override
    public int getItemCount() {
        return originalProperties.size();
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_propertiesItemTitle_title);
        }
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView txtOriginal, txtSecond,txtOriginalTitle;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOriginal = itemView.findViewById(R.id.txt_compareItem_original);
            txtSecond = itemView.findViewById(R.id.txt_compareItem_second);
            txtOriginalTitle = itemView.findViewById(R.id.txt_compareItem_originalTitle);
        }
    }
}
