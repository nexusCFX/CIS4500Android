package com.cis4500.music.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.adapters.SongRecyclerViewAdapter;
import com.cis4500.music.adapters.SongRecyclerViewAdapter.SongRecyclerViewDelegate;
import com.cis4500.music.models.MusicDataSource;
import com.cis4500.music.models.Song;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class SongFragment extends ListFragment implements SongRecyclerViewDelegate {

    private List<Song> songs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songs = MusicDataSource.shared().getSongs();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView.setAdapter(new SongRecyclerViewAdapter(songs, this));
        return view;
    }

    @Override
    public void didSelectSong(Song song) {

    }

    @Override
    public int numberOfColumns() {
        return 1;
    }

    @Override
    public String getTitle() {
        return "Songs";
    }
}