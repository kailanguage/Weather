package com.kailang.weather;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kailang.weather.CityStarFragment.OnListFragmentInteractionListener;
import com.kailang.weather.data.Weather;
import com.kailang.weather.data.WeatherJSON;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Weather} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CityStarAdapter extends RecyclerView.Adapter<CityStarAdapter.ViewHolder> {

    private final List<Weather> mValues;
    private final OnListFragmentInteractionListener mListener;

    public CityStarAdapter(List<Weather> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getCityID());
        Gson gson = new Gson();
        WeatherJSON weatherJSON = new WeatherJSON();
        weatherJSON=gson.fromJson(holder.mItem.getJsonStr(),WeatherJSON.class);
        holder.mContentView.setText(weatherJSON.getCityInfo().getParent()+" "+weatherJSON.getCityInfo().getCity()+"  数据更新时间:"+weatherJSON.getData().getForecast().get(0).getYmd()+" "+weatherJSON.getCityInfo().getUpdateTime());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mView,holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Weather mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.textView_city_id);
            mContentView = (TextView) view.findViewById(R.id.textView_city_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
