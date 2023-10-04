package com.example.newdigikala.Comments.AddComment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Model.RatingModel;
import com.example.newdigikala.R;
import com.hedgehog.ratingbar.RatingBar;

import java.util.List;

public class AddCommentAdapter extends RecyclerView.Adapter<AddCommentAdapter.AddCommentViewHolder> {

    List<RatingModel> ratingModels;
    Context context;

    public AddCommentAdapter(List<RatingModel> ratingModels, Context context) {
        this.ratingModels = ratingModels;
        this.context = context;
    }

    @NonNull
    @Override
    public AddCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.add_comment_item,parent,false);
        return new AddCommentViewHolder(view);
    }

    public List<RatingModel> getRatingModels(){
        return ratingModels;
    }

    @Override
    public void onBindViewHolder(@NonNull AddCommentViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RatingModel ratingModel=ratingModels.get(position);
        holder.txtTitle.setText(ratingModel.getTitle());
        holder.ratingBar.setStar(3);
        ratingModel.setValue(3+"");
        holder.ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float v) {
                RatingModel ratingModelItem=ratingModels.get(position);
                ratingModelItem.setValue(v+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return ratingModels.size();
    }

    public class AddCommentViewHolder extends RecyclerView.ViewHolder{
        RatingBar ratingBar;
        TextView txtTitle;
        public AddCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_addCommentItem_title);
            ratingBar = itemView.findViewById(R.id.rating_addCommentItem);
        }
    }
}
