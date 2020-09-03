package com.genesis.apps.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.viewmodel.NotiViewModel;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.databinding.ActivityMygVersionBinding;
import com.genesis.apps.databinding.ActivityNotiListBinding;
import com.genesis.apps.ui.view.viewholder.NotiAccodianRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MyGVersioniActivity extends SubActivity<ActivityMygVersionBinding> {

    //TODO 버전 확인 정책...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_version);
        ui.lTitle.title.setText(R.string.title_version);


        String newVersion="0.0.1";
        String currentVersion= PackageUtil.getApplicationVersionName(this, getPackageName());
        if (TextUtils.isEmpty(currentVersion)) {
            currentVersion = "";
        }



//        ui.ivIcon.setImageResource(R.mipmap.ic_launcher);
        ui.tvCurrentVersion.setText(currentVersion);
        ui.tvNewVersion.setText(newVersion);


        //최신버전일경우
        ui.tvMsg.setText(R.string.msg_version_1);
        ui.lNewVersion.setBackgroundColor(getColor(R.color.x_f4f4f4));
        ui.tvNewTitle.setTextColor(getColor(R.color.x_525252));
        ui.tvNewVersion.setTextColor(getColor(R.color.x_141414));
        ui.tvUpdate.setVisibility(View.GONE);

        //업데이트필요한경우
        ui.tvMsg.setText(R.string.msg_version_2);
        ui.lNewVersion.setBackgroundResource(R.drawable.bg_ffffff_stroke_cd9a81);
        ui.tvNewTitle.setTextColor(getColor(R.color.x_cd9a81));
        ui.tvNewVersion.setTextColor(getColor(R.color.x_cd9a81));
        ui.tvUpdate.setVisibility(View.VISIBLE);
    }

}
