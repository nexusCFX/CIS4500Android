package com.cis4500.music.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.R;
import com.cis4500.music.models.Artist;
import com.cis4500.music.views.TitleImageViewHolder;

import java.util.List;


public class ArtistRecyclerViewAdapter extends RecyclerView.Adapter<TitleImageViewHolder> {

    private final List<Artist> artists;
    private final ArtistRecyclerViewDelegate delegate;

    public ArtistRecyclerViewAdapter(List<Artist> artists, ArtistRecyclerViewDelegate delegate) {
        this.artists = artists;
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public TitleImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TitleImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.title_image_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TitleImageViewHolder holder, int position) {
        holder.view.setOnClickListener(v -> delegate.didSelectArtist(artists.get(position)));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public interface ArtistRecyclerViewDelegate {
        void didSelectArtist(Artist artist);
    }
}
