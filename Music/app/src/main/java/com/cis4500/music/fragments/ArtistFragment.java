package com.cis4500.music.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.R;
import com.cis4500.music.adapters.ArtistRecyclerViewAdapter;
import com.cis4500.music.adapters.ArtistRecyclerViewAdapter.ArtistRecyclerViewDelegate;
import com.cis4500.music.models.Artist;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

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
        Bundle bundle = new Bundle();
        bundle.putString("artistName", artist.getName());
        Navigation.findNavController(getView()).navigate(R.id.action_artistFragment_to_albumFragment, bundle);
    }

    @Override
    public String getTitle() {
        return "Artists";
    }
}
