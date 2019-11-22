package com.kailang.weather;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kailang.weather.data.city.City;
import com.kailang.weather.data.Weather;
import com.kailang.weather.data.WeatherJSON;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private WeatherViewModel weatherViewModel;
    private LiveData<List<City>> allCitiesLive;
    private List<Weather> weatherList;
    private List<City> cityList;
    private RecyclerView recyclerView;
    private WeatherAdapter myAdapter;
    private WeatherJSON weatherNow;
    private MutableLiveData<String> cityCode;
    private List<WeatherJSON.DataBean.ForecastBean> weatherListForecast;

    private TextView city, updateTime, shidu, pm25, pm10, quality, wendu, ganmao, high, low, week, ymd, aqi, fx, fl, type, notice;
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
                    .setLocatedCity(new LocatedCity("北京 朝阳区", "北京", "101060110"))  //APP自身已定位的城市，传null会自动定位（默认）
                    .setHotCities(hotCities)    //指定热门城市
                    .setOnPickListener(new OnPickListener() {
                        @Override
                        public void onCancel() {
                            Toast.makeText(getContext(), "取消选择", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPick(int position, com.zaaach.citypicker.model.City data) {
                            Log.e("xxxx", data.getName() + " " + data.getCode());
                            weatherViewModel.setWeatherWebService(data.getCode());
                            cityCode.postValue(data.getCode());
                        }

                        @Override
                        public void onLocate() {
                            //定位接口，需要APP自身实现，这里模拟一下定位
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //定位完成之后更新数据
                                    CityPicker.from(getActivity())
                                            .locateComplete(new LocatedCity("朝阳区", "北京", "101060110"), LocateState.SUCCESS);
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
                        weatherViewModel.setWeatherWebService(tmp);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "请输入合法的id", Toast.LENGTH_SHORT).show();
                    }
                }
            }).show();

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
        initView();
        swipeRefreshLayout=requireActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(cityCode==null||cityCode.getValue().isEmpty()){
                    cityCode.postValue("101060110");
                    //在线的
                    weatherViewModel.setWeatherWebService(cityCode.getValue());
                    swipeRefreshLayout.setRefreshing(false);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView = requireActivity().findViewById(R.id.recyclerView_weather);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapter = new WeatherAdapter();

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);

        /*
        观察城市代码变化，如果数据库有且时间不超过半个小时，从数据库中获取，否则，从api获取，并更新数据库
         */
        cityCode.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });


        weatherViewModel.getAllWeatherLive().observe(this, new Observer<List<Weather>>() {
            @Override
            public void onChanged(List<Weather> weathers) {
                weatherList = weathers;
            }
        });

        weatherViewModel.getWeatherJSONLiveData().observe(this, new Observer<WeatherJSON>() {
            @Override
            public void onChanged(WeatherJSON weatherJSON) {
                if (weatherJSON != null) {
                    setDataView(weatherJSON);
                    weatherListForecast=weatherJSON.getData().getForecast();
                    myAdapter.setWeatherForecast(weatherListForecast);
                    recyclerView.setAdapter(myAdapter);
                }
            }
        });

    }


    //绑定控件
    private void initView() {
        city = getActivity().findViewById(R.id.textView_city);
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

    private void setDataView(WeatherJSON weatherJSON) {
        city.setText(weatherJSON.getCityInfo().getCity());
        updateTime.setText("更新时间:"+weatherJSON.getCityInfo().getUpdateTime());
        shidu.setText("湿度:"+weatherJSON.getData().getShidu());
        pm25.setText("PM2.5:"+weatherJSON.getData().getPm25() + "");
        pm10.setText("PM10:"+weatherJSON.getData().getPm10() + "");
//        quality.setText("空气质量："+weatherJSON.getData().getQuality());
        wendu.setText(weatherJSON.getData().getWendu()+"℃");
        ganmao.setText(weatherJSON.getData().getGanmao());
        high.setText("最"+weatherJSON.getData().getForecast().get(0).getHigh());
        low.setText("最"+weatherJSON.getData().getForecast().get(0).getLow());
        ymd.setText(weatherJSON.getData().getForecast().get(0).getYmd());
        aqi.setText(weatherJSON.getData().getForecast().get(0).getAqi()+" "+weatherJSON.getData().getQuality());
        fx.setText(weatherJSON.getData().getForecast().get(0).getFx());
        fl.setText(weatherJSON.getData().getForecast().get(0).getFl());
        type.setText(weatherJSON.getData().getForecast().get(0).getType());
        notice.setText(weatherJSON.getData().getForecast().get(0).getNotice());
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
