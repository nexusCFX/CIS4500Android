package com.cis4500.music.models;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

// TODO: Add smart caching and improve efficiency
public class MusicDataSource {

    private static MusicDataSource instance = null;

    private HashMap<String, Bitmap> albumArt = new HashMap<>();
    private ArrayList<Album> albums;
    private ArrayList<Artist> artists;
    private ArrayList<Song> songs;
    private ArrayList<String> genres;
    private Bitmap defaultArt;
    private Bitmap defaultArtistArt;
    private Context context;

    // Prevent class from being instantiated by others.
    private MusicDataSource() {}

    /**
     * Accessor for singleton data source instance. Creates instance if null.
     * @return The singleton data source instance.
     */
    public static MusicDataSource shared() {
        if (instance == null) {
            instance = new MusicDataSource();
        }
        return instance;
    }

    /**
     * Gets all albums. MusicDataSource must be populated.
     * @return All the albums that are stored in the MusicDataSource instance.
     */
    public ArrayList<Album> getAlbums() {
        if (albums != null) {
            return albums;
        }
        return new ArrayList<>();
    }

    public ArrayList<Album> getAlbumsOfArtist(String artistName) {
        ArrayList<Album> albumsOfArtist = new ArrayList<>(albums.size());
        for (Album album : albums) {
            if (album.getArtist().equals(artistName)) {
                albumsOfArtist.add(album);
            }
        }
        return albumsOfArtist;
    }

    public ArrayList<Album> getAlbumsFeaturingArtist(String artistName) {
        ArrayList<Album> albumsOfArtist = new ArrayList<>(albums.size());
        HashSet<String> albumNames = new HashSet<>(albums.size());
        for (Song song : songs) {
            if (song.getArtist().equals(artistName) && !albumNames.contains(song.getAlbum())) {
                for (Album album : albums) {
                    if (album.getTitle().equals(song.getAlbum())) {
                        albumsOfArtist.add(album);
                        albumNames.add(album.getTitle());
                        break;
                    }
                }
            }
        }
        return albumsOfArtist;
    }

    /**
     * Gets all artists. MusicDataSource must be populated.
     * @return All the artists that are stored in the MusicDataSource instance.
     */
    public ArrayList<Artist> getArtists() {
        if (artists != null) {
            return artists;
        }
        return new ArrayList<>();
    }

    /**
     * Gets all artists that have at least one published album. MusicDataSource must be populated.
     * @return All artists in the MusicDataSource instance where numberOfAlbums > 0.
     */
    public ArrayList<Artist> getAllAlbumArtists() {
        ArrayList<Artist> albumArtists = new ArrayList<>(artists.size());
        for (Artist artist : artists) {
            for (Album album : albums) {
                if (album.getArtist().equals(artist.getName())) {
                    albumArtists.add(artist);
                    break;
                }
            }
        }
        return albumArtists;
    }

    /**
     * Gets all songs. MusicDataSource must be populated.
     * @return All the songs that are stored in the MusicDataSource instance.
     */
    public ArrayList<Song> getSongs() {
        if (songs != null) {
            return songs;
        }
        return new ArrayList<>();
    }

    public ArrayList<String> getGenres() {
        if (genres != null) {
            return genres;
        }
        return new ArrayList<>();
    }

