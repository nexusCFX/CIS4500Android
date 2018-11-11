package com.cis4500.music.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cis4500.music.R;

import androidx.fragment.app.Fragment;

public class PlaybackBarFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playback_bar, container, false);
        ImageView imageView = view.findViewById(R.id.albumArt);
        imageView.setBackgroundResource(R.drawable.red_rounded_rect);
        imageView.setClipToOutline(true);
        return view;
    }
}
