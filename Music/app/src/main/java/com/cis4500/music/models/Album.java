package com.cis4500.music.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {

    private String title;
    private String artist;
    private String genre;
    private int year = -1;
    private int numberOfSongs = -1;

    public Album(String title, String artist, String genre, int year, int numberOfSongs) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
        this.numberOfSongs = numberOfSongs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }

    protected Album(Parcel in) {
        title = in.readString();
        artist = in.readString();
        genre = in.readString();
        year = in.readInt();
        numberOfSongs = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(genre);
        dest.writeInt(year);
        dest.writeInt(numberOfSongs);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
