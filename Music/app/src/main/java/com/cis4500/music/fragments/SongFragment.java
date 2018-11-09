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


public class SongFragment extends Fragment implements SongRecyclerViewDelegate {

    private List<Song> songs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songs = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setBarTitle("Artists");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SongRecyclerViewAdapter(songs, this));
        return view;
    }

    @Override
    public void didSelectSong(Song song) {

    }
}