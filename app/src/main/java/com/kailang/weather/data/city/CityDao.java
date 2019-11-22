package com.kailang.weather.data.city;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CityDao {
    @Insert
    void insertCity(City... city);

    @Update
    void updateCity(City... city);

    @Delete
    void deleteCity(City... city);

    @Query("DELETE FROM City")
    void deleteAllCity();

    @Query("SELECT * FROM City ")
    LiveData<List<City>> getAllCityLive();
}
