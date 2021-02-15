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

public class SearchTvShowRepository {
    private ApiService apiService;

    public SearchTvShowRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<TvShowResponse> searchTvShow(String q, int page) {
        MutableLiveData<TvShowResponse> data = new MutableLiveData<>();
        apiService.searchTvShow(q, page).enqueue(new Callback<TvShowResponse>() {
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
