package com.genesis.apps.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.viewmodel.NotiViewModel;
import com.genesis.apps.comm.model.map.MapViewModel;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.VibratorUtil;
import com.genesis.apps.databinding.ActivityNotiListBinding;
import com.genesis.apps.databinding.ActivityVerticalOverlapExampleBinding;
import com.genesis.apps.ui.activity.test.TestCardViewAdapter;
import com.genesis.apps.ui.view.BaseRecyclerViewAdapter;
import com.genesis.apps.ui.view.listview.Link;
import com.genesis.apps.ui.view.listview.LinkDiffCallback;
import com.genesis.apps.ui.view.listview.MyItemClickListener;
import com.genesis.apps.ui.view.listview.TestUserAdapter;
import com.genesis.apps.ui.view.viewholder.BaseDiffCallback;
import com.genesis.apps.ui.view.viewholder.NotiAccodianListAdapter;
import com.genesis.apps.ui.view.viewholder.NotiAccodianRecyclerAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NotiActivity extends SubActivity<ActivityNotiListBinding> {

    private NotiViewModel notiViewModel;
    private NotiAccodianRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_list);
        notiViewModel = new ViewModelProvider(this).get(NotiViewModel.class);
        ui.setLifecycleOwner(this);


        adapter = new NotiAccodianRecyclerAdapter((data, position) -> notiViewModel.updateNotiVO(data, position));

        notiViewModel.getNotiVOList().observe(this, data -> {
            adapter.setRows(data);
            adapter.notifyDataSetChanged();
        });

        ui.rvNoti.setLayoutManager(new LinearLayoutManager(this));
        ui.rvNoti.setAdapter(adapter);

        notiViewModel.reqNotiVoList(getListData());
    }

    private List<NotiVO> getListData(){
        List<NotiVO> list = new ArrayList<>();

        for(int i=0; i<10; i++){
            list.add(new NotiVO("",i+"", "test"+i, "content"+i, "20200902110522",false));
        }
        return list;
    }
}
