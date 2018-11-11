package com.cis4500.music;

import android.os.Bundle;

import com.cis4500.music.fragments.PlaybackBarFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

public class MainActivity extends AppCompatActivity implements PlaybackBarFragment.PlaybackBarDelegate {

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
        //  TransitionManager.beginDelayedTransition(layout);
        TransitionManager.beginDelayedTransition(layout, new ChangeBounds());
        set.constrainHeight(R.id.playbackBarContainer, layout.getHeight() + Math.round(64*getResources().getDisplayMetrics().density));
        set.applyTo(layout);
    }

    private void collapseNowPlaying() {
        ConstraintSet set = new ConstraintSet();
        ConstraintLayout layout = findViewById(R.id.baseLayout);
        set.clone(layout);
        //  TransitionManager.beginDelayedTransition(layout);
        TransitionManager.beginDelayedTransition(layout, new ChangeBounds());
        set.constrainHeight(R.id.playbackBarContainer, Math.round(64*getResources().getDisplayMetrics().density));
        set.applyTo(layout);
    }
}
