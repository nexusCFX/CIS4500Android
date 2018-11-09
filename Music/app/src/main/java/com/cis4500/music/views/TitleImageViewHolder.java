package com.cis4500.music.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cis4500.music.R;

import androidx.recyclerview.widget.RecyclerView;

public class TitleImageViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public TextView title;
    public ImageView image;

    public TitleImageViewHolder(View view) {
        super(view);
        this.view = view;
        title = view.findViewById(R.id.title);
        image = view.findViewById(R.id.image);
        image.setImageResource(R.drawable.noartist);
        image.setBackgroundResource(R.drawable.rounded_rect);
        image.setClipToOutline(true);
    }
}
