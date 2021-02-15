package com.ghandour.tvshow.listeners;


import com.ghandour.tvshow.rommDb.TvShowRoom;

public interface WatchListListener {
    void onTvShowClicked(TvShowRoom tvShowRoom);

    void removeTvShowFromWatchList(TvShowRoom tvShowRoom, int position);
}
