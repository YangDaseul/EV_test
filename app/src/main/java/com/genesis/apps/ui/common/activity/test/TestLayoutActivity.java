package com.genesis.apps.ui.common.activity.test;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.view.listview.test.TestItemAdapter;

public class TestLayoutActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getIntent().getIntExtra(TestItemAdapter.INTENT_LAYOUT_ID, R.layout.item_test);
        setContentView(layoutId);
    }
}
