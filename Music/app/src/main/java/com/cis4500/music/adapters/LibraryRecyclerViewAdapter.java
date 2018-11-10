package com.cis4500.music.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cis4500.music.R;
import com.cis4500.music.models.Album;
import com.cis4500.music.views.TitleImageViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class LibraryRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static int TYPE_LIBRARY_CATEGORY = 0;
    private static int TYPE_ALBUM_HEADER = 1;
    private static int TYPE_ALBUM_LEFT_COLUMN = 2;
    private static int TYPE_ALBUM_RIGHT_COLUMN = 3;

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
        } else if (position == 4 || position == 5) {
            return TYPE_ALBUM_HEADER;
        } else {
            if (position % 2 == 0) {
                return TYPE_ALBUM_LEFT_COLUMN;
            } else {
                return TYPE_ALBUM_RIGHT_COLUMN;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LIBRARY_CATEGORY) {
            return new TitleImageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.album_list_item_left_column, parent, false));
        } else if (viewType == TYPE_ALBUM_HEADER) {
            return null;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface LibraryRecyclerViewDelegate {
        void didSelectAlbum(Album album);
        void didSelectLibraryCategory();
    }
}
