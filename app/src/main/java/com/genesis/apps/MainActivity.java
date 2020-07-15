package com.genesis.apps;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.genesis.apps.comm.model.RequestCodes;
import com.genesis.apps.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(activityMainBinding.navView, navController);

        activityMainBinding.button.setOnClickListener(view -> startActivitySingleTop(new Intent(MainActivity.this, EntranceActivity.class),RequestCodes.REQ_CODE_DEFAULT.getCode()));
    }


}
