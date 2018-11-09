package com.cis4500.music.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cis4500.music.R;
import com.cis4500.music.models.Album;
import com.cis4500.music.views.TitleDetailImageViewHolder;

import java.util.List;

public class AlbumRecyclerViewAdapter extends RecyclerView.Adapter<TitleDetailImageViewHolder> {

    private static int TYPE_LEFT_COLUMN = 0;
    private static int TYPE_RIGHT_COLUMN = 1;

    private final List<Album> albums;
    private final AlbumRecyclerViewDelegate delegate;

    public AlbumRecyclerViewAdapter(List<Album> albums, AlbumRecyclerViewDelegate delegate) {
        this.albums = albums;
        this.delegate = delegate;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? TYPE_LEFT_COLUMN : TYPE_RIGHT_COLUMN;
    }

    @NonNull
    @Override
    public TitleDetailImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LEFT_COLUMN) {
            return new TitleDetailImageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.album_list_item_left_column, parent, false));
        }
        return new TitleDetailImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_list_item_right_column, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TitleDetailImageViewHolder holder, int position) {
       holder.view.setOnClickListener(v -> delegate.didSelectAlbum(albums.get(position)));
       holder.image.setImageResource(R.drawable.noart);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public interface AlbumRecyclerViewDelegate {
        void didSelectAlbum(Album album);
    }
}
