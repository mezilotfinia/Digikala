package com.example.newdigikala.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Model.Product;
import com.example.newdigikala.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {
    private List<Product> products;
    OnItemClickListener onItemClickListener;
    Context context;
    public CatAdapter(Context context,List<Product> products,OnItemClickListener onItemClickListener){
        this.products=products;
        this.onItemClickListener=onItemClickListener;
        this.context=context;
    }
    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.tab_item,viewGroup,false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder catViewHolder, int i) {

        final Product product=products.get(i);
        catViewHolder.id=product.getId();
        Picasso.get().load(product.getImage()).into(catViewHolder.image);
        catViewHolder.txtTitle.setText(product.getTitle());
        catViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(product.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parent;
        String id;
        TextView txtTitle;
        ImageView image;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txt_tabItem_title);
            parent=itemView.findViewById(R.id.rel_tabItem_parent);
            image=itemView.findViewById(R.id.img_tabItem_image);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(String id);
    }
}
