package com.cis4500.music;

import android.os.Bundle;
import android.view.View;

import com.cis4500.music.fragments.NowPlayingFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.AutoTransition;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

public class MainActivity extends AppCompatActivity implements NowPlayingFragment.PlaybackBarDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.closeButton).setOnClickListener(v -> collapseNowPlaying());
    }

    public void setBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void didTapPlaybackBar() {
        expandNowPlaying();
    }

    private void expandNowPlaying() {
        ConstraintSet set = new ConstraintSet();
        ConstraintLayout layout = findViewById(R.id.baseLayout);
        set.clone(layout);
        View image = findViewById(R.id.playbackBarTinyArt);
        View text = findViewById(R.id.playbackBarTitle);
        View playButton = findViewById(R.id.playButton);
        View nextButton = findViewById(R.id.nextButton);
        getSupportActionBar().hide();

        TransitionManager.beginDelayedTransition(layout, new ChangeBounds());
        image.animate().alpha(0).start();
        text.animate().alpha(0).start();
        playButton.animate().alpha(0).start();
        nextButton.animate().alpha(0).start();
        set.setMargin(R.id.fragmentContainer, ConstraintSet.TOP, 0);
        set.constrainHeight(R.id.playbackBarContainer, layout.getHeight() + Math.round(64*getResources().getDisplayMetrics().density));
        set.applyTo(layout);
    }

    private void collapseNowPlaying() {
        ConstraintSet set = new ConstraintSet();
        ConstraintLayout layout = findViewById(R.id.baseLayout);
        set.clone(layout);
        View image = findViewById(R.id.playbackBarTinyArt);
        View text = findViewById(R.id.playbackBarTitle);
        View playButton = findViewById(R.id.playButton);
        View nextButton = findViewById(R.id.nextButton);

        getSupportActionBar().show();
        TransitionManager.beginDelayedTransition(layout, new ChangeBounds());
        image.animate().alpha(1).start();
        text.animate().alpha(1).start();
        playButton.animate().alpha(1).start();
        nextButton.animate().alpha(1).start();
        set.setMargin(R.id.fragmentContainer, ConstraintSet.TOP, getSupportActionBar().getHeight());
        set.constrainHeight(R.id.playbackBarContainer, Math.round(64*getResources().getDisplayMetrics().density));
        set.applyTo(layout);
    }
}
