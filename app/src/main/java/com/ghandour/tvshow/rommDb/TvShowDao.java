package com.ghandour.tvshow.rommDb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;



import java.util.List;


@Dao
public interface TvShowDao {
    @Query("SElECT * FROM tvShows ")
    LiveData<List<TvShowRoom>> getWatchList();

    @Query("SElECT id FROM tvShows  where id=:id")
    long exist(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addToWatchList(TvShowRoom tvShowRoom);

    @Delete
    void removeFromWatchList(TvShowRoom tvShowRoom);
    @Query("SElECT airDate FROM tvShows ")
    List<String> airDate();


}
