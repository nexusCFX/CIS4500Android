package com.cis4500.music.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cis4500.music.R;

import androidx.recyclerview.widget.RecyclerView;

public class TitleDetailImageViewHolder extends TitleImageViewHolder {
    public TextView detail;

    public TitleDetailImageViewHolder(View view) {
        super(view);
        detail = view.findViewById(R.id.detail);
    }
}