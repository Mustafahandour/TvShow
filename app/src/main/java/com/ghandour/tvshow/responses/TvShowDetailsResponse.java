package com.ghandour.tvshow.responses;

import com.ghandour.tvshow.models.TvShowDetails;
import com.google.gson.annotations.SerializedName;

public class TvShowDetailsResponse {
    @SerializedName("tvShow")
    private TvShowDetails tvShowDetails;

    public TvShowDetails getTvShowDetails() {
        return tvShowDetails;
    }
}
