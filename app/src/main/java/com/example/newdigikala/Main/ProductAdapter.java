package com.example.newdigikala.Main;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newdigikala.Model.Product;
import com.example.newdigikala.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    List<Product> products;
    OnProductClick onProductClick;

    public ProductAdapter(Context context, List<Product> products,OnProductClick onProductClick){
        this.context=context;
        this.products=products;
        this.onProductClick=onProductClick;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product=products.get(position);
        holder.id=product.getId();
        Picasso.get().load(product.getImage()).into(holder.imageView);
        holder.txtTitle.setText(product.getTitle());
        SpannableString spannableString=new SpannableString(product.getPprice()+"تومان");
        spannableString.setSpan(new StrikethroughSpan(),0,product.getPprice().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.txtPrePrice.setText(spannableString);
        holder.txtPrice.setText(product.getPrice()+"تومان");

        holder.cardParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductClick.onClick(holder.id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        String id;
        ImageView imageView;
        CardView cardParent;
        TextView txtTitle,txtPrePrice,txtPrice;
        public ProductViewHolder( View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_product_image);
            txtTitle=itemView.findViewById(R.id.txt_product_title);
            txtPrePrice=itemView.findViewById(R.id.txt_product_prePrice);
            txtPrice=itemView.findViewById(R.id.txt_product_price);
            cardParent=itemView.findViewById(R.id.card_productItem_parent);
        }
    }

    public interface OnProductClick{
        void onClick(String id);
    }

}
