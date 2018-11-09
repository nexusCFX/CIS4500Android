package com.cis4500.music.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cis4500.music.MainActivity;
import com.cis4500.music.R;
import com.cis4500.music.adapters.ArtistRecyclerViewAdapter;
import com.cis4500.music.adapters.ArtistRecyclerViewAdapter.ArtistRecyclerViewDelegate;
import com.cis4500.music.models.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistFragment extends ListFragment implements ArtistRecyclerViewDelegate {

    private List<Artist> artists;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artists = new ArrayList<>();
        artists.add(new Artist("Daft Punk"));
        artists.add(new Artist("ClariS"));
        artists.add(new Artist("Queen"));
        artists.add(new Artist("Rush"));
        artists.add(new Artist("Radiohead"));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView.setAdapter(new ArtistRecyclerViewAdapter(artists, this));
        return view;
    }

    @Override
    public int numberOfColumns() {
        return 1;
    }

    @Override
    public void didSelectArtist(Artist artist) {

    }

    @Override
    public String getTitle() {
        return "Artists";
    }
}
