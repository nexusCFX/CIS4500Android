package com.cis4500.music.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cis4500.music.MusicPlayer;
import com.cis4500.music.R;
import com.cis4500.music.models.MusicDataSource;
import com.cis4500.music.models.Song;

import androidx.fragment.app.Fragment;

public class NowPlayingFragment extends Fragment implements MusicPlayer.MusicPlayerDelegate {

    private PlaybackBarDelegate delegate;

    private ImageView tinyArt;
    private ImageView bigArt;
    private TextView barTitle;
    private TextView bigTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        tinyArt = view.findViewById(R.id.playbackBarTinyArt);
        bigArt = view.findViewById(R.id.artView);
        tinyArt.setClipToOutline(true);
        bigArt.setClipToOutline(true);
        tinyArt.setBackgroundResource(R.drawable.red_rounded_rect);
        bigArt.setBackgroundResource(R.drawable.red_rounded_rect);
        barTitle = view.findViewById(R.id.playbackBarTitle);
        bigTitle = view.findViewById(R.id.songTitleView);
        view.setOnClickListener(v -> delegate.didTapPlaybackBar());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MusicPlayer.shared().setDelegate(this);
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

    @Override
    public void onSongChanged(Song newSong) {
        Bitmap image = MusicDataSource.shared().getAlbumArtForAlbum(newSong.getAlbum());
        tinyArt.setImageBitmap(image);
        bigArt.setImageBitmap(image);
        bigTitle.setText(newSong.getTitle());
        barTitle.setText(newSong.getTitle());
    }

    public interface PlaybackBarDelegate {
        void didTapPlaybackBar();
    }
}
