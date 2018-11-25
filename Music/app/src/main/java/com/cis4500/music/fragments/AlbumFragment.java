package com.cis4500.music.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.R;
import com.cis4500.music.adapters.AlbumRecyclerViewAdapter;
import com.cis4500.music.adapters.AlbumRecyclerViewAdapter.AlbumRecyclerViewDelegate;
import com.cis4500.music.models.Album;
import com.cis4500.music.models.MusicDataSource;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

public class AlbumFragment extends ListFragment implements AlbumRecyclerViewDelegate {

    private List<Album> albums;
    private String artistName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String genre = getArguments().getString("genre");
        if (genre != null && !genre.isEmpty()) {
            artistName = genre;
            // TODO: Pull albums for genre
        } else {
            artistName = getArguments().getString("artistName");
            if (artistName.equals("Albums")) {
                albums = MusicDataSource.shared().getAlbums();
            } else {
                albums = MusicDataSource.shared().getAlbumsFeaturingArtist(artistName);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView.setAdapter(new AlbumRecyclerViewAdapter(albums, this));
        return view;
    }

    @Override
    public int numberOfColumns() {
        return 2;
    }

    @Override
    public void didSelectAlbum(Album album) {
        Bundle bundle = new Bundle();
        bundle.putString("albumTitle",album.getTitle());
        Navigation.findNavController(getView()).navigate(R.id.action_albumFragment_to_songsInAlbumFragment, bundle);
    }

    @Override
    public String getTitle() {
        return artistName;
    }
}
