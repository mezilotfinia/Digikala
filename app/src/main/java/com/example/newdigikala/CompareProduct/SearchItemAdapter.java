package com.example.newdigikala.CompareProduct;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Model.Product;
import com.example.newdigikala.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.SearchViewHolder> {

    List<Product> products;
    OnSearchedItemClickListener onSearchedItemClickListener;

    public SearchItemAdapter(OnSearchedItemClickListener onSearchedItemClickListener){
        products=new ArrayList<>();
        this.onSearchedItemClickListener=onSearchedItemClickListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        return new SearchViewHolder(view);
    }

    public void onBind(List<Product> products){
        this.products.clear();
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Product product=products.get(position);
        holder.id=product.getId();
        Picasso.get().load(product.getImage()).into(holder.image);
        holder.txtTitle.setText(product.getTitle());
        holder.txtDesc.setText(product.getTitle());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchedItemClickListener.onClickListener(product.getProperties(),product.getImage(),product.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView txtTitle,txtDesc;
        ConstraintLayout parent;
        String id;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txt_searchItem_title);
            txtDesc=itemView.findViewById(R.id.txt_searchItem_desc);
            image=itemView.findViewById(R.id.img_searchItem_image);
            parent=itemView.findViewById(R.id.constraing_searchItem_parent);
        }
    }
    public interface OnSearchedItemClickListener{
        void onClickListener(String properties,String image,String id);
    }
}
