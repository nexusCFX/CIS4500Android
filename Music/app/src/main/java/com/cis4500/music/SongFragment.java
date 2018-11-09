package com.cis4500.music;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;


public class SongFragment extends Fragment {


    private SongFragmentDelegate delegate;
    private List<Song> songs;

    public SongFragment() {
    }

    @SuppressWarnings("unused")
    public static SongFragment newInstance(int columnCount) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load songs from data source

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setAdapter(new SongRecyclerViewAdapter(songs, delegate));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SongFragmentDelegate) {
            delegate = (SongFragmentDelegate) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SongFragmentDelegate");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        delegate = null;
    }

    public interface SongFragmentDelegate {
        void didSelectSong(Song song);
    }
}