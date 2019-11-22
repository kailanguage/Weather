package com.kailang.weather.data.city;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {City.class},version = 1,exportSchema = false)
public abstract class CityDatabase extends RoomDatabase {
    private static CityDatabase INSTANCE;

    public static synchronized CityDatabase getDatabase(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),CityDatabase.class,"city").build();
        }
        return INSTANCE;
    }
    public abstract CityDao getCityDao();
}
