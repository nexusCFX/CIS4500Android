package com.cis4500.music.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cis4500.music.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GenreRecyclerViewAdapter extends RecyclerView.Adapter<GenreRecyclerViewAdapter.ViewHolder> {

    private final List<String> genres;
    private final GenreRecyclerViewDelegate delegate;

    public GenreRecyclerViewAdapter(List<String> genres, GenreRecyclerViewDelegate delegate) {
        this.genres = genres;
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String genre = genres.get(position);
        holder.title.setText(genre);
        holder.view.setOnClickListener(v -> delegate.didSelectGenre(genre));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public interface GenreRecyclerViewDelegate {
        void didSelectGenre(String genre);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView title;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.title = view.findViewById(R.id.title);
        }
    }
}
