package com.cis4500.music;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.SongFragment.SongFragmentDelegate;
import com.cis4500.music.models.Song;

import java.util.List;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder> {

    private final List<Song> songs;
    private final SongFragmentDelegate actionDelegate;

    public SongRecyclerViewAdapter(List<Song> songs, SongFragment.SongFragmentDelegate actionDelegate) {
        this.songs = songs;
        this.actionDelegate = actionDelegate;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.song = songs.get(position);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != actionDelegate) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    actionDelegate.didSelectSong(holder.song);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Song song;
        View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }
}
