package com.cis4500.music.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
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
    private TextView artistView;
    private TextView currentTimeView;
    private TextView durationView;
    private Song currentSong;
    private SeekBar currentPosition;
    private ImageButton smallPlay;
    private ImageButton bigPlay;
    private ImageButton repeatButton;
    private ImageButton shuffleButton;
    private MusicPlayer player = MusicPlayer.shared();

    private Handler seekBarUpdateHandler = new Handler();
    private Runnable seekBarUpdater = new Runnable() {
        @Override
        public void run() {
            try {
                currentPosition.setProgress(MusicPlayer.shared().getCurrentPosition());
            } finally {
                seekBarUpdateHandler.postDelayed(this, 100);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        tinyArt = view.findViewById(R.id.playbackBarTinyArt);
        bigArt = view.findViewById(R.id.artView);
        tinyArt.setClipToOutline(true);
        bigArt.setClipToOutline(true);
        barTitle = view.findViewById(R.id.playbackBarTitle);
        bigTitle = view.findViewById(R.id.songTitleView);
        smallPlay = view.findViewById(R.id.smallPlay);
        bigPlay = view.findViewById(R.id.bigPlay);
        artistView = view.findViewById(R.id.artistView);
        currentTimeView = view.findViewById(R.id.currentTimeInSong);
        durationView = view.findViewById(R.id.songDuration);
        currentPosition = view.findViewById(R.id.songScrubber);
        repeatButton = view.findViewById(R.id.repeatButton);
        shuffleButton = view.findViewById(R.id.shuffleButton);

        bigPlay.setOnClickListener(this::onPlayPauseTap);
        smallPlay.setOnClickListener(this::onPlayPauseTap);

        repeatButton.setOnClickListener(this::onRepeatButtonTap);
        shuffleButton.setOnClickListener(this::onShuffleButtonTap);

        currentPosition.setOnSeekBarChangeListener(seekBarChangeListener);

        view.setOnClickListener(v -> {
            if (currentSong != null) {
                delegate.didTapPlaybackBar();
            }
        });
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
    public void onDestroy() {
        super.onDestroy();
        stopSeekbarUpdates();
    }

    @Override
    public void onSongChanged(Song newSong) {
        currentSong = newSong;
        int timeInSeconds = newSong.getDuration()/1000;
        currentPosition.setMax(newSong.getDuration());
        int minutes = (timeInSeconds/60) % 60;
        int seconds = (timeInSeconds % 60);
        if (seconds < 10) {
            durationView.setText(String.valueOf(minutes) + ":0" + String.valueOf(seconds));
        } else {
            durationView.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
        }
        updateUI();
    }

    /**
     * Updates the UI based on the current state of playback. Also used for initial setup.
     */
    private void updateUI() {
        barTitle.setText(currentSong.getTitle());
        bigTitle.setText(currentSong.getTitle());
        artistView.setText(currentSong.getArtist());
        bigArt.setImageBitmap(MusicDataSource.shared().getAlbumArtForAlbum(currentSong.getAlbum()));
        tinyArt.setImageBitmap(MusicDataSource.shared().getAlbumArtForAlbum(currentSong.getAlbum()));

        if (MusicPlayer.shared().isPlaying()) {
            bigPlay.setImageResource(R.drawable.pause_large);
            smallPlay.setImageResource(R.drawable.pause);
            // Scale the album art back to its original size to indicate playback state
            bigArt.animate().translationZ(128).scaleX(1f).scaleY(1f).setInterpolator(new OvershootInterpolator()).start();
            startSeekbarUpdates();
        } else {
            bigPlay.setImageResource(R.drawable.play_large);
            smallPlay.setImageResource(R.drawable.play);
            // Scale down artwork to give a clear visual indication of pause state
            bigArt.animate().translationZ(4).scaleX(0.8f).scaleY(0.8f).setInterpolator(new OvershootInterpolator()).start();
            // Cancel the task of updating the current position TextView
            stopSeekbarUpdates();
        }
    }

    private void startSeekbarUpdates() {
        // Every second, update the TextView that displays the current position of the song playback
        seekBarUpdater.run();
    }

    private void stopSeekbarUpdates() {
        seekBarUpdateHandler.removeCallbacks(seekBarUpdater);
    }

    public void onNextSongTap(View view) {
        player.next();
        currentPosition.setProgress(0);
    }

    public void onPreviousSongTap(View view) {
        player.previous();
        currentPosition.setProgress(0);
    }

    public void onRepeatButtonTap(View view) {
        if (player.repeatMode() == MusicPlayer.REPEAT_NONE) {
            repeatButton.setColorFilter(getResources().getColor(R.color.colorAccent));
            player.setRepeat(MusicPlayer.REPEAT_ALL);
        } else if (player.repeatMode() == MusicPlayer.REPEAT_ALL) {
            repeatButton.setImageResource(R.drawable.repeat_one);
            player.setRepeat(MusicPlayer.REPEAT_ONE);
        } else {
            repeatButton.setColorFilter(Color.BLACK);
            repeatButton.setImageResource(R.drawable.repeat);
            player.setRepeat(MusicPlayer.REPEAT_NONE);
        }
    }

    public void onShuffleButtonTap(View view) {
        if (player.isShuffle()) {
            player.setShuffle(false);
            shuffleButton.setColorFilter(Color.BLACK);
        } else {
            player.setShuffle(true);
            shuffleButton.setColorFilter(getResources().getColor(R.color.colorAccent));
        }
    }

    /**
     * Called when the user taps the play/pause button.
     */
    public void onPlayPauseTap(View view) {
        if (player.isPlaying()) {
            player.pause();
            bigPlay.setImageResource(R.drawable.play_large);
            smallPlay.setImageResource(R.drawable.play);
            bigArt.animate().translationZ(4).scaleX(0.8f).scaleY(0.8f).setInterpolator(new OvershootInterpolator()).start();
        } else {
            player.play(currentSong);
            bigPlay.setImageResource(R.drawable.pause_large);
            smallPlay.setImageResource(R.drawable.pause);
            bigArt.animate().translationZ(128).scaleX(1f).scaleY(1f).setInterpolator(new OvershootInterpolator()).start();
        }
    }

    /**
     * Object that responds to changes in the state of the currentPosition SeekBar.
     */
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            int timeInSeconds = seekBar.getProgress()/1000;
            int minutes = (timeInSeconds/60) % 60;
            int seconds = (timeInSeconds % 60);
            if (seconds < 10) {
                currentTimeView.setText(String.valueOf(minutes) + ":0" + String.valueOf(seconds));
            } else {
                currentTimeView.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            bigArt.animate().translationZ(64).scaleX(0.9f).scaleY(0.9f).start();
            stopSeekbarUpdates();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            player.seek(seekBar.getProgress());
            if (player.isPlaying()) {
                bigArt.animate().translationZ(128).scaleX(1f).scaleY(1f).start();
                startSeekbarUpdates();
            } else {
                bigArt.animate().translationZ(4).scaleX(0.8f).scaleY(0.8f).setInterpolator(new OvershootInterpolator()).start();
            }
        }
    };

    public interface PlaybackBarDelegate {
        void didTapPlaybackBar();
    }
}
