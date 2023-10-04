package com.example.newdigikala.Detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.newdigikala.Model.RatingModel;
import com.example.newdigikala.R;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingViewHolder> {

    List<RatingModel> ratingModels;

    public RatingAdapter(List<RatingModel> ratingModels) {
        this.ratingModels = ratingModels;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_item,parent,false);
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        RatingModel ratingModel=ratingModels.get(position);
        holder.txtTitle.setText(ratingModel.getTitle());
        holder.progressBar.setProgress(Float.parseFloat(ratingModel.getValue()));
    }

    @Override
    public int getItemCount() {
        return ratingModels.size();
    }

    public class RatingViewHolder extends RecyclerView.ViewHolder{
        RoundCornerProgressBar progressBar;
        TextView txtTitle;
        public RatingViewHolder(View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progress_ratingItem);
            txtTitle=itemView.findViewById(R.id.txt_ratingItem_title);
        }
    }
}
