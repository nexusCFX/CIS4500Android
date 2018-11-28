package com.cis4500.music;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.cis4500.music.models.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MusicPlayer {
    public static final int REPEAT_NONE = 0;
    public static final int REPEAT_ALL = 1;
    public static final int REPEAT_ONE = 2;

    private static MusicPlayer instance = null;

    private MusicPlayerDelegate delegate;

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Song currentSong;
    private Song playerDataSource;
    private Song[] songsInContext;
    private Song[] orderedSongs;

    private int repeat = 0;
    private boolean isPlaying = false;
    private boolean isShuffle = false;

    // Prevent others from making instances
    private MusicPlayer() {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(mp -> {
            if (repeat == REPEAT_ONE) {
                startPlayback(currentSong);
            } else {
                if (indexOfCurrentSong() + 1 == songsInContext.length && repeat == REPEAT_NONE) {
                    pause();
                }
                next();
            }
        });
    }

    public static MusicPlayer shared() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public Song[] getSongsInContext() {
        return songsInContext;
    }

    public void setSongsInContext(Song[] songs) {
        songsInContext = songs.clone();
        orderedSongs = songs.clone();
    }

    public void setDelegate(MusicPlayerDelegate delegate) {
        this.delegate = delegate;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void setShuffle(boolean shuffle) {
        isShuffle = shuffle;
        if (isShuffle) {
            reShuffle(false);
        } else {
            songsInContext = orderedSongs.clone();
        }
    }

    public int repeatMode() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        if (repeat == REPEAT_NONE || repeat == REPEAT_ALL) {
            this.repeat = repeat;
            mediaPlayer.setLooping(false);
        } else if (repeat == REPEAT_ONE) {
            this.repeat = repeat;
            mediaPlayer.setLooping(true);
        }
    }

    public void reShuffle(boolean startPlayback) {
        if (songsInContext != null && songsInContext.length > 0) {
            List<Song> songList = Arrays.asList(songsInContext);
            Collections.shuffle(songList);
            songsInContext = songList.toArray(new Song[0]);
            if (startPlayback) {
                startPlayback(songsInContext[0]);
            }
        }
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getSongDuration() {
        return mediaPlayer.getDuration();
    }

    public void play(Song song) {
        // In case user forgets to give songs in context for upcoming queue.
        if (songsInContext == null || songsInContext.length == 0) {
            songsInContext = new Song[]{song};
        }

        // If we were already playing this
        if (song.equals(playerDataSource)) {
            mediaPlayer.start();
            isPlaying = true;
        } else {
            startPlayback(song);
        }

    }

    public void pause() {
        mediaPlayer.pause();
        isPlaying = false;
    }

    public void seek(int time) {
        mediaPlayer.seekTo(time);
    }

    public void next() {
        int index = indexOfCurrentSong();
        if (index != -1) {
            if (index + 1 < songsInContext.length) {
                currentSong = songsInContext[index + 1];
            } else {
                currentSong = songsInContext[0];
            }
            if (isPlaying) {
                startPlayback(currentSong);
            } else {
                if (delegate != null) {
                    delegate.onSongChanged(currentSong);
                }
            }
        }
    }

    public void previous() {
        if (mediaPlayer.getCurrentPosition() > 5000) {
            mediaPlayer.seekTo(0);
            return;
        }
        int index = indexOfCurrentSong();
        if (index != -1) {
            currentSong = songsInContext[Math.max(index - 1, 0)];
            if (isPlaying) {
                startPlayback(currentSong);
            } else {
                if (delegate != null) {
                    delegate.onSongChanged(currentSong);
                }
            }
        }
    }

    private void startPlayback(Song song) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(song.getDiskPath());
            playerDataSource = song;
            mediaPlayer.prepare();
            mediaPlayer.start();

            currentSong = song;

            isPlaying = true;
            if (delegate != null) {
                delegate.onSongChanged(song);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int indexOfCurrentSong() {
        int indexOfCurrentSong = -1;
        for (int i = 0; i < songsInContext.length; i++) {
            if (songsInContext[i].getDiskPath().equals(currentSong.getDiskPath())) {
                indexOfCurrentSong = i;
                break;
            }
        }
        return indexOfCurrentSong;
    }

    public interface MusicPlayerDelegate {
        void onSongChanged(Song newSong);
    }
}
