package com.genesis.app.comm.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.genesis.app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public final void showProgressDialog(final boolean show, final View view) {
        try {
            if (view != null)
                runOnUiThread(() -> view.setVisibility(show ? View.VISIBLE : View.GONE));
        } catch (Exception ignore) {

        }
    }
}
