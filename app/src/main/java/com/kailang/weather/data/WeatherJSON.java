package com.kailang.weather.data;

import java.util.List;

public class WeatherJSON {

    /**
     * message : success感谢又拍云(upyun.com)提供CDN赞助
     * status : 200
     * date : 20191120
     * time : 2019-11-20 22:35:16
     * cityInfo : {"city":"天津市","citykey":"101030100","parent":"天津","updateTime":"21:47"}
     * data : {"shidu":"54%","pm25":112,"pm10":232,"quality":"轻度污染","wendu":"4","ganmao":"儿童、老年人及心脏、呼吸系统疾病患者人群应减少长时间或高强度户外锻炼","forecast":[{"date":"20","high":"高温 10℃","low":"低温 2℃","ymd":"2019-11-20","week":"星期三","sunrise":"06:58","sunset":"16:55","aqi":140,"fx":"东南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"21","high":"高温 13℃","low":"低温 4℃","ymd":"2019-11-21","week":"星期四","sunrise":"06:59","sunset":"16:54","aqi":185,"fx":"南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"22","high":"高温 14℃","low":"低温 6℃","ymd":"2019-11-22","week":"星期五","sunrise":"07:00","sunset":"16:53","aqi":185,"fx":"南风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"23","high":"高温 12℃","low":"低温 2℃","ymd":"2019-11-23","week":"星期六","sunrise":"07:01","sunset":"16:53","aqi":185,"fx":"北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"24","high":"高温 9℃","low":"低温 0℃","ymd":"2019-11-24","week":"星期日","sunrise":"07:02","sunset":"16:52","aqi":60,"fx":"东北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"25","high":"高温 9℃","low":"低温 1℃","ymd":"2019-11-25","week":"星期一","sunrise":"07:03","sunset":"16:52","aqi":50,"fx":"西北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"26","high":"高温 8℃","low":"低温 0℃","ymd":"2019-11-26","week":"星期二","sunrise":"07:04","sunset":"16:51","fx":"西南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"27","high":"高温 5℃","low":"低温 -1℃","ymd":"2019-11-27","week":"星期三","sunrise":"07:05","sunset":"16:51","fx":"西南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"28","high":"高温 5℃","low":"低温 0℃","ymd":"2019-11-28","week":"星期四","sunrise":"07:06","sunset":"16:50","fx":"北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"29","high":"高温 6℃","low":"低温 0℃","ymd":"2019-11-29","week":"星期五","sunrise":"07:07","sunset":"16:50","fx":"东风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"30","high":"高温 5℃","low":"低温 -1℃","ymd":"2019-11-30","week":"星期六","sunrise":"07:08","sunset":"16:50","fx":"东南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"01","high":"高温 5℃","low":"低温 -1℃","ymd":"2019-12-01","week":"星期日","sunrise":"07:09","sunset":"16:49","fx":"西北风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"02","high":"高温 5℃","low":"低温 -1℃","ymd":"2019-12-02","week":"星期一","sunrise":"07:10","sunset":"16:49","fx":"北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"03","high":"高温 6℃","low":"低温 -1℃","ymd":"2019-12-03","week":"星期二","sunrise":"07:11","sunset":"16:49","fx":"西风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"04","high":"高温 7℃","low":"低温 0℃","ymd":"2019-12-04","week":"星期三","sunrise":"07:12","sunset":"16:49","fx":"西风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"}],"yesterday":{"date":"19","high":"高温 7℃","low":"低温 0℃","ymd":"2019-11-19","week":"星期二","sunrise":"06:57","sunset":"16:55","aqi":62,"fx":"南风","fl":"3-4级","type":"晴","notice":"愿你拥有比阳光明媚的心情"}}
     */

