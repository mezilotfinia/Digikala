package com.example.newdigikala.Filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Model.Product;
import com.example.newdigikala.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilterItemAdapter extends RecyclerView.Adapter<FilterItemAdapter.FilterViewHolder> {
    List<Product> products;
    OnItemClick onItemClick;
    public FilterItemAdapter(List<Product> products,OnItemClick onItemClick){
        this.products=products;
        this.onItemClick=onItemClick;
    }
    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_item,viewGroup,false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder filterViewHolder, int i) {
        final Product product=products.get(i);
        Picasso.get().load(product.getImage()).into(filterViewHolder.image);
        filterViewHolder.txtTitle.setText(product.getTitle());
        filterViewHolder.txtDesc.setText(product.getTitle());
        filterViewHolder.txtPrice.setText(product.getPrice());

        filterViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(product.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtDesc,txtPrice;
        ImageView image;
        CardView parent;
        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txt_filterItem_title);
            txtDesc=itemView.findViewById(R.id.txt_filterItem_desc);
            txtPrice=itemView.findViewById(R.id.txt_filteItem_price);
            parent=itemView.findViewById(R.id.card_filteItem_parent);
            image=itemView.findViewById(R.id.img_filterItem_image);
        }
    }
    public interface OnItemClick{
        void onClick(String id);
    }
}
