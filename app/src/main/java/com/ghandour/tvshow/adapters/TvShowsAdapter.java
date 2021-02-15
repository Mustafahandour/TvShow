package com.ghandour.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.ghandour.tvshow.R;
import com.ghandour.tvshow.databinding.ItemContainerBinding;
import com.ghandour.tvshow.listeners.TvShowListeners;
import com.ghandour.tvshow.models.TvShow;

import java.util.List;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowHolder> {
    private List<TvShow> tvShowList;
    private LayoutInflater layoutInflater;
    private TvShowListeners tvShowListeners;

    public TvShowsAdapter(List<TvShow> tvShowList, TvShowListeners tvShowListeners) {
        this.tvShowList = tvShowList;
        this.tvShowListeners = tvShowListeners;
    }

    @NonNull
    @Override
    public TvShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerBinding itemContainerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container, parent, false);
        return new TvShowHolder(itemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowHolder holder, int position) {
        holder.bindTvShow(tvShowList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    public class TvShowHolder extends RecyclerView.ViewHolder {

        private ItemContainerBinding itemContainerBinding;

        public TvShowHolder(ItemContainerBinding itemContainerBinding) {
            super(itemContainerBinding.getRoot());
            this.itemContainerBinding = itemContainerBinding;
        }

        public void bindTvShow(TvShow tvShow) {
            itemContainerBinding.setTvShow(tvShow);
            itemContainerBinding.executePendingBindings();
            itemContainerBinding.getRoot().setOnClickListener(v -> tvShowListeners.onTvShowClicked(tvShow));
        }


    }


}
