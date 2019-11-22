package com.kailang.weather.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeatherDao {
    @Insert
    void insertWeather(Weather...weather);

    @Update
    void updateWeather(Weather... weather);

    @Delete
    void deleteWeather(Weather... weather);

    @Query("DELETE FROM Weather")
    void deleteAllWeather();

    @Query("SELECT * FROM Weather ")
    LiveData<List<Weather>> getAllWeatherLive();
}
