package com.cis4500.music.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.R;

public class LibraryFragment extends Fragment {

    public LibraryFragment() {
    }

    @SuppressWarnings("unused")
    public static LibraryFragment newInstance(int columnCount) {
        return new LibraryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library_temp, container, false);
        view.findViewById(R.id.albumBtn).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_libraryFragment_to_albumFragment));
        view.findViewById(R.id.songBtn).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_libraryFragment_to_songFragment));
        view.findViewById(R.id.artistBtn).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_libraryFragment_to_artistFragment));
        return view;
    }
}
