package com.cis4500.music.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cis4500.music.R;
import com.cis4500.music.adapters.SongRecyclerViewAdapter;
import com.cis4500.music.adapters.SongsInAlbumRecyclerViewAdapter;
import com.cis4500.music.adapters.SongsInAlbumRecyclerViewAdapter.SongsInAlbumRecyclerViewDelegate;
import com.cis4500.music.models.Song;

import java.util.List;

public class SongsInAlbumFragment extends ListFragment implements SongsInAlbumRecyclerViewDelegate {

    private List<Song> songs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView.setAdapter(new SongsInAlbumRecyclerViewAdapter(songs, this));
        return view;
    }

    @Override
    public int numberOfColumns() {
        return 1;
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void didSelectSong(Song song) {

    }
}
