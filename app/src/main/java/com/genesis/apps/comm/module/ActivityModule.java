package com.genesis.apps.comm.module;

import android.app.Activity;

import com.genesis.apps.R;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.TwoButtonDialog;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class ActivityModule {

    @Provides
    public BaseActivity getBaseActivity(Activity activity){
        return (BaseActivity)activity;
    }

    @Provides
    public TwoButtonDialog getTwoButtonDialog(Activity activity){
        return new TwoButtonDialog(activity, R.style.BottomSheetDialogTheme);
    }

    @Provides
    public BottomListDialog bottomListDialog(Activity activity){
        return new BottomListDialog(activity, R.style.BottomSheetDialogTheme);
    }
}
