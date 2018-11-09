package com.cis4500.music.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.MainActivity;
import com.cis4500.music.R;
import com.cis4500.music.adapters.SongRecyclerViewAdapter;
import com.cis4500.music.adapters.SongRecyclerViewAdapter.SongRecyclerViewDelegate;
import com.cis4500.music.models.Song;

import java.util.ArrayList;
import java.util.List;


public class SongFragment extends ListFragment implements SongRecyclerViewDelegate {

    private List<Song> songs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songs = new ArrayList<>();
        songs.add(new Song("Exit Music", "OK Computer", "Radiohead", "", -1, -1, -1));
        songs.add(new Song("Starlog", "Fate/Kaleid", "ChouCho", "", -1, -1, -1));
        songs.add(new Song("TWO BY TWO", "Fate/Kaleid", "", "", -1, -1, -1));
        songs.add(new Song("Bohemian Rhapsody", "Queen Greatest Hits", "Queen", "", -1, -1, -1));
        songs.add(new Song("Black Tears", "Guardians of the Galaxy Original Score", "Tyler Bates", "", -1, -1, -1));
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
}