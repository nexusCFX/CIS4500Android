package com.cis4500.music.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.R;
import com.cis4500.music.adapters.LibraryRecyclerViewAdapter;
import com.cis4500.music.adapters.LibraryRecyclerViewAdapter.LibraryRecyclerViewDelegate;
import com.cis4500.music.models.Album;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

public class LibraryFragment extends ListFragment implements LibraryRecyclerViewDelegate {

    private List<Album> recentAlbums;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recentAlbums = new ArrayList<>();
        recentAlbums.add(new Album("OK Computer", "Radiohead", "", 1991, 12));
        recentAlbums.add(new Album("Fate/Kaleid", "Various Artists", "", 2012, 12));
        recentAlbums.add(new Album("Guardians of the Galaxy Original Score", "Tyler Bates", "", 2013, 12));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView.setAdapter(new LibraryRecyclerViewAdapter(recentAlbums, this));
        return view;
    }

    @Override
    public int numberOfColumns() {
        return 2;
    }

    @Override
    public String getTitle() {
        return "Library";
    }

    @Override
    public void didSelectAlbum(Album album) {
        // TODO
    }

    @Override
    public void didSelectLibraryCategory(int category) {
        if (category == LibraryRecyclerViewAdapter.CATEGORY_ALBUM) {
            Navigation.findNavController(getView()).navigate(R.id.action_libraryFragment_to_albumFragment);
        } else if (category == LibraryRecyclerViewAdapter.CATEGORY_ARTIST) {
            Navigation.findNavController(getView()).navigate(R.id.action_libraryFragment_to_artistFragment);
        } else if (category == LibraryRecyclerViewAdapter.CATEGORY_SONGS) {
            Navigation.findNavController(getView()).navigate(R.id.action_libraryFragment_to_songFragment);
        } else if (category == LibraryRecyclerViewAdapter.CATEGORY_GENRES) {
            // TODO
        }
    }
}
