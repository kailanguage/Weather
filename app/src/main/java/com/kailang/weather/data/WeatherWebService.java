package com.kailang.weather.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherWebService {
    private String cityCode;
    private MutableLiveData<WeatherJSON> weatherJSONMutableLiveData = new MutableLiveData<>();
    public void sendRequestWithOkHttpGSON() {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String strUrl= "http://t.weather.sojson.com/api/weather/city/"+cityCode;
                    Request request=new Request.Builder().url(strUrl).build();
                    Response response=okHttpClient.newCall(request).execute();
                    Log.e("API_URL",strUrl);
                    String responseData=response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        WeatherJSON weatherJSON =gson.fromJson(jsonData,WeatherJSON.class);
        if(weatherJSON.getStatus()==200) {
            Log.e("weatherJSON_getStatus", weatherJSON.getStatus() + "");
            weatherJSONMutableLiveData.postValue(weatherJSON);
        }
    }

    public MutableLiveData<WeatherJSON> getWeatherJSONMutableLiveData() {
        return weatherJSONMutableLiveData;
    }

    public void setWeatherJSONMutableLiveData(MutableLiveData<WeatherJSON> weatherJSONMutableLiveData) {
        this.weatherJSONMutableLiveData = weatherJSONMutableLiveData;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
