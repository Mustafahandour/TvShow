package com.ghandour.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ghandour.tvshow.R;
import com.ghandour.tvshow.databinding.ItemContainerEpisodesBinding;
import com.ghandour.tvshow.models.Episode;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder> {
    List<Episode> episodeList;
    private LayoutInflater layoutInflater;

    public EpisodesAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public EpisodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerEpisodesBinding itemContainerEpisodesBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_episodes,
                parent, false);
        return new EpisodesViewHolder(itemContainerEpisodesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesViewHolder holder, int position) {
        holder.bindEpisode(episodeList.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public class EpisodesViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerEpisodesBinding itemContainerEpisodesBinding;

        public EpisodesViewHolder(ItemContainerEpisodesBinding itemContainerEpisodesBinding) {
            super(itemContainerEpisodesBinding.getRoot());
            this.itemContainerEpisodesBinding = itemContainerEpisodesBinding;
        }

        public void bindEpisode(Episode episode) {
            String title = "s";
            String season = String.valueOf(episode.getSeason());
            if (season.length() == 1) {
                season = "0".concat(season);
            }
            String episodeNumber = String.valueOf(episode.getEpisode());
            if (episodeNumber.length() == 1) {
                episodeNumber = "0".concat(episodeNumber);
            }
            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(season).concat(episodeNumber);
            itemContainerEpisodesBinding.setTitle(title);
            itemContainerEpisodesBinding.setName(episode.getName());
            itemContainerEpisodesBinding.setAirDate(episode.getAirDate());

        }
    }
}
