package com.cis4500.music.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.R;
import com.cis4500.music.adapters.ArtistRecyclerViewAdapter;
import com.cis4500.music.adapters.ArtistRecyclerViewAdapter.ArtistRecyclerViewDelegate;
import com.cis4500.music.models.Artist;

public class ArtistFragment extends Fragment implements ArtistRecyclerViewDelegate {

    public ArtistFragment() {
    }

    @SuppressWarnings("unused")
    public static ArtistFragment newInstance(int columnCount) {
        return new ArtistFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_layout, container, false);
    }

    @Override
    public void didSelectArtist(Artist artist) {

    }
}
