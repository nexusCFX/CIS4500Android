package com.cis4500.music.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.MediaStore;


public class MediaStoreUtils {

    /**
     * Retrieves all artists on the device.
     * @param context Current application / activity context for ContentResolver
     * @return A cursor object with columns "_id", "artist", and "number_of_albums"
     * with rows sorted alphabetically by arist name.
     */
    public static Cursor getAllArtists(Context context) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] columns = {
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS
        };

        return contentResolver.query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                MediaStore.Audio.Artists.ARTIST + " COLLATE NOCASE ASC"); // Sort by artist name
    }

    /**
     * Retrieves all music albums on the device.
     * @param context Current application / activity context for ContentResolver
     * @return A cursor object with columns "_id", "album", "artist", "album_art", and "numsongs"
     * with rows sorted alphabetically by album name.
     */
    public static Cursor getAllAlbums(Context context) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] columns = new String[] {
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS
        };

        return contentResolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                MediaStore.Audio.Albums.ALBUM + " COLLATE NOCASE ASC");
    }

    /**
     * Retrieves all songs on the device.
     * @param context Current application / activity context for ContentResolver
     * @return A cursor object with columns "_id", "track", "title", "album", "artist", "duration",
     * "_data" and "year" with rows sorted alphabetically by album name.
     */
    public static Cursor getAllSongs(Context context) {
        ContentResolver contentResolver = context.getContentResolver();

        final String[] columns = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TRACK, // Track number
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, // Path on disk
                MediaStore.Audio.Media.YEAR
        };

        return contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                MediaStore.Audio.Media.TITLE + " COLLATE NOCASE ASC"); // Sort by title
    }

    /**
     * Retrieves the name of each music genre type on the device.
     * @param context Current application / activity context for ContentResolver
     * @return A cursor object with column "name" sorted alphabetically.
     */
    public static Cursor getAllGenres(Context context) {
        ContentResolver contentResolver = context.getContentResolver();

        final String[] columns = {
                MediaStore.Audio.Genres.NAME
        };

        return contentResolver.query(
                MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                MediaStore.Audio.Genres.NAME + " COLLATE NOCASE ASC"); // Sort by title
    }

    public static Cursor getAllSongsWithGenre(Context context, String genre) {
        ContentResolver contentResolver = context.getContentResolver();
        final String[] genreColumns = {
                MediaStore.Audio.Genres.NAME,
                MediaStore.Audio.Genres._ID
        };

        Cursor genreCursor = contentResolver.query(
                MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI,
                genreColumns,
                "name=?",
                new String[]{genre},
                null);

        genreCursor.move(1);
        int index = genreCursor.getColumnIndex(MediaStore.Audio.Genres._ID);
        int genreID = genreCursor.getInt(index);

        Uri uri = MediaStore.Audio.Genres.Members.getContentUri("external", genreID);

        final String[] songColumns = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TRACK, // Track number
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, // Path on disk
                MediaStore.Audio.Media.YEAR
        };

        return contentResolver.query(
                uri,
                songColumns,
                null,
                null,
                MediaStore.Audio.Media.TITLE + " COLLATE NOCASE ASC"); // Sort by title
    }

    /**
     * Retrieves all the albums created by the specified artist.
     * @param context Context object for using ContentResolver.
     * @param artist The artist name as a string. Case sensitive.
     * @return A Cursor object with the columns "_id", "album", "artist", "album_art", "numsongs".
     * Row order is sorted alphabetically by album name.
     */
    public static Cursor getAllAlbumsByArtist(Context context, String artist) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] columns = new String[] {
                MediaStore.Audio.Albums._ID,
                "DISTINCT album",
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS
        };

        return contentResolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                columns,
                "artist=?",
                new String[] {artist},
                MediaStore.Audio.Albums.ALBUM + " COLLATE NOCASE ASC"); // Sort by album name
    }

    /**
     * Retrieves all the albums that feature a track from the specified artist.
     * @param context Context object for using ContentResolver.
     * @param artist The artist name as a string. Case sensitive.
     * @return A Cursor object with the columns "_id", "album", "artist", "album_art", "numsongs".
     * Row order is sorted alphabetically by album name.
     */
    public static Cursor getAllAlbumsFeaturingArtist(Context context, String artist) {
        ContentResolver contentResolver = context.getContentResolver();

        // For all songs that an artist has created, grab the unique associated album IDs
        Cursor albumCursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{"DISTINCT album_id"},
                "artist=?",
                new String[]{artist},
                null);

        Cursor[] albumCursors = new Cursor[albumCursor.getCount()];
        int i = 0;

        try {
            while (albumCursor.moveToNext()) {
                int index = albumCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                if (index != -1) {
                    String albumID = (albumCursor.getString(index));

                    String[] columns = new String[] {
                            MediaStore.Audio.Albums._ID,
                            MediaStore.Audio.Albums.ALBUM,
                            MediaStore.Audio.Albums.ARTIST,
                            MediaStore.Audio.Albums.ALBUM_ART,
                            MediaStore.Audio.Albums.NUMBER_OF_SONGS
                    };

                    albumCursors[i] = contentResolver.query(
                            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                            columns,
                            "_id=?",
                            new String[]{albumID},
                            MediaStore.Audio.Albums.ALBUM + " COLLATE NOCASE ASC");
                    i++;
                }
            }
        } finally {
            albumCursor.close();
        }

        return new MergeCursor(albumCursors);
    }

    /**
     * Retrieves all songs that are part of the specified album.
     * @param context Context object for using ContentResolver.
     * @param album The album name as a string. Case sensitive.
     * @return A cursor object with columns "_id", "track", "title", "album", "artist", "duration",
     * "_data" and "year" with rows sorted alphabetically by album name.
     */
    public static Cursor getAllSongsInAlbum(Context context, String album) {
        ContentResolver contentResolver = context.getContentResolver();

        final String[] columns = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TRACK, // Track number
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, // Path on disk
                MediaStore.Audio.Media.YEAR
        };

        return contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                columns,
                "album=?",
                new String[]{album},
                MediaStore.Audio.Media.TITLE + " COLLATE NOCASE ASC"); // Sort by title
    }

    /**
     * Retrieves all songs created by the specified artist.
     * @param context Context object for using ContentResolver.
     * @param artist The artist name as a string. Case sensitive.
     * @return A cursor object with columns "_id", "track", "title", "album", "artist", "duration",
     * "_data" and "year" with rows sorted alphabetically by album name.
     */
    public static Cursor getAllSongsForArtist(Context context, String artist) {
        ContentResolver contentResolver = context.getContentResolver();

        final String[] columns = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TRACK, // Track number
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, // Path on disk
                MediaStore.Audio.Media.YEAR
        };

        return contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                columns,
                "artist=?",
                new String[]{artist},
                MediaStore.Audio.Media.TITLE + " COLLATE NOCASE ASC"); // Sort by title
    }
}

