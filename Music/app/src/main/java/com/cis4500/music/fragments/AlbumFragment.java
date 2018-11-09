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
import com.cis4500.music.adapters.AlbumRecyclerViewAdapter;
import com.cis4500.music.adapters.AlbumRecyclerViewAdapter.AlbumRecyclerViewDelegate;
import com.cis4500.music.models.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends ListFragment implements AlbumRecyclerViewDelegate {

    private List<Album> albums;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        albums = new ArrayList<>();
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

    }
}
