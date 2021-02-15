
package com.ghandour.tvshow.responses;

import java.util.List;

import com.ghandour.tvshow.models.TvShow;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvShowResponse {


    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("tv_shows")
    @Expose
    private List<TvShow> tvShows = null;


    public Integer getPage() {
        return page;
    }


    public Integer getPages() {
        return pages;
    }


    public List<TvShow> getTvShows() {
        return tvShows;
    }


}