    public ArrayList<Song> getAllSongsWithGenre(String genre) {
        try (Cursor songCursor = MediaStoreUtils.getAllSongsWithGenre(context, genre)) {
            ArrayList<Song> songsInAlbum = new ArrayList<>(songCursor.getCount());
            while (songCursor.moveToNext()) {
                String title = "";
                String album = "";
                String artist = "";
                String diskPath = "";

                int trackNumber = -1;
                int duration = -1;
                int year = -1;

                int index = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                if (index != -1) {
                    title = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                if (index != -1) {
                    album = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                if (index != -1) {
                    artist = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                if (index != -1) {
                    diskPath = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.TRACK);
                if (index != -1) {
                    trackNumber = songCursor.getInt(index);
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                if (index != -1) {
                    duration = songCursor.getInt(index);
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.YEAR);
                if (index != -1) {
                    year = songCursor.getInt(index);
                }

                songsInAlbum.add(new Song(title, album, artist, diskPath, trackNumber, duration, year));
            }
            return songsInAlbum;
        }
    }

    /**
     * Retrieves all the songs that have a given album name.
     * @param albumTitle The name of a music album on the device.
     * @return An array of Song objects that are associated with the provided album name.
     */
    public ArrayList<Song> getAllSongsInAlbum(String albumTitle) {
        try (Cursor songCursor = MediaStoreUtils.getAllSongsInAlbum(context, albumTitle)) {
            ArrayList<Song> songsInAlbum = new ArrayList<>(songCursor.getCount());
            while (songCursor.moveToNext()) {
                String title = "";
                String album = "";
                String artist = "";
                String diskPath = "";

                int trackNumber = -1;
                int duration = -1;
                int year = -1;

                int index = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                if (index != -1) {
                    title = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                if (index != -1) {
                    album = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                if (index != -1) {
                    artist = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                if (index != -1) {
                    diskPath = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.TRACK);
                if (index != -1) {
                    trackNumber = songCursor.getInt(index);
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                if (index != -1) {
                    duration = songCursor.getInt(index);
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.YEAR);
                if (index != -1) {
                    year = songCursor.getInt(index);
                }

                songsInAlbum.add(new Song(title, album, artist, diskPath, trackNumber, duration, year));
            }
            Collections.sort(songsInAlbum, new Comparator<Song>() {
                @Override
                public int compare(Song s1, Song s2) {
                    return s1.getTrackNumber() - s2.getTrackNumber();
                }
            });
            return songsInAlbum;
        }
    }

    /**
     * Retrieves album art for a given music album.
     * @param albumName The name of the album.
     * @return An unscaled Bitmap representing the album art for the album, or a default image
     * if no album art exists.
     */
    public Bitmap getAlbumArtForAlbum(String albumName) {
        if (albumArt.containsKey(albumName)) {
            return albumArt.get(albumName);
        } else {
            return defaultArt;
        }
    }

    public Bitmap getAlbumArtForArtist(String artistName) {
        for (Album album : albums) {
            if (album.getArtist().equals(artistName)) {
                if (albumArt.containsKey(album.getTitle())) {
                    return albumArt.get(album.getTitle());
                } else {
                    return defaultArt;
                }
            }
        }
        return defaultArt;
    }

    /**
     * Populates model data for albums (with art), artists, and songs.
     * @param context Context object for using ContentResolver.
     */
    public void populateAll(Context context, Bitmap defaultArt, Bitmap defaultArtistArt) {
        this.context = context;
        this.defaultArtistArt = defaultArtistArt;
        populateAlbums(context, true);
        populateArtists(context);
        populateSongs(context);
        populateGenres(context);
    }

    /**
     * Populates the album data which can be retrieved afterward using getAlbums()
     * @param context Context object for using ContentResolver.
     * @param withAlbumArt If true, store album art as Bitmap. If false, do nothing.
     */
    public void populateAlbums(Context context, boolean withAlbumArt) {
        this.context = context;
        try (Cursor albumCursor = MediaStoreUtils.getAllAlbums(context)) {
            HashMap<String, Integer> albumPositionMap = new HashMap<>();
            albums = new ArrayList<>(albumCursor.getCount());
            while (albumCursor.moveToNext()) {
                String title = "";
                String artist = "";
                String art = "";
                int numberOfSongs = 0;
                int index = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
                if (index != -1) {
                    title = (albumCursor.getString(index));
                }

                index = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
                if (index != -1) {
                    artist = (albumCursor.getString(index));
                }

                index = albumCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);
                if (index != -1) {
                    numberOfSongs = Integer.valueOf((albumCursor.getString(index)));
                }
                if (albumPositionMap.containsKey(title)) {
                    albums.get(albumPositionMap.get(title)).setArtist("Various Artists");
                } else {
                    albumPositionMap.put(title, albums.size());
                    albums.add(new Album(title, artist, "", -1, numberOfSongs));

                    if (withAlbumArt) {
                        index = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                        if (index != -1) {
                            art = (albumCursor.getString(index));
                        }

                        if (title != null && art != null && !title.equals("") && !art.equals("")) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                            Bitmap albumCover = BitmapFactory.decodeFile(art);
                            albumArt.put(title, albumCover);
                        }
                    }
                }
            }
        }
    }

    /**
     * Populates the artist data which can be retrieved afterward using getArtists()
     * @param context Context object for using ContentResolver.
     */
    public void populateArtists(Context context) {
        this.context = context;
        try (Cursor artistCursor = MediaStoreUtils.getAllArtists(context)) {
            artists = new ArrayList<>(artistCursor.getCount());
            while (artistCursor.moveToNext()) {
                String name = "";
                int numberOfAlbums = 0;

                int index = artistCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
                if (index != -1) {
                    name = (artistCursor.getString(index));
                }

                index = artistCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
                if (index != -1) {
                    numberOfAlbums = artistCursor.getInt(index);
                }

                artists.add(new Artist(name));
            }
        }
    }

    /**
     * Populates the song data which can be retrieved afterward using getSongs()
     * @param context Context object for using ContentResolver.
     */
    public void populateSongs(Context context) {
        this.context = context;
        try (Cursor songCursor = MediaStoreUtils.getAllSongs(context)) {
            songs = new ArrayList<>(songCursor.getCount());
            while (songCursor.moveToNext()) {
                String title = "";
                String album = "";
                String artist = "";
                String diskPath = "";

                int trackNumber = 0;
                int duration = 0;
                int year = 0;

                int index = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                if (index != -1) {
                    title = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                if (index != -1) {
                    album = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                if (index != -1) {
                    artist = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                if (index != -1) {
                    diskPath = (songCursor.getString(index));
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.TRACK);
                if (index != -1) {
                    trackNumber = songCursor.getInt(index);
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                if (index != -1) {
                    duration = songCursor.getInt(index);
                }

                index = songCursor.getColumnIndex(MediaStore.Audio.Media.YEAR);
                if (index != -1) {
                    year = songCursor.getInt(index);
                }

                songs.add(new Song(title, album, artist, diskPath, trackNumber, duration, year));
            }
        }
    }

    public void populateGenres(Context context) {
        this.context = context;
        try (Cursor genreCursor = MediaStoreUtils.getAllGenres(context)) {
            genres = new ArrayList<>();
            while (genreCursor.moveToNext()) {
                String genre;
                int index = genreCursor.getColumnIndex(MediaStore.Audio.Genres.NAME);
                if (index != -1) {
                    genre = (genreCursor.getString(index));
                    genres.add(genre);
                }
            }
        }
    }
}
