package com.kailang.weather.data.city;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {

    /**
     * id : 1
     * pid : 0
     * city_code : 101010100
     * city_name : 北京
     * post_code : 100000
     * area_code : 010
     * ctime : 2019-07-11 17:30:06
     */
    @PrimaryKey
    private int id;
    private int pid;
    private String city_code;
    private String city_name;
    private String post_code;
    private String area_code;
    private String ctime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
}
