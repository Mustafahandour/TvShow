package com.ghandour.tvshow.rommDb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "tvShows")
public class TvShowRoom implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("network")
    @Expose
    private String network;


    @SerializedName("image_path")
    @Expose
    private String imagePath;


    @SerializedName("episode_name")
    @Expose
    private List<String> episodeName;
    @SerializedName("episode")
    @Expose
    private List<String>  episodeNumber;

    @SerializedName("air_date")
    @Expose
    private List<String> airDate;


    public TvShowRoom(Integer id, String name, String startDate, String country, String status, String network, String imagePath, List<String> episodeName, List<String> episodeNumber, List<String> airDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.country = country;
        this.status = status;
        this.network = network;
        this.imagePath = imagePath;
        this.episodeName = episodeName;
        this.episodeNumber = episodeNumber;
        this.airDate = airDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<String> getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(List<String> episodeName) {
        this.episodeName = episodeName;
    }

    public List<String> getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(List<String> episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public List<String> getAirDate() {
        return airDate;
    }

    public void setAirDate(List<String> airDate) {
        this.airDate = airDate;
    }
}
