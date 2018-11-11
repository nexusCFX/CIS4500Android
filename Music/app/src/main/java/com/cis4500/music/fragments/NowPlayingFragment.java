package com.cis4500.music.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cis4500.music.R;

import androidx.fragment.app.Fragment;

public class NowPlayingFragment extends Fragment {

    PlaybackBarDelegate delegate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ImageView imageView = view.findViewById(R.id.playbackBarTinyArt);
        view.setOnClickListener(v -> delegate.didTapPlaybackBar());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlaybackBarDelegate) {
            delegate = (PlaybackBarDelegate) context;
        } else {
            throw new ClassCastException("Container class must implement PlaybackBarDelegate");
        }
    }

    public interface PlaybackBarDelegate {
        void didTapPlaybackBar();
    }
}
