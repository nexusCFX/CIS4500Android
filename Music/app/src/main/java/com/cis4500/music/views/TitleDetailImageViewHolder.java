package com.cis4500.music.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cis4500.music.R;

import androidx.recyclerview.widget.RecyclerView;

public class TitleDetailImageViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    public final TextView title;
    public final TextView detail;
    public final ImageView image;

    public TitleDetailImageViewHolder(View view) {
        super(view);
        this.view = view;
        title = view.findViewById(R.id.title);
        detail = view.findViewById(R.id.detail);
        image = view.findViewById(R.id.image);
    }
}