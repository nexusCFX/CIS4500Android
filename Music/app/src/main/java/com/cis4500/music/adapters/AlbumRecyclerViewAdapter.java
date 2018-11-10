package com.cis4500.music.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cis4500.music.R;
import com.cis4500.music.models.Album;
import com.cis4500.music.views.TitleDetailImageViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class AlbumRecyclerViewAdapter extends RecyclerView.Adapter<TitleDetailImageViewHolder> {

    private final List<Album> albums;
    private final AlbumRecyclerViewDelegate delegate;

    public AlbumRecyclerViewAdapter(List<Album> albums, AlbumRecyclerViewDelegate delegate) {
        this.albums = albums;
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public TitleDetailImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TitleDetailImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TitleDetailImageViewHolder holder, int position) {
        Album album = albums.get(position);
        StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams)holder.view.getLayoutParams();
        int density = Math.round(holder.view.getContext().getResources().getDisplayMetrics().density);
        if (position % 2 == 0) {
            p.setMargins(16*density,16*density,8*density,0);
        } else {
            p.setMargins(8*density,16*density,16*density,0);
        }
        holder.title.setText(album.getTitle());
        holder.detail.setText(album.getArtist());
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
