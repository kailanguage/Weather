package com.kailang.weather.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kailang.weather.data.city.City;
import com.kailang.weather.data.city.CityDao;
import com.kailang.weather.data.city.CityDatabase;

import java.util.List;

public class WeatherRepository {
    private LiveData<List<Weather>> allWeatherLive;
    private LiveData<List<City>> allCitiesLive;
    private WeatherDao weatherDao;
    private CityDao cityDao;
    private WeatherWebService weatherWebService;
    public WeatherRepository(Context context){
        WeatherDatabase weatherDatabase = WeatherDatabase.getDatabase(context);
        CityDatabase cityDatabase = CityDatabase.getDatabase(context);
        weatherDao = weatherDatabase.getWeatherDao();
        cityDao = cityDatabase.getCityDao();
        allWeatherLive=weatherDao.getAllWeatherLive();
        allCitiesLive=cityDao.getAllCityLive();
        weatherWebService = new WeatherWebService();
    }

    public void setWeatherWebService(String cityCode){
        weatherWebService.setCityCode(cityCode);
        weatherWebService.sendRequestWithOkHttpGSON();
    }


    public LiveData<WeatherJSON> getWeatherJSONLiveData(){
        return weatherWebService.getWeatherJSONMutableLiveData();
    }
    public void insertWeather(Weather...weather){
        new InsertAsyncTask(weatherDao).execute(weather);
    }

    public void updateWeather(Weather...weather){
        new UpdateAsyncTask(weatherDao).execute(weather);
    }
    public void deleteWeather(Weather...weather){
        new DeleteAsyncTask(weatherDao).execute(weather);
    }

    public LiveData<List<Weather>> getAllWeatherLive(){
        return allWeatherLive;
    }

    public LiveData<List<City>> getAllCitiesLive(){
        return allCitiesLive;
    }


    public void insertCity(City...cities){
        new InsertCityAsyncTask(cityDao).execute(cities);
    }

//    public void updateCity(City...cities){
//        new UpdateCityAsyncTask(cityDao).execute(cities);
//    }
//    public void deleteCity(City...cities){
//        new DeleteCityAsyncTask(cityDao).execute(cities);
//    }


    private static class InsertCityAsyncTask extends AsyncTask<City,Void,Void> {
        private CityDao cityDao;
        public InsertCityAsyncTask(CityDao cityDao) {
            this.cityDao = cityDao;
        }

        @Override
        protected Void doInBackground(City... cities) {
            cityDao.insertCity(cities);
            return null;
        }
    }


    private static class InsertAsyncTask extends AsyncTask<Weather,Void,Void> {
        private WeatherDao weatherDao;
        public InsertAsyncTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }

        @Override
        protected Void doInBackground(Weather... weather) {
            weatherDao.insertWeather(weather);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Weather,Void,Void> {
        private WeatherDao weatherDao;
        public UpdateAsyncTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }

        @Override
        protected Void doInBackground(Weather... weather) {
            weatherDao.updateWeather(weather);
            return null;
        }
    }
    private static class DeleteAsyncTask extends AsyncTask<Weather,Void,Void> {
        private WeatherDao weatherDao;
        public DeleteAsyncTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }

        @Override
        protected Void doInBackground(Weather... weather) {
            weatherDao.deleteWeather(weather);
            return null;
        }
    }

}
