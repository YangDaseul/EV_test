package com.genesis.apps.ui.myg;

import android.os.Bundle;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityMygVersionBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class MyGGAActivity extends SubActivity<ActivityMygVersionBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_ga);
        ui.lTitle.title.setText(R.string.title_ga);
        //TODO 처리 필요


//        ui.ivIcon.setImageResource(R.mipmap.ic_launcher);
//        ui.tvCurrentVersion.setText(currentVersion);
//        ui.tvNewVersion.setText(newVersion);
//
//
//        //최신버전일경우
//        ui.tvMsg.setText(R.string.msg_version_1);
//        ui.lNewVersion.setBackgroundColor(getColor(R.color.x_f4f4f4));
//        ui.tvNewTitle.setTextColor(getColor(R.color.x_525252));
//        ui.tvNewVersion.setTextColor(getColor(R.color.x_141414));
//        ui.tvUpdate.setVisibility(View.GONE);
//
//        //업데이트필요한경우
//        ui.tvMsg.setText(R.string.msg_version_2);
//        ui.lNewVersion.setBackgroundResource(R.drawable.bg_ffffff_stroke_cd9a81);
//        ui.tvNewTitle.setTextColor(getColor(R.color.x_cd9a81));
//        ui.tvNewVersion.setTextColor(getColor(R.color.x_cd9a81));
//        ui.tvUpdate.setVisibility(View.VISIBLE);
    }

}
