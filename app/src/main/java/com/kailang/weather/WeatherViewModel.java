package com.kailang.weather;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.kailang.weather.data.city.City;
import com.kailang.weather.data.Weather;
import com.kailang.weather.data.WeatherJSON;
import com.kailang.weather.data.WeatherRepository;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    private MutableLiveData<String> cityCode=new MutableLiveData<>();
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
    public void insertWeather(Weather...weathers){
        weatherRepository.insertWeather(weathers);
    }

    public void updateWeather(Weather...weathers){
        weatherRepository.updateWeather(weathers);
    }

    public void insertCity(City...cities){
        weatherRepository.insertCity(cities);
    }

    public String getCityCode() {
        return cityCode.getValue();
    }

    public void setCityCode(String cityCode) {
        this.cityCode.setValue(cityCode);
    }

    private LiveData<String>  cityCodeLive= Transformations.map(cityCode, new Function<String, String>() {
        @Override
        public String apply(String input) {
            return input;
        }
    });

    public LiveData<String> getCityCodeLive(){
        return cityCodeLive;
    }
}
