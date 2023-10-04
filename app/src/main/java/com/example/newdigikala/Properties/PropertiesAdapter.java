package com.example.newdigikala.Properties;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Model.Properties;
import com.example.newdigikala.R;

import java.util.List;

public class PropertiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TITLE = 1;
    private static final int PROPERTIES = 3;
    List<Properties> propertiesList;
    Context context;

    public PropertiesAdapter(List<Properties> propertiesList, Context context) {
        this.propertiesList = propertiesList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        if (viewType==TITLE){
            View view=layoutInflater.inflate(R.layout.properties_item_title,parent,false);
            return new TitleViewHolder(view);
        }else {
            View view=layoutInflater.inflate(R.layout.properties_item_param,parent,false);
            return new PropertiesViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Properties properties=propertiesList.get(position);
        if (getItemViewType(position)==TITLE){
            ((TitleViewHolder) holder).txtTitle.setText(properties.getTitle());
        }else {
            ((PropertiesViewHolder) holder).txtKey.setText(properties.getTitle());
            if(properties.getValue().equals("YES")){
                ((PropertiesViewHolder) holder).imgHaveOrNot.setVisibility(View.VISIBLE);
                ((PropertiesViewHolder) holder).txtValue.setVisibility(View.GONE);
                ((PropertiesViewHolder) holder).imgHaveOrNot.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_done_green_24dp));
            }else if (properties.getValue().equals("NO")){
                ((PropertiesViewHolder) holder).imgHaveOrNot.setVisibility(View.VISIBLE);
                ((PropertiesViewHolder) holder).txtValue.setVisibility(View.GONE);
                ((PropertiesViewHolder) holder).imgHaveOrNot.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_close_red_24dp));
            }else {
                ((PropertiesViewHolder) holder).txtValue.setVisibility(View.VISIBLE);
                ((PropertiesViewHolder) holder).imgHaveOrNot.setVisibility(View.GONE);
                ((PropertiesViewHolder) holder).txtValue.setText(properties.getValue());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (propertiesList.get(position).getValue().equals("")) {
            return TITLE;
        } else {
            return PROPERTIES;
        }
    }

    @Override
    public int getItemCount() {
        return propertiesList.size();
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txt_propertiesItemTitle_title);
        }
    }

    public class PropertiesViewHolder extends RecyclerView.ViewHolder{
        TextView txtValue,txtKey;
        ImageView imgHaveOrNot;
        public PropertiesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtKey=itemView.findViewById(R.id.txt_propertiesItemParam_key);
            txtValue=itemView.findViewById(R.id.txt_propertiesItemParam_value);
            imgHaveOrNot=itemView.findViewById(R.id.img_propertiesItem_haveOrNot);
        }
    }

}
