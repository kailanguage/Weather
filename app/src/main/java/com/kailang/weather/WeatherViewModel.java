package com.kailang.weather;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kailang.weather.data.city.City;
import com.kailang.weather.data.Weather;
import com.kailang.weather.data.WeatherJSON;
import com.kailang.weather.data.WeatherRepository;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherRepository = new WeatherRepository(application);
    }
    //本地的
    public LiveData<List<Weather>> getAllWeatherLive(){
        return weatherRepository.getAllWeatherLive();
    }
    //在线的
    public LiveData<WeatherJSON> getWeatherJSONLiveData(){

        return weatherRepository.getWeatherJSONLiveData();
    }
    //城市代码
    public LiveData<List<City>> getAllCitiesLive(){
        return weatherRepository.getAllCitiesLive();
    }
    //获取指代城市
    public void setWeatherWebService(String cityCode){
        weatherRepository.setWeatherWebService(cityCode);
    }
    public void insertCity(City...cities){
        weatherRepository.insertCity(cities);
    }

}
