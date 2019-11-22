package com.kailang.weather.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Weather")
public class Weather {
    @NonNull
    @PrimaryKey
    private String cityID;
    private long lastTime;
    private String jsonStr;

    public Weather(){

    }

    public Weather(String cityID, long lastTime, String jsonStr) {
        this.cityID = cityID;
        this.lastTime = lastTime;
        this.jsonStr = jsonStr;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
