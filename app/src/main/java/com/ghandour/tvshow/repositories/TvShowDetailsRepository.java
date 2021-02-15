package com.ghandour.tvshow.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.ghandour.tvshow.apis.ApiClient;
import com.ghandour.tvshow.apis.ApiService;
import com.ghandour.tvshow.responses.TvShowDetailsResponse;
import com.ghandour.tvshow.responses.TvShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailsRepository {
    public ApiService apiService;

    public TvShowDetailsRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }


    public LiveData<TvShowDetailsResponse> getTvShowDetails(String tvShowId) {
        MutableLiveData<TvShowDetailsResponse> data = new MutableLiveData<>();
        apiService.getTvShowDetails(tvShowId).enqueue(new Callback<TvShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDetailsResponse> call, @NonNull Response<TvShowDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowDetailsResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;

    }
}
