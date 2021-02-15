package com.ghandour.tvshow.rommDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = TvShowRoom.class, version = 1, exportSchema = false)
@TypeConverters(DataTypeConverter.class)
public abstract class TvShowsDatabase extends RoomDatabase {
    private static TvShowsDatabase tvShowsDatabase;

    public static synchronized TvShowsDatabase getTvShowsDatabase(Context context) {
        if (tvShowsDatabase == null) {
            tvShowsDatabase = Room.databaseBuilder(context,
                    TvShowsDatabase.class, "tv_shows_db").build();
        }
        return tvShowsDatabase;
    }

    public abstract TvShowDao tvShowDao();

}
