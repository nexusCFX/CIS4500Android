package com.cis4500.music.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cis4500.music.R;
import com.cis4500.music.models.Album;

import java.util.List;

public class AlbumRecyclerViewAdapter extends RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder> {

    private final List<Album> albums;
    private final AlbumRecyclerViewDelegate delegate;

    public AlbumRecyclerViewAdapter(List<Album> albums, AlbumRecyclerViewDelegate delegate) {
        this.albums = albums;
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
       holder.view.setOnClickListener(v -> delegate.didSelectAlbum(albums.get(position)));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
     //   final TextView titleView;
     //   final TextView artistView;
        Album album;

        ViewHolder(View view) {
            super(view);
            this.view = view;
       //     this.titleView = view.findViewById(R.id.item_number);
      //      this.artistView = view.findViewById(R.id.content);
        }
    }

    public interface AlbumRecyclerViewDelegate {
        void didSelectAlbum(Album album);
    }
}
