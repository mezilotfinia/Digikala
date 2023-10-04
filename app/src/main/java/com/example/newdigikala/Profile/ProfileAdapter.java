package com.example.newdigikala.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdigikala.Model.Profile;
import com.example.newdigikala.R;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>{

    List<Profile> profiles;
    OnProfileItemClick onProfileItemClick;
    Context context;

    public ProfileAdapter(Context context,List<Profile> profiles,OnProfileItemClick onProfileItemClick){
        this.profiles=profiles;
        this.onProfileItemClick=onProfileItemClick;
        this.context=context;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item,parent,false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        final Profile profile=profiles.get(position);
        holder.txtTitle.setText(profile.getTitle());
        holder.icon.setImageDrawable(ContextCompat.getDrawable(context,profile.getIcon()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileItemClick.onClick(profile.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parent;
        TextView txtTitle;
        ImageView icon;
        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.rel_profileItem_parent);
            txtTitle=itemView.findViewById(R.id.txt_profileItem_title);
            icon=itemView.findViewById(R.id.img_profileItem_icon);
        }
    }
    public interface OnProfileItemClick{
        void onClick(String title);
    }
}