    private int status;
    private String date;
    private String time;
    private CityInfoBean cityInfo;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CityInfoBean getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoBean cityInfo) {
        this.cityInfo = cityInfo;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class CityInfoBean {
        /**
         * city : 天津市
         * citykey : 101030100
         * parent : 天津
         * updateTime : 21:47
         */

        private String city;
        private String citykey;
        private String parent;
        private String updateTime;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCitykey() {
            return citykey;
        }

        public void setCitykey(String citykey) {
            this.citykey = citykey;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class DataBean {
        /**
         * shidu : 54%
         * pm25 : 112.0
         * pm10 : 232.0
         * quality : 轻度污染
         * wendu : 4
         * ganmao : 儿童、老年人及心脏、呼吸系统疾病患者人群应减少长时间或高强度户外锻炼
         * forecast : [{"date":"20","high":"高温 10℃","low":"低温 2℃","ymd":"2019-11-20","week":"星期三","sunrise":"06:58","sunset":"16:55","aqi":140,"fx":"东南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"21","high":"高温 13℃","low":"低温 4℃","ymd":"2019-11-21","week":"星期四","sunrise":"06:59","sunset":"16:54","aqi":185,"fx":"南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"22","high":"高温 14℃","low":"低温 6℃","ymd":"2019-11-22","week":"星期五","sunrise":"07:00","sunset":"16:53","aqi":185,"fx":"南风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"23","high":"高温 12℃","low":"低温 2℃","ymd":"2019-11-23","week":"星期六","sunrise":"07:01","sunset":"16:53","aqi":185,"fx":"北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"24","high":"高温 9℃","low":"低温 0℃","ymd":"2019-11-24","week":"星期日","sunrise":"07:02","sunset":"16:52","aqi":60,"fx":"东北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"25","high":"高温 9℃","low":"低温 1℃","ymd":"2019-11-25","week":"星期一","sunrise":"07:03","sunset":"16:52","aqi":50,"fx":"西北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"26","high":"高温 8℃","low":"低温 0℃","ymd":"2019-11-26","week":"星期二","sunrise":"07:04","sunset":"16:51","fx":"西南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"27","high":"高温 5℃","low":"低温 -1℃","ymd":"2019-11-27","week":"星期三","sunrise":"07:05","sunset":"16:51","fx":"西南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"28","high":"高温 5℃","low":"低温 0℃","ymd":"2019-11-28","week":"星期四","sunrise":"07:06","sunset":"16:50","fx":"北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"29","high":"高温 6℃","low":"低温 0℃","ymd":"2019-11-29","week":"星期五","sunrise":"07:07","sunset":"16:50","fx":"东风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"30","high":"高温 5℃","low":"低温 -1℃","ymd":"2019-11-30","week":"星期六","sunrise":"07:08","sunset":"16:50","fx":"东南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"01","high":"高温 5℃","low":"低温 -1℃","ymd":"2019-12-01","week":"星期日","sunrise":"07:09","sunset":"16:49","fx":"西北风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"02","high":"高温 5℃","low":"低温 -1℃","ymd":"2019-12-02","week":"星期一","sunrise":"07:10","sunset":"16:49","fx":"北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"03","high":"高温 6℃","low":"低温 -1℃","ymd":"2019-12-03","week":"星期二","sunrise":"07:11","sunset":"16:49","fx":"西风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"04","high":"高温 7℃","low":"低温 0℃","ymd":"2019-12-04","week":"星期三","sunrise":"07:12","sunset":"16:49","fx":"西风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"}]
         * yesterday : {"date":"19","high":"高温 7℃","low":"低温 0℃","ymd":"2019-11-19","week":"星期二","sunrise":"06:57","sunset":"16:55","aqi":62,"fx":"南风","fl":"3-4级","type":"晴","notice":"愿你拥有比阳光明媚的心情"}
         */

        private String shidu;
        private double pm25;
        private double pm10;
        private String quality;
        private String wendu;
        private String ganmao;
        private YesterdayBean yesterday;
        private List<ForecastBean> forecast;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public double getPm25() {
            return pm25;
        }

        public void setPm25(double pm25) {
            this.pm25 = pm25;
        }

        public double getPm10() {
            return pm10;
        }

        public void setPm10(double pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 19
             * high : 高温 7℃
             * low : 低温 0℃
             * ymd : 2019-11-19
             * week : 星期二
             * sunrise : 06:57
             * sunset : 16:55
             * aqi : 62
             * fx : 南风
             * fl : 3-4级
             * type : 晴
             * notice : 愿你拥有比阳光明媚的心情
             */

            private String date;
            private String high;
            private String low;
            private String ymd;
            private String week;
            private String sunrise;
            private String sunset;
            private int aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getYmd() {
                return ymd;
            }

            public void setYmd(String ymd) {
                this.ymd = ymd;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class ForecastBean {
            /**
             * date : 20
             * high : 高温 10℃
             * low : 低温 2℃
             * ymd : 2019-11-20
             * week : 星期三
             * sunrise : 06:58
             * sunset : 16:55
             * aqi : 140
             * fx : 东南风
             * fl : <3级
             * type : 晴
             * notice : 愿你拥有比阳光明媚的心情
             */

            private String date;
            private String high;
            private String low;
            private String ymd;
            private String week;
            private String sunrise;
            private String sunset;
            private int aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getYmd() {
                return ymd;
            }

            public void setYmd(String ymd) {
                this.ymd = ymd;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }
    }
}
