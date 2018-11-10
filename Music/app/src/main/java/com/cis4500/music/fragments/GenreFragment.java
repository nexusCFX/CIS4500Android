package com.cis4500.music.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.R;
import com.cis4500.music.adapters.GenreRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

public class GenreFragment extends ListFragment implements GenreRecyclerViewAdapter.GenreRecyclerViewDelegate {

    private List<String> genres;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        genres = new ArrayList<>();
        genres.add("Anime");
        genres.add("Classic Rock");
        genres.add("Jazz");
        genres.add("Jpop");
        genres.add("Film");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView.setAdapter(new GenreRecyclerViewAdapter(genres, this));
        return view;
    }

    @Override
    public void didSelectGenre(String genre) {
        Bundle bundle = new Bundle();
        bundle.putString("genre", genre);
        Navigation.findNavController(getView()).navigate(R.id.action_genreFragment_to_albumFragment, bundle);
    }

    @Override
    public int numberOfColumns() {
        return 1;
    }

    @Override
    public String getTitle() {
        return "Genres";
    }
}
