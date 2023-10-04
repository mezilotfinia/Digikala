package com.example.newdigikala.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Model.Favorite;
import com.example.newdigikala.Model.Product;
import com.example.newdigikala.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    List<Favorite> favorites;
    OnFavoriteClick onFavoriteClick;
    OnDeleteClick onDeleteClick;
    Context context;

    public FavAdapter(List<Favorite> favorites, Context context,OnFavoriteClick onFavoriteClick,OnDeleteClick onDeleteClick) {
        this.favorites = favorites;
        this.context = context;
        this.onDeleteClick=onDeleteClick;
        this.onFavoriteClick=onFavoriteClick;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_item, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        Favorite favorite = favorites.get(position);
        holder.productId=favorite.getProductId();
        holder.favId=favorite.getFavId();
        Picasso.get().load(favorite.getImage()).into(holder.image);
        holder.txtTitle.setText(favorite.getTitle());
        holder.txtDescription.setText(favorite.getTitle());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoriteClick.onFavClick(favorite.getProductId());
            }
        });

        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClick.onDelete(favorite.getFavId());
                favorites.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),favorites.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView txtTitle, txtDescription, txtDelete;
        CardView parent;
        String productId;
        String favId;
        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_favItem_image);
            txtTitle = itemView.findViewById(R.id.txt_favItem_title);
            txtDescription = itemView.findViewById(R.id.txt_favItem_desc);
            txtDelete = itemView.findViewById(R.id.txt_favitem_delete);
            parent = itemView.findViewById(R.id.card_favItem_parent);
        }
    }
    public interface OnFavoriteClick{
        void onFavClick(String productId);
    }
    public interface OnDeleteClick{
        void onDelete(String favId);
    }
}
