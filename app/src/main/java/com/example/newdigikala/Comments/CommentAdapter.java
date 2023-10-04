package com.example.newdigikala.Comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Detail.RatingAdapter;
import com.example.newdigikala.Model.Comment;
import com.example.newdigikala.Model.RatingModel;
import com.example.newdigikala.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    List<Comment> comments;
    Context context;
    List<RatingModel> ratingModels;
    OnLikeOrDislikeClick onLikeOrDislikeClick;
    String id;

    public CommentAdapter(List<Comment> comments, Context context,OnLikeOrDislikeClick onLikeOrDislikeClick) {
        this.comments = comments;
        this.context = context;
        this.ratingModels = new ArrayList<>();
        this.onLikeOrDislikeClick=onLikeOrDislikeClick;
    }

    public void changeLikeOrDislikeCount(Comment comment,String likeOrDislike){
            //indexOf search mikone peyda mikone position maro
            int position=comments.indexOf(comment);
            Comment findComment=comments.get(position);
            if (likeOrDislike.equals("like")){
                int currentLike=Integer.parseInt(findComment.getLikecount());
                currentLike++;
                findComment.setLikecount(currentLike+"");
            }else {
                int currentDislike=Integer.parseInt(findComment.getDislikecount());
                currentDislike++;
                findComment.setDislikecount(currentDislike+"");
            }
            notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment=comments.get(position);
        id=comment.getId();
        ratingModels.clear();
        String param=comment.getParam();
        try {
            JSONArray jsonArray=new JSONArray(param);
            for (int i = 0; i <jsonArray.length(); i++) {
                RatingModel ratingModel=new RatingModel();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                ratingModel.setTitle(jsonObject.getString("title"));
                ratingModel.setValue(jsonObject.getString("value"));
                ratingModels.add(ratingModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (comment.getSuggest().equals("1")){
            holder.txtIsSuggested.setVisibility(View.VISIBLE);
        }else {
            holder.txtIsSuggested.setVisibility(View.GONE);
        }
        holder.txtUsername.setText(comment.getUser());
        holder.txtPassage.setText(comment.getPassage());
        holder.txtLike.setText(comment.getLikecount());
        holder.txtDislike.setText(comment.getDislikecount());
        holder.txtPositive.setText(comment.getPositive());
        holder.txtNegative.setText(comment.getNegative());
        holder.recyclerView.setAdapter(new RatingAdapter(ratingModels));

        holder.linearLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mvvm darim pas ba interface pas midim b activity ta ba viewModel brim jolo
                onLikeOrDislikeClick.onClick(comment,"like");
            }
        });
        holder.linearDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLikeOrDislikeClick.onClick(comment,"dislike");
            }
        });

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        LinearLayout linearLike, linearDisLike;
        TextView txtIsSuggested, txtUsername, txtLike, txtDislike, txtPassage, txtPositive, txtNegative;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIsSuggested = itemView.findViewById(R.id.txt_commentItem_isSuggested);
            txtUsername = itemView.findViewById(R.id.txt_commentItem_username);
            linearLike = itemView.findViewById(R.id.linear_commentItem_like);
            linearDisLike = itemView.findViewById(R.id.linear_commentItem_dislike);
            txtLike = itemView.findViewById(R.id.txt_commentItem_like);
            txtDislike = itemView.findViewById(R.id.txt_commentItem_dislike);
            txtPassage = itemView.findViewById(R.id.txt_commentItem_description);
            txtPositive = itemView.findViewById(R.id.txt_commentItem_positiveTitle);
            txtNegative = itemView.findViewById(R.id.txt_commentItem_negative);
            recyclerView = itemView.findViewById(R.id.rv_commentItem_commentRate);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }
    public interface OnLikeOrDislikeClick{
        void onClick(Comment comment,String likeOrDislike);
    }
}
