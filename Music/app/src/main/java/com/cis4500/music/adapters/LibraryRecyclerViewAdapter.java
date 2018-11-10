package com.cis4500.music.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cis4500.music.R;
import com.cis4500.music.models.Album;
import com.cis4500.music.views.TitleImageViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class LibraryRecyclerViewAdapter extends RecyclerView.Adapter<TitleImageViewHolder> {

    public static int CATEGORY_ARTIST = 0;
    public static int CATEGORY_ALBUM = 1;
    public static int CATEGORY_SONGS = 2;
    public static int CATEGORY_GENRES = 3;

    private static int TYPE_LIBRARY_CATEGORY = 0;
    private static int TYPE_ALBUM_HEADER = 1;
    private static int TYPE_ALBUM = 2;

    private List<Album> recentAlbums;
    private LibraryRecyclerViewDelegate delegate;

    public LibraryRecyclerViewAdapter(List<Album> recentAlbums, LibraryRecyclerViewDelegate delegate) {
        this.recentAlbums = recentAlbums;
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public TitleImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TitleImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.title_image_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TitleImageViewHolder holder, int position) {
        if (position == 0) {
            holder.title.setText("Artists");
            holder.image.setImageResource(R.drawable.category_album);
            holder.image.setBackgroundResource(R.drawable.red_rounded_rect);
        } else if (position == 1) {
            holder.title.setText("Albums");
        } else if (position == 2) {
            holder.title.setText("Songs");
        } else if (position == 3) {
            holder.title.setText("Genres");
        }
        if (position < 4) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams)holder.itemView.getLayoutParams();
            p.setFullSpan(true);
            holder.view.setOnClickListener(v -> delegate.didSelectLibraryCategory(position));
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

   /* // ViewHolder for songs
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView titleView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            titleView = view.findViewById(R.id.title);
        }
    }
    */
}
