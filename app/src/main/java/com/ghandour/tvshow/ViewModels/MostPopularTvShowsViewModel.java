package com.ghandour.tvshow.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ghandour.tvshow.repositories.MostPopularTvShowsRepository;
import com.ghandour.tvshow.responses.TvShowResponse;


public class MostPopularTvShowsViewModel extends ViewModel {

    private MostPopularTvShowsRepository mostPopularTvShowsRepository;

    public MostPopularTvShowsViewModel() {
        mostPopularTvShowsRepository = new MostPopularTvShowsRepository();
    }

    public LiveData<TvShowResponse> getMostPopularTvShows(int page) {
        return mostPopularTvShowsRepository.getMostPopularTvShows(page);
    }
}
