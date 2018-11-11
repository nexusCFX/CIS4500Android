package com.cis4500.music.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cis4500.music.R;
import com.cis4500.music.models.Song;
import com.cis4500.music.views.TitleDetailImageViewHolder;
import com.cis4500.music.views.TitleImageViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<TitleImageViewHolder> {

    private final List<Song> songs;
    private final SongRecyclerViewDelegate delegate;

    private static int TYPE_NO_ARTIST = 0;
    private static int TYPE_ARTIST = 1;

    public SongRecyclerViewAdapter(List<Song> songs, SongRecyclerViewDelegate delegate) {
        this.songs = songs;
        this.delegate = delegate;
    }

    @Override
    public int getItemViewType(int position) {
        return songs.get(position).getArtist().isEmpty() ? TYPE_NO_ARTIST : TYPE_ARTIST;
    }

    @NonNull
    @Override
    public TitleImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NO_ARTIST) {
            return new TitleImageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.title_image_list_item, parent, false));
        }
        return new TitleDetailImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.title_detail_image_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TitleImageViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.view.setOnClickListener(v -> delegate.didSelectSong(song));
        holder.title.setText(song.getTitle());
        holder.image.setImageResource(R.drawable.noart);
        holder.image.setBackgroundResource(R.drawable.red_rounded_rect);
        holder.image.setClipToOutline(true);
        if (holder instanceof TitleDetailImageViewHolder) {
            ((TitleDetailImageViewHolder) holder).detail.setText(song.getArtist());
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public interface SongRecyclerViewDelegate {
        void didSelectSong(Song song);
    }
}
