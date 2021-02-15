package com.ghandour.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.ghandour.tvshow.R;
import com.ghandour.tvshow.databinding.ItemContainerWatchListBinding;
import com.ghandour.tvshow.listeners.WatchListListener;
import com.ghandour.tvshow.rommDb.TvShowRoom;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.TvShowHolder> {
    private List<TvShowRoom> tvShowRoomList;
    private LayoutInflater layoutInflater;
    private WatchListListener watchListListener;

    public WatchListAdapter(List<TvShowRoom> tvShowRoomList, WatchListListener watchListListener) {
        this.tvShowRoomList = tvShowRoomList;
        this.watchListListener = watchListListener;
    }

    @NonNull
    @Override
    public TvShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerWatchListBinding ItemContainerWatchListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_watch_list, parent, false);
        return new TvShowHolder(ItemContainerWatchListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowHolder holder, int position) {
        holder.bindTvShow(tvShowRoomList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowRoomList.size();
    }

    public class TvShowHolder extends RecyclerView.ViewHolder {

        private ItemContainerWatchListBinding itemContainerWatchListBinding;

        public TvShowHolder(ItemContainerWatchListBinding itemContainerWatchListBinding) {
            super(itemContainerWatchListBinding.getRoot());
            this.itemContainerWatchListBinding = itemContainerWatchListBinding;
        }

        public void bindTvShow(TvShowRoom tvShowRoom) {
            itemContainerWatchListBinding.setTvShowRoom(tvShowRoom);
            itemContainerWatchListBinding.executePendingBindings();
            itemContainerWatchListBinding.getRoot().setOnClickListener(v -> watchListListener.onTvShowClicked(tvShowRoom));
            itemContainerWatchListBinding.imageDelete.setOnClickListener(v -> watchListListener.removeTvShowFromWatchList(tvShowRoom, getAdapterPosition()));
            itemContainerWatchListBinding.imageDelete.setVisibility(View.VISIBLE);
        }


    }


}
