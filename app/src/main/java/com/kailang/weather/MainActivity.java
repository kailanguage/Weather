package com.kailang.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kailang.weather.data.Weather;

public class MainActivity extends AppCompatActivity implements CityStarFragment.OnListFragmentInteractionListener{
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(findViewById(R.id.fragment));
        NavigationUI.setupActionBarWithNavController(this,navController);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        getSupportFragmentManager().popBackStack("weather",0);
        navController.navigateUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onListFragmentInteraction(View view, Weather weather) {
        Bundle bundle = new Bundle();
        bundle.putString("cityID",weather.getCityID());
        Navigation.findNavController(view).navigate(R.id.action_cityStarFragment_to_weatherFragment,bundle);
    }

}
