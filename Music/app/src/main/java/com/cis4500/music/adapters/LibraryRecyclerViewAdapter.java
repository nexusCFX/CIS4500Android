package com.cis4500.music.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cis4500.music.R;
import com.cis4500.music.models.Album;
import com.cis4500.music.models.MusicDataSource;
import com.cis4500.music.views.TitleDetailImageViewHolder;
import com.cis4500.music.views.TitleImageViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class LibraryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int CATEGORY_ARTIST = 0;
    public static int CATEGORY_ALBUM = 1;
    public static int CATEGORY_SONGS = 2;
    public static int CATEGORY_GENRES = 3;

    private static int TYPE_LIBRARY_CATEGORY = 0;
    private static int TYPE_HEADER = 1;
    private static int TYPE_ALBUM = 2;

    private List<Album> recentAlbums;
    private LibraryRecyclerViewDelegate delegate;

    public LibraryRecyclerViewAdapter(List<Album> recentAlbums, LibraryRecyclerViewDelegate delegate) {
        this.recentAlbums = recentAlbums;
        this.delegate = delegate;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 4) {
            return TYPE_LIBRARY_CATEGORY;
        } else if (position == 4) {
            return TYPE_HEADER;
        } else {
            return TYPE_ALBUM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LIBRARY_CATEGORY) {
            return new TitleImageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.title_image_list_item, parent, false));
        } else if (viewType == TYPE_HEADER) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_list_item, parent, false));
        } else {
            return new TitleDetailImageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.album_list_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams)holder.itemView.getLayoutParams();
        TitleImageViewHolder h = null;
        if (position != 4) {
            h = (TitleImageViewHolder)holder;
        }
        if (position < 4) {
            h.view.setOnClickListener(v -> delegate.didSelectLibraryCategory(position));
            p.setFullSpan(true);
        }
        if (position == CATEGORY_ARTIST) {
            h.title.setText("Artists");
            h.image.setImageResource(R.drawable.noartist);
        } else if (position == CATEGORY_ALBUM) {
            h.title.setText("Albums");
            h.image.setImageResource(R.drawable.category_album);
            h.image.setBackgroundResource(R.drawable.red_rounded_rect);
        } else if (position == CATEGORY_SONGS) {
            h.title.setText("Songs");
            h.image.setImageResource(R.drawable.category_music);
            h.image.setBackgroundResource(R.drawable.red_rounded_rect);
        } else if (position == CATEGORY_GENRES) {
            h.image.setImageResource(R.drawable.category_genre);
            h.image.setBackgroundResource(R.drawable.red_rounded_rect);
            h.title.setText("Genres");
        } else if (position == 4) {
            ((ViewHolder)holder).titleView.setText("Recently Played");
            p.setFullSpan(true);
        } else {
            int density = Math.round(((TitleDetailImageViewHolder)holder).view.getContext().getResources().getDisplayMetrics().density);
            if (position % 2 == 1) {
                p.setMargins(16*density,16*density,8*density,0);
            } else {
                p.setMargins(8*density,16*density,16*density,0);
            }

            Album album = recentAlbums.get(position - 5);
            h.title.setText(album.getTitle());
            ((TitleDetailImageViewHolder)h).detail.setText(album.getArtist());
            h.image.setImageBitmap(MusicDataSource.shared().getAlbumArtForAlbum(album.getTitle()));
            h.image.setBackgroundResource(R.drawable.red_rounded_rect);
            h.view.setOnClickListener(v -> delegate.didSelectAlbum(album));
        }
    }

    @Override
    public int getItemCount() {
        return 4 + recentAlbums.size() + Integer.min(recentAlbums.size(), 1);
    }

    public interface LibraryRecyclerViewDelegate {
        void didSelectAlbum(Album album);
        void didSelectLibraryCategory(int category);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView titleView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            titleView = view.findViewById(R.id.title);
        }
    }

}
