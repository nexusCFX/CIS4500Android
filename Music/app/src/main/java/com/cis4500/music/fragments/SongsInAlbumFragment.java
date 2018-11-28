package com.cis4500.music.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.MusicPlayer;
import com.cis4500.music.adapters.SongsInAlbumRecyclerViewAdapter;
import com.cis4500.music.adapters.SongsInAlbumRecyclerViewAdapter.SongsInAlbumRecyclerViewDelegate;
import com.cis4500.music.models.Album;
import com.cis4500.music.models.MusicDataSource;
import com.cis4500.music.models.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SongsInAlbumFragment extends ListFragment implements SongsInAlbumRecyclerViewDelegate {

    private List<Song> songs;
    private Album album;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String albumTitle = getArguments().getString("albumTitle");
        album = MusicDataSource.shared().getAlbums().stream().filter(album -> album.getTitle().equals(albumTitle)).findFirst().get();
        songs  = MusicDataSource.shared().getAllSongsInAlbum(albumTitle);
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
        return album.getTitle();
    }

    @Override
    public void didSelectSong(Song song) {
        MusicPlayer.shared().play(song);
        MusicPlayer.shared().setSongsInContext(songs.toArray(new Song[0]));
    }
}
