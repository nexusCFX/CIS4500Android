package com.cis4500.music;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {

    private String name;
    private int numberOfAlbums = -1;

    public Artist(String name, int numberOfAlbums) {
        this.name = name;
        this.numberOfAlbums = numberOfAlbums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfAlbums() {
        return numberOfAlbums;
    }

    public void setNumberOfAlbums(int numberOfAlbums) {
        this.numberOfAlbums = numberOfAlbums;
    }

    protected Artist(Parcel in) {
        name = in.readString();
        numberOfAlbums = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(numberOfAlbums);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
