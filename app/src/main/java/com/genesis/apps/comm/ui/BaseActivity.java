package com.genesis.apps.comm.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.genesis.apps.comm.util.excutor.ExecutorService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BaseActivity extends AppCompatActivity {

    @Inject
    public ExecutorService executorService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
