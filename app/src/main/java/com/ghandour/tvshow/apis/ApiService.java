package com.ghandour.tvshow.apis;


import com.ghandour.tvshow.responses.TvShowDetailsResponse;
import com.ghandour.tvshow.responses.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


    @GET("most-popular")
    Call<TvShowResponse> getMostPopularTvShow(@Query("page") int page);


    @GET("show-details")
    Call<TvShowDetailsResponse> getTvShowDetails(@Query("q") String tvShowId);

    @GET("show-details")
    Call<TvShowResponse> getTvShow(@Query("q") String tvShowId);

    @GET("search")
    Call<TvShowResponse> searchTvShow(@Query("q") String q,
                                      @Query("page") int page);


}
