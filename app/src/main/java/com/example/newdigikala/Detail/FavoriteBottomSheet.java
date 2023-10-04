package com.example.newdigikala.Detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newdigikala.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

public class FavoriteBottomSheet extends BottomSheetDialogFragment {
    View view;
    ImageView image, imgAdd;
    TextView txtCount,textView;
    OnAddImageClick onAddImageClick;
    OnTextClick onTextClick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.favorite_bottomsheet, null);
        }
        String url=getArguments().getString("url");
        int count=getArguments().getInt("count");
        setupViews();
        setFavImage(url);
        setFavCount(count);
        return view;
    }

    public void setOnAddImageClick(OnAddImageClick onAddImageClick) {
        this.onAddImageClick = onAddImageClick;
    }

    public void setOnTextClick(OnTextClick onTextClick) {
        this.onTextClick = onTextClick;
    }

    private void setFavCount(int count) {
        txtCount.setText(count+"");
    }

    public void setFavImage(String url) {
        if (!url.equals(""))
        Picasso.get().load(url).into(image);
    }

    private void setupViews() {
        textView=view.findViewById(R.id.txt_favDialog_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextClick.onClick();
            }
        });
        imgAdd = view.findViewById(R.id.img_favDialog_addFolder);
        image = view.findViewById(R.id.img_favDialog_image);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddImageClick.onClick();
                //FavoriteBottomSheet.this.dismiss();
            }
        });
        txtCount = view.findViewById(R.id.txt_favDialog_favConut);

    }
    public interface OnAddImageClick{
        void onClick();
    }
    public interface OnTextClick{
        void onClick();
    }
}
