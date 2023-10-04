package com.example.newdigikala.ShippingCart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Model.Basket;
import com.example.newdigikala.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShippingAdapter extends RecyclerView.Adapter<ShippingAdapter.ShippingViewHolder> {

    Context context;
    List<Basket> basketList;
    OnBasketItemClick onBasketItemClick;
    OnDeleteItem onDeleteItem;
    OnPriceCallBack onPriceCallBack;

    public ShippingAdapter(Context context, List<Basket> basketList, OnBasketItemClick onBasketItemClick, OnDeleteItem onDeleteItem, OnPriceCallBack onPriceCallBack) {
        this.context = context;
        this.basketList = basketList;
        this.onBasketItemClick = onBasketItemClick;
        this.onDeleteItem = onDeleteItem;
        this.onPriceCallBack = onPriceCallBack;
    }

    @NonNull
    @Override
    public ShippingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.shipping_item,parent,false);
        return new ShippingViewHolder(view);
    }

    public void deleteRow(Basket basket){
        int index=basketList.indexOf(basket);
        basketList.remove(index);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ShippingViewHolder holder, int position) {
        Basket basket=basketList.get(position);
        Picasso.get().load(basket.getImage()).into(holder.image);
        holder.txtTitle.setText(basket.getTitle());
        holder.txtPrice.setText(basket.getPrice());
        holder.txtGuarantee.setText(basket.getGuarantee());
        holder.txtFinalPrice.setText(basket.getPrice());
        holder.productId=basket.getProductId();
        holder.basketId=basket.getBasketId();

        onPriceCallBack.onPriceBack(basket.getPrice());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBasketItemClick.onItemClick(holder.productId,holder.basketId);
            }
        });
        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteItem.onDelete(basket);
            }
        });
    }

    @Override
    public int getItemCount() {
        return basketList.size();
    }

    public class ShippingViewHolder extends RecyclerView.ViewHolder{

        String productId, basketId;
        CardView parent;
        ImageView image;
        TextView txtTitle, txtGuarantee, txtPrice, txtFinalPrice, txtDelete;

        public ShippingViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.card_shippingItem_parent);
            image = itemView.findViewById(R.id.img_shippingItem_image);
            txtTitle = itemView.findViewById(R.id.txt_shippingItem_title);
            txtGuarantee = itemView.findViewById(R.id.txt_shippingItem_guarantee);
            txtPrice = itemView.findViewById(R.id.txt_shippingItem_price);
            txtFinalPrice = itemView.findViewById(R.id.txt_shippingItem_finalPrice);
            txtDelete = itemView.findViewById(R.id.txt_shippingItem_delete);
        }
    }
    public interface OnBasketItemClick{
        void onItemClick(String productId,String basketId);
    }
    public interface OnDeleteItem{
        void onDelete(Basket basket);
    }

    public interface OnPriceCallBack{
        void onPriceBack(String price);
    }

}
