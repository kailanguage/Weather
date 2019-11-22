package com.kailang.weather.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Weather")
public class Weather {
    @PrimaryKey
    private int cityID;
    private long lastTime;
    private String jsonStr;

    public Weather(int cityID, long lastTime, String jsonStr) {
        this.cityID = cityID;
        this.lastTime = lastTime;
        this.jsonStr = jsonStr;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
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
