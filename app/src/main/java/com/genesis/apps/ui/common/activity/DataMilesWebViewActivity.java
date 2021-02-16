package com.genesis.apps.ui.common.activity;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;

/**
 * Class Name : DataMilesWebViewActivity
 *
 * @author Ki-man Kim
 * @since 2021-02-09
 */
public class DataMilesWebViewActivity extends WebviewActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui.lTitle.lTitleBar.setVisibility(View.GONE);
    }
} // end of class DataMilesWebViewActivity
