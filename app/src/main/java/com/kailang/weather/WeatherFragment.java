package com.kailang.weather;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kailang.weather.data.city.City;
import com.kailang.weather.data.Weather;
import com.kailang.weather.data.WeatherJSON;
import com.kailang.weather.utils.CityFileUtils;
import com.kailang.weather.utils.DateUtils;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private WeatherViewModel weatherViewModel;
    private List<Weather> weatherList;
    private Weather weather;
    private List<City> cityList;
    private RecyclerView recyclerView;
    private WeatherAdapter myAdapter;
    private WeatherJSON weatherNow;
    private LiveData<List<Weather>> allWeathersLive;
    private LiveData<WeatherJSON> weatherJSONLiveData;
    private LiveData<String> cityCode;
    private final int updateMinute=-50;  //设置数据库有效性为30分钟


    private TextView parent,city, updateTime, shidu, pm25, pm10, quality, wendu, ganmao, high, low, week, ymd, aqi, fx, fl, type, notice;
    private ImageView imageView_star;
    private SwipeRefreshLayout swipeRefreshLayout;

    public WeatherFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_search) {
            List<HotCity> hotCities = new ArrayList<>();
            hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
            hotCities.add(new HotCity("上海", "上海", "101020100"));
            hotCities.add(new HotCity("广州", "广东", "101280101"));
            hotCities.add(new HotCity("深圳", "广东", "101280601"));

            CityPicker.from(getActivity()) //activity或者fragment
                    .enableAnimation(true)    //启用动画效果，默认无
                    .setLocatedCity(new LocatedCity("北京 朝阳区", "北京", "101010300"))  //APP自身已定位的城市，传null会自动定位（默认）
                    .setHotCities(hotCities)    //指定热门城市
                    .setOnPickListener(new OnPickListener() {
                        @Override
                        public void onCancel() {
                            Toast.makeText(getContext(), "取消选择", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPick(int position, com.zaaach.citypicker.model.City data) {
                            Log.e("CityPicker", data.getName() + " " + data.getCode());
//                            weatherViewModel.setWeatherWebService(data.getCode());
//                            cityCode.postValue(data.getCode());
                            weatherViewModel.setCityCode(data.getCode());

                        }

                        @Override
                        public void onLocate() {
                            //定位接口，需要APP自身实现，这里模拟一下定位
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //定位完成之后更新数据
                                    CityPicker.from(getActivity())
                                            .locateComplete(new LocatedCity("朝阳区", "北京", "101010300"), LocateState.SUCCESS);
                                }
                            }, 3000);
                        }
                    })
                    .show();
        } else if (item.getItemId() == R.id.app_bar_search_id) {

            final EditText input = new EditText(getContext());
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("请输入城市id").setView(input)
                    .setNegativeButton(R.string.cancel, null);
            builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String tmp = input.getText().toString().trim();
                    try {
                        Integer.parseInt(tmp);
                        if(tmp.length()==9) {
                            weatherViewModel.setWeatherWebService(tmp);
                        }else{
                            Toast.makeText(getContext(), "请输入合法的id", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "请输入合法的id", Toast.LENGTH_SHORT).show();
                    }
                }
            }).show();

        }
        else if(item.getItemId()==R.id.app_bar_star_city){
//            getActivity().getSupportFragmentManager().beginTransaction().add(WeatherFragment.this,"weather").addToBackStack(null).commit();
            Navigation.findNavController(getActivity().getCurrentFocus()).navigate(R.id.action_weatherFragment_to_cityStarFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weatherViewModel = ViewModelProviders.of(getActivity()).get(WeatherViewModel.class);
        initView();
        initObserve();
        initDate();

    }

    //初始数据
    private void initDate() {
        Bundle bundle=getArguments();
        String cityID="";
        if(bundle!=null)
            cityID = bundle.getString("cityID");
        if(cityID!=null&&!cityID.isEmpty())
            weatherViewModel.setCityCode(cityID);
        else{
            //从shp中获取上次城市ID
            SharedPreferences shp = requireActivity().getSharedPreferences("lastCityID", Context.MODE_PRIVATE);
            cityID=shp.getString("last","101010300");
            weatherViewModel.setCityCode(cityID);
        }
    }

    //保存退出之前的城市ID
    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences shp = requireActivity().getSharedPreferences("lastCityID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        if(cityCode.getValue()!=null) {
            editor.putString("last", cityCode.getValue());
            Log.e("onDestroy",cityCode.getValue());
            editor.commit();
        }
    }

    //感知数据库
    private void initObserve() {
        swipeRefreshLayout = requireActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String cityCode = weatherViewModel.getCityCode();
                if (cityCode == null || cityCode.isEmpty()) {
                    weatherViewModel.setCityCode("101010300");
                    cityCode = "101010300";
                    swipeRefreshLayout.setRefreshing(false);
                }
                weatherViewModel.setCityCode(cityCode);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        imageView_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weather!=null){
                    if(weather.isStar()) {
                        weather.setStar(false);
                        weatherViewModel.updateWeather(weather);
                        Toast.makeText(getContext(), "取消收藏", Toast.LENGTH_SHORT).show();
                        imageView_star.setImageDrawable(getActivity().getDrawable(R.drawable.ic_star_border_black_24dp));
                    }
                    else {
                        weather.setStar(true);
                        weatherViewModel.updateWeather(weather);
                        Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                        imageView_star.setImageDrawable(getActivity().getDrawable(R.drawable.ic_star_black_24dp));
                    }
                }

            }
        });
        recyclerView = requireActivity().findViewById(R.id.recyclerView_weather);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapter = new WeatherAdapter();

        allWeathersLive=weatherViewModel.getAllWeatherLive();
        allWeathersLive.observe(getViewLifecycleOwner(), new Observer<List<Weather>>() {
            @Override
            public void onChanged(List<Weather> weathers) {
                weatherList = weathers;
                Log.e("weatherList", "数据库中缓存的城市数量："+weatherList.size());
            }
        });

        weatherJSONLiveData=weatherViewModel.getWeatherJSONLiveData();
        weatherJSONLiveData.observe(getViewLifecycleOwner(), new Observer<WeatherJSON>() {
            @Override
            public void onChanged(WeatherJSON weatherJSON) {
                if (weatherJSON != null) {
                    //更新数据信息
                    Gson gson = new Gson();
                    String cityCode = weatherJSON.getCityInfo().getCitykey();
                    Weather weatherTemp = new Weather(cityCode, DateUtils.dateToLong(weatherJSON.getTime()), gson.toJson(weatherJSON));
                    if (weatherList != null) {
                        for (Weather w : weatherList) {
                            if (w.getCityID().equals(cityCode)) {
                                weather=w;
                                if (w.isStar()) {
                                    setDataView(weatherJSON, true);
                                    weatherTemp.setStar(true);
                                }
                                else
                                    setDataView(weatherJSON,false);
                                weatherViewModel.updateWeather(weatherTemp);
                                Log.e("getWeatherJSONLiveData", "更新天气数据");
                                Toast.makeText(getContext(), "更新天气数据", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        setDataView(weatherJSON,false);
                        weather=weatherTemp;
                        weatherTemp.setStar(false);
                        weatherViewModel.insertWeather(weatherTemp);
                        Log.e("getWeatherJSONLiveData", "插入天气数据");
                        Toast.makeText(getContext(), "插入天气数据", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /*
        观察城市代码变化，如果数据库有且时间不超过半个小时，从数据库中获取，否则，从api获取，并更新数据库
         */
        cityCode=weatherViewModel.getCityCodeLive();
        cityCode.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("getCityCodeLive", s);
                //获取当前日历时间，并倒退30分钟
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MINUTE, updateMinute);
                if (s != null && !s.isEmpty()) {
                    Gson gson = new Gson();
                    if (weatherList != null && !weatherList.isEmpty()) {
                        //遍历数据库里的天气
                        for (Weather w : weatherList) {
                            if (s.equals(w.getCityID())) {
                                weatherNow = gson.fromJson(w.getJsonStr(), WeatherJSON.class);
                                long dateInDB = DateUtils.dateToLong(weatherNow.getTime());
                                //如果数据没有过时,否则从网络中获取
                                if (c.getTimeInMillis() <= dateInDB) {
                                    setDataView(weatherNow,w.isStar());
                                    Log.e("cityCode_observe", "从数据库中获取数据");
                                    Toast.makeText(getContext(), "从数据库中获取数据", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    weatherViewModel.setWeatherWebService(s);
                                    Log.e("cityCode_observe", "从网络中获取数据");
                                    Toast.makeText(getContext(), "从网络中获取数据", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }
                        }
                    }
                    weatherViewModel.setWeatherWebService(s);
                }
            }
        });
    }

    //绑定控件
    private void initView() {
        parent=getActivity().findViewById(R.id.textView_parent);
        city = getActivity().findViewById(R.id.textView_city);
        imageView_star=getActivity().findViewById(R.id.imageView_star);
        city.setFocusable(true);
        city.setFocusableInTouchMode(true);
        city.requestFocus();
        updateTime = getActivity().findViewById(R.id.textView_updateTime);
        shidu = getActivity().findViewById(R.id.textView_shidu);
        pm25 = getActivity().findViewById(R.id.textView_pm25);
        pm10 = getActivity().findViewById(R.id.textView_pm10);
        quality = getActivity().findViewById(R.id.textView_quality);
        wendu = getActivity().findViewById(R.id.textView_wendu);
        ganmao = getActivity().findViewById(R.id.textView_ganmao);
        high = getActivity().findViewById(R.id.textView_high);
        low = getActivity().findViewById(R.id.textView_low);
        ymd = getActivity().findViewById(R.id.textView_ymd);
        aqi = getActivity().findViewById(R.id.textView_aqi);
        fx = getActivity().findViewById(R.id.textView_fx);
        fl = getActivity().findViewById(R.id.textView_fl);
        type = getActivity().findViewById(R.id.textView_type);
        notice = getActivity().findViewById(R.id.textView_notice);
    }

    //加载天气数据
    private void setDataView(WeatherJSON weatherJSON,boolean star) {
        List<WeatherJSON.DataBean.ForecastBean> weatherListForecast;
        parent.setText(weatherJSON.getCityInfo().getParent());
        city.setText(weatherJSON.getCityInfo().getCity());
        if (star)
            imageView_star.setImageDrawable(getActivity().getDrawable(R.drawable.ic_star_black_24dp));
        else
            imageView_star.setImageDrawable(getActivity().getDrawable(R.drawable.ic_star_border_black_24dp));
        updateTime.setText("更新时间:" + weatherJSON.getCityInfo().getUpdateTime());
        shidu.setText("湿度:" + weatherJSON.getData().getShidu());
        pm25.setText("PM2.5:" + weatherJSON.getData().getPm25() + "");
        pm10.setText("PM10:" + weatherJSON.getData().getPm10() + "");
//        quality.setText("空气质量："+weatherJSON.getData().getQuality());
        wendu.setText(weatherJSON.getData().getWendu() + "℃");
        ganmao.setText(weatherJSON.getData().getGanmao());
        high.setText("最" + weatherJSON.getData().getForecast().get(0).getHigh());
        low.setText("最" + weatherJSON.getData().getForecast().get(0).getLow());
        ymd.setText(weatherJSON.getData().getForecast().get(0).getYmd());
        aqi.setText(weatherJSON.getData().getForecast().get(0).getAqi() + " " + weatherJSON.getData().getQuality());
        fx.setText(weatherJSON.getData().getForecast().get(0).getFx());
        fl.setText(weatherJSON.getData().getForecast().get(0).getFl());
        type.setText(weatherJSON.getData().getForecast().get(0).getType());
        setBackground(type.getText().toString());
        notice.setText(weatherJSON.getData().getForecast().get(0).getNotice());

        weatherListForecast = weatherJSON.getData().getForecast();
        myAdapter.setWeatherForecast(weatherListForecast);
        recyclerView.setAdapter(myAdapter);
    }

    //设置背景
    private void setBackground(String str) {
        Random random = new Random();
        int a = random.nextInt(5);
        switch (a) {
            case 1:
                swipeRefreshLayout.setBackground(getActivity().getDrawable(R.drawable.ic_background_a));
                break;
            case 2:
                swipeRefreshLayout.setBackground(getActivity().getDrawable(R.drawable.ic_background_b));
                break;
            case 3:
                swipeRefreshLayout.setBackground(getActivity().getDrawable(R.drawable.ic_background_c));
                break;
            case 4:
                swipeRefreshLayout.setBackground(getActivity().getDrawable(R.drawable.ic_background_d));
                break;
            case 5:
                swipeRefreshLayout.setBackground(getActivity().getDrawable(R.drawable.ic_background_e));
                break;
            default:
                swipeRefreshLayout.setBackground(getActivity().getDrawable(R.drawable.ic_background_f));
        }
    }

    //初始化城市数据
    private void initCityData() {
        // 解析从本地Json中存储城市代码的数据
        String cityJsonData = CityFileUtils.getJson(getContext(), "city.json");
        Gson gson = new Gson();
        City[] cities = gson.fromJson(cityJsonData, City[].class);
        weatherViewModel.insertCity(cities);
    }
}
