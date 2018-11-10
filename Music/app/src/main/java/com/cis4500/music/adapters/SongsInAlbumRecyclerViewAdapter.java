package com.cis4500.music.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cis4500.music.R;
import com.cis4500.music.models.Album;
import com.cis4500.music.models.Song;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongsInAlbumRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int TYPE_HEADER = 0;
    private static int TYPE_SONG = 1;

    private List<Song> songs;
    private SongsInAlbumRecyclerViewDelegate delegate;
    private Album album;

    public SongsInAlbumRecyclerViewAdapter(Album album, List<Song> songs, SongsInAlbumRecyclerViewDelegate delegate) {
        this.songs = songs;
        this.delegate = delegate;
        this.album = album;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_SONG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.album_header, parent, false));
        }
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (position != 0) {
            ViewHolder songHolder = (ViewHolder)holder;
            Song song = songs.get(position - 1);
            songHolder.song = song;
            songHolder.titleView.setText(song.getTitle());
            songHolder.view.setTag(song);

            // Only display valid track numbers
            if (song.getTrackNumber() < 1) {
                songHolder.trackNumberView.setText("");
            } else {
                songHolder.trackNumberView.setText(String.valueOf(song.getTrackNumber()));
            }
            songHolder.view.setOnClickListener(v -> delegate.didSelectSong(song));
        } else {
            HeaderViewHolder header = (HeaderViewHolder)holder;

            // Bind relevant data on creation since it never changes.
            header.albumView.setText(album.getTitle());
            header.artistView.setText(album.getArtist());
           // TODO header.artView.setImageBitmap(albumArt);

            //Year and genre may not be available.
            if (album.getYear() == -1) {
                header.yearView.setVisibility(View.GONE);
            } else {
                header.yearView.setText(String.valueOf(album.getYear()));
            }

            if (album.getGenre() == null || album.getGenre().isEmpty()) {
                header.genreView.setVisibility(View.GONE);
            } else {
                header.genreView.setText(album.getGenre());
            }
        }
    }

    @Override
    public int getItemCount() {
        return songs.size() + 1;
    }

    public interface SongsInAlbumRecyclerViewDelegate {
        void didSelectSong(Song song);
    }

    // ViewHolder for the header cell
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView albumView;
        public final TextView artistView;
        public final TextView genreView;
        public final TextView yearView;
        public final ImageView artView;

        public HeaderViewHolder(View view) {
            super(view);
            this.view = view;
            albumView = view.findViewById(R.id.album_header_album_textView);
            artistView = view.findViewById(R.id.album_header_artist_textView);
            genreView = view.findViewById(R.id.album_header_genre_textView);
            yearView = view.findViewById(R.id.album_header_year_textView);
            artView = view.findViewById(R.id.album_header_albumArt);
            artView.setBackgroundResource(R.drawable.rounded_rect);
            artView.setClipToOutline(true);
        }
    }

    // ViewHolder for songs
    public class ViewHolder extends RecyclerView.ViewHolder {
        public Song song;
        public final View view;
        public final TextView titleView;
        public final TextView trackNumberView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            titleView = view.findViewById(R.id.songTitleView);
            trackNumberView = view.findViewById(R.id.trackNumberView);
        }
    }
}
