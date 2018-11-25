package com.cis4500.music;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.cis4500.music.fragments.NowPlayingFragment;
import com.cis4500.music.models.MusicDataSource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.transition.AutoTransition;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

public class MainActivity extends AppCompatActivity implements NowPlayingFragment.PlaybackBarDelegate, ActivityCompat.OnRequestPermissionsResultCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.closeButton).setOnClickListener(v -> collapseNowPlaying());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.READ_EXTERNAL_STORAGE }, 1);
        } else {
            populateData();
        }
    }

    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            populateData();
        }
    }

    private void populateData() {
        // Populate music data source ahead of time because we need it at first load
        Bitmap albumCover = BitmapFactory.decodeResource(getResources(), R.drawable.noart);
        Bitmap artistCover = BitmapFactory.decodeResource(getResources(), R.drawable.noartist);
        MusicDataSource.shared().populateAll(getApplicationContext(), albumCover, artistCover);
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
