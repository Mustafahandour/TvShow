package com.ghandour.tvshow.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.ghandour.tvshow.apis.ApiClient;
import com.ghandour.tvshow.apis.ApiService;
import com.ghandour.tvshow.responses.TvShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTvShowsRepository {
    private ApiService apiService;

    public MostPopularTvShowsRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<TvShowResponse> getMostPopularTvShows(int page) {
        MutableLiveData<TvShowResponse> data = new MutableLiveData<>();
        apiService.getMostPopularTvShow(page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
