package com.kailang.weather.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Weather.class},version = 1,exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    private static WeatherDatabase INSTANCE;

    static synchronized WeatherDatabase getDatabase(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),WeatherDatabase.class,"weather").build();
        }
        return INSTANCE;
    }
    public abstract WeatherDao getWeatherDao();

}
