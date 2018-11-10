package com.cis4500.music.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cis4500.music.R;
import com.cis4500.music.models.Song;
import com.cis4500.music.views.TitleDetailImageViewHolder;
import com.cis4500.music.views.TitleImageViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongsInAlbumRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int TYPE_HEADER = 0;
    private static int TYPE_SONG = 1;

    private List<Song> songs;
    private SongsInAlbumRecyclerViewDelegate delegate;

    public SongsInAlbumRecyclerViewAdapter(List<Song> songs, SongsInAlbumRecyclerViewDelegate delegate) {
        this.songs = songs;
        this.delegate = delegate;
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

    }

    @Override
    public int getItemCount() {
        return songs.size();
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
        }
    }

    // ViewHolder for songs
    public class ViewHolder extends RecyclerView.ViewHolder {
        public Song song;
        public final View view;
    //    public final TextView titleView;
   //     public final TextView trackNumberView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
          //  titleView = view.findViewById(R.id.song_in_album_cell_title_textView);
          //  trackNumberView = view.findViewById(R.id.song_in_album_cell_trackNumber_textView);
        }
    }
}
