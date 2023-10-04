package com.example.newdigikala.Main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Model.Cat;
import com.example.newdigikala.R;

import java.util.List;

public class CatsAdapter extends RecyclerView.Adapter<CatsAdapter.CatsViewHolder> {
    List<Cat> cats;
    OnCatClickListener onCatClickListener;

    public CatsAdapter(List<Cat> cats,OnCatClickListener onCatClickListener) {
        this.cats = cats;
        this.onCatClickListener=onCatClickListener;
    }

    @NonNull
    @Override
    public CatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item,parent,false);
        return new CatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatsViewHolder holder, int position) {
        Cat cat=cats.get(position);
        holder.txtTitle.setText(cat.getTitle());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCatClickListener.onClick(position,cat.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    public class CatsViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        RelativeLayout parent;
        String id;
        public CatsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txt_catItem_title);
            parent=itemView.findViewById(R.id.rel_catItem_parent);
        }
    }
    public interface OnCatClickListener{
        void onClick(int position,String title);
    }
}
