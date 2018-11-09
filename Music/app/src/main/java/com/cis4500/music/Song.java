package com.cis4500.music;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    private String title;
    private String album;
    private String artist;
    private String diskPath;

    private int trackNumber = 1;
    private int duration = -1;
    private int year = -1;

    public Song(String title, String album, String artist, String diskPath, int trackNumber, int duration, int year) {
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.diskPath = diskPath;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDiskPath() {
        return diskPath;
    }

    public void setDiskPath(String diskPath) {
        this.diskPath = diskPath;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    protected Song(Parcel in) {
        title = in.readString();
        album = in.readString();
        artist = in.readString();
        diskPath = in.readString();
        trackNumber = in.readInt();
        duration = in.readInt();
        year = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(album);
        dest.writeString(artist);
        dest.writeString(diskPath);
        dest.writeInt(trackNumber);
        dest.writeInt(duration);
        dest.writeInt(year);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
