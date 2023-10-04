package com.example.newdigikala.Filter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.newdigikala.R;

public class SortDialog extends DialogFragment {

    View view;
    private static final int MOST_VIEW = 1;
    private static final int MOST_SELL = 2;
    private static final int PRICE_DESC = 3;
    private static final int PRICE_ASC = 4;
    private static final int NEWEST = 5;
    private static int SITUATION = 1;

    OnDialogItemClick onDialogItemClick;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        view = LayoutInflater.from(getContext()).inflate(R.layout.filter_dialog, null);
        setupViews();
        builder.setView(view);
        return builder.create();
    }

    public void setOnDialogItemClick(OnDialogItemClick onDialogItemClick) {
        this.onDialogItemClick = onDialogItemClick;
    }

    private void setupViews() {
        LinearLayout mostView = view.findViewById(R.id.linear_filterDialog_mostView);
        LinearLayout mostSell = view.findViewById(R.id.linear_filterDialog_mostSell);
        LinearLayout priceDesc = view.findViewById(R.id.linear_filterDialog_priceDesc);
        LinearLayout priceAsc = view.findViewById(R.id.linear_filterDialog_priceAsc);
        LinearLayout newest = view.findViewById(R.id.linear_filterDialog_newest);
        RadioButton radioMostView = view.findViewById(R.id.radio_filterDialog_mostView);
        RadioButton radioMostSell = view.findViewById(R.id.radio_filterDialog_mostSell);
        RadioButton radioPriceDesc = view.findViewById(R.id.radio_filterDialog_priceDesc);
        RadioButton radioPriceAsc = view.findViewById(R.id.radio_filterDialog_priceAsc);
        RadioButton radioNewest = view.findViewById(R.id.radio_filterDialog_newest);


        switch (SITUATION){
            case MOST_VIEW:
                radioMostView.setChecked(true);
                break;
            case MOST_SELL:
                radioMostSell.setChecked(true);
                break;
            case PRICE_DESC:
                radioPriceDesc.setChecked(true);
                break;
            case PRICE_ASC:
                radioPriceAsc.setChecked(true);
                break;
            case NEWEST:
                radioNewest.setChecked(true);
                break;
        }

        mostView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogItemClick.onClick(MOST_VIEW);
                dismiss();
                SITUATION=MOST_VIEW;
            }
        });
        mostSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogItemClick.onClick(MOST_SELL);
                dismiss();
                SITUATION=MOST_SELL;
            }
        });
        priceDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogItemClick.onClick(PRICE_DESC);
                dismiss();
                SITUATION=PRICE_DESC;
            }
        });
        priceAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogItemClick.onClick(PRICE_ASC);
                dismiss();
                SITUATION=PRICE_ASC;
            }
        });
        newest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogItemClick.onClick(NEWEST);
                dismiss();
                SITUATION=NEWEST;
            }
        });


        radioMostView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SortDialog.this.dismiss();
                SITUATION=MOST_VIEW;
            }
        });

        radioMostSell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SortDialog.this.dismiss();
                SITUATION=MOST_SELL;
            }
        });

        radioPriceDesc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SortDialog.this.dismiss();
                SITUATION=PRICE_DESC;
            }
        });

        radioPriceAsc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SortDialog.this.dismiss();
                SITUATION=PRICE_ASC;
            }
        });

        radioNewest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SortDialog.this.dismiss();
                SITUATION=NEWEST;
            }
        });
    }
    public interface OnDialogItemClick{
        void onClick(int sort);
    }
}
