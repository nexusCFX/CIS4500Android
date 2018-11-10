package com.cis4500.music.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.adapters.SongsInAlbumRecyclerViewAdapter;
import com.cis4500.music.adapters.SongsInAlbumRecyclerViewAdapter.SongsInAlbumRecyclerViewDelegate;
import com.cis4500.music.models.Album;
import com.cis4500.music.models.Song;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SongsInAlbumFragment extends ListFragment implements SongsInAlbumRecyclerViewDelegate {

    private List<Song> songs;
    private Album album;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String albumTitle = getArguments().getString("albumTitle");
        album = new Album(albumTitle, "Various Artists", "Anime", 2012, 12);
        songs = new ArrayList<>();
        songs.add(new Song("Exit Music", "OK Computer", "Radiohead", "", 1, -1, -1));
        songs.add(new Song("Starlog", "Fate/Kaleid", "ChouCho", "", -1, -1, -1));
        songs.add(new Song("TWO BY TWO", "Fate/Kaleid", "", "", 9, -1, -1));
        songs.add(new Song("Bohemian Rhapsody", "Queen Greatest Hits", "Queen", "", 30, -1, -1));
        songs.add(new Song("Black Tears", "Guardians of the Galaxy Original Score", "Tyler Bates", "", 287, -1, -1));
        // TODO: Get real songs for real album
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView.setAdapter(new SongsInAlbumRecyclerViewAdapter(album, songs, this));
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
