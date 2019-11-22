package com.kailang.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kailang.weather.data.Weather;
import com.kailang.weather.data.WeatherJSON;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {
    private List<WeatherJSON.DataBean.ForecastBean> weatherListForecast=new ArrayList<>();

    public void setWeatherForecast(List<WeatherJSON.DataBean.ForecastBean> weatherListForecast) {
        weatherListForecast.remove(0);
        this.weatherListForecast = weatherListForecast;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.weather_card,parent,false);

        final MyViewHolder holder = new MyViewHolder(itemView);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //点击跳转
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.week.setText(weatherListForecast.get(position).getWeek());
        holder.high.setText(weatherListForecast.get(position).getHigh());
        holder.low.setText(weatherListForecast.get(position).getLow());
        holder.fx.setText(weatherListForecast.get(position).getFx());
        holder.fl.setText(weatherListForecast.get(position).getFl());
        holder.type.setText(weatherListForecast.get(position).getType());
        holder.ymd.setText(weatherListForecast.get(position).getYmd());
    }

    @Override
    public int getItemCount() {
        return weatherListForecast.size();
    }

    //自定义ViewHolder:内部类，static 防止内存泄露
    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView week,high,low,fx,fl,type,ymd;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            week=itemView.findViewById(R.id.textView_week_w);
            high=itemView.findViewById(R.id.textView_high_w);
            low=itemView.findViewById(R.id.textView_low_w);
            fx=itemView.findViewById(R.id.textView_fx_w);
            fl=itemView.findViewById(R.id.textView_fl_w);
            type=itemView.findViewById(R.id.textView_type_w);
            ymd=itemView.findViewById(R.id.textView_ymd_w);

        }
    }
}
