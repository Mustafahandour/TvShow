package com.ghandour.tvshow.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ghandour.tvshow.repositories.TvShowDetailsRepository;
import com.ghandour.tvshow.responses.TvShowDetailsResponse;
import com.ghandour.tvshow.rommDb.TvShowRoom;
import com.ghandour.tvshow.rommDb.TvShowsDatabase;


public class TvShowDetailsViewModel extends AndroidViewModel {

    private TvShowDetailsRepository tvShowDetailsRepository;
    private TvShowsDatabase tvShowsDatabase;

    public TvShowDetailsViewModel(@NonNull Application application) {
        super(application);
        tvShowDetailsRepository = new TvShowDetailsRepository();
        tvShowsDatabase = TvShowsDatabase.getTvShowsDatabase(application);
    }

    public LiveData<TvShowDetailsResponse> getTvShowDetails(String tvShowId) {
        return tvShowDetailsRepository.getTvShowDetails(tvShowId);
    }

    public void addToWatchList(TvShowRoom tvShowRoom) {
         tvShowsDatabase.tvShowDao().addToWatchList(tvShowRoom);
    }

    public void removeTvShowFromWatchList(TvShowRoom tvShowRoom) {
        tvShowsDatabase.tvShowDao().removeFromWatchList(tvShowRoom);
    }

    public long existId(long id) {
        return tvShowsDatabase.tvShowDao().exist(id);
    }
}
