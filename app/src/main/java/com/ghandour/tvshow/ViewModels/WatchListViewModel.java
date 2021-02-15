package com.ghandour.tvshow.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.ghandour.tvshow.models.TvShowDetails;
import com.ghandour.tvshow.rommDb.TvShowRoom;
import com.ghandour.tvshow.rommDb.TvShowsDatabase;

import java.util.List;

public class WatchListViewModel extends AndroidViewModel {

    private TvShowsDatabase tvShowsDatabase;

    public WatchListViewModel(@NonNull Application application) {
        super(application);
        tvShowsDatabase = TvShowsDatabase.getTvShowsDatabase(application);
    }

    public LiveData<List<TvShowRoom>> loadWatchList() {
        return tvShowsDatabase.tvShowDao().getWatchList();
    }

    public void removeTvShowFromWatchList(TvShowRoom tvShowRoom) {
        tvShowsDatabase.tvShowDao().removeFromWatchList(tvShowRoom);
    }

}
