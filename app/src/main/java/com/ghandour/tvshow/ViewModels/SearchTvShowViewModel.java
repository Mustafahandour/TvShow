package com.ghandour.tvshow.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ghandour.tvshow.repositories.SearchTvShowRepository;
import com.ghandour.tvshow.responses.TvShowResponse;


public class SearchTvShowViewModel extends ViewModel {

    private SearchTvShowRepository searchTvShowRepository;

    public SearchTvShowViewModel() {
        searchTvShowRepository = new SearchTvShowRepository();
    }

    public LiveData<TvShowResponse> searchTvShow(String q, int page) {
        return searchTvShowRepository.searchTvShow(q, page);
    }
}
