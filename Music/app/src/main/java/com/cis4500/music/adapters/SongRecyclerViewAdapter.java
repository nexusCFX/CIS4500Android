package com.cis4500.music.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cis4500.music.R;
import com.cis4500.music.models.Song;

import java.util.List;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder> {

    private final List<Song> songs;
    private final SongRecyclerViewDelegate delegate;

    public SongRecyclerViewAdapter(List<Song> songs, SongRecyclerViewDelegate delegate) {
        this.songs = songs;
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.view.setOnClickListener(v -> delegate.didSelectSong(songs.get(position)));
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Song song;
        View view;
        TextView trackNumberView;
        TextView titleView;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            this.trackNumberView = view.findViewById(R.id.trackNumberView);
            this.titleView = view.findViewById(R.id.songTitleView);
        }
    }

    public interface SongRecyclerViewDelegate {
        void didSelectSong(Song song);
    }
}
