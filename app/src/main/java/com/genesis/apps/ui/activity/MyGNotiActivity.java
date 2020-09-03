package com.genesis.apps.ui.activity;

import android.os.Bundle;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.viewmodel.NotiViewModel;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.databinding.ActivityNotiListBinding;
import com.genesis.apps.ui.view.viewholder.NotiAccodianRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MyGNotiActivity extends SubActivity<ActivityNotiListBinding> {

    private NotiViewModel notiViewModel;
    private NotiAccodianRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_list);
        ui.lTitle.title.setText(R.string.title_noti);
        ui.setLifecycleOwner(this);


        notiViewModel = new ViewModelProvider(this).get(NotiViewModel.class);
        notiViewModel.getNotiVOList().observe(this, data -> {
            adapter.setRows(data);
            adapter.notifyDataSetChanged();
        });

        adapter = new NotiAccodianRecyclerAdapter();
//        ((SimpleItemAnimator) ui.rvNoti.getItemAnimator()).setSupportsChangeAnimations(true);
        ui.rvNoti.setLayoutManager(new LinearLayoutManager(this));
//        ui.rvNoti.setHasFixedSize(true);
        ui.rvNoti.setHasFixedSize(true);
        ui.rvNoti.setAdapter(adapter);

        notiViewModel.reqNotiVoList(getListData());
    }

    private List<NotiVO> getListData(){
        List<NotiVO> list = new ArrayList<>();
        list.add(new NotiVO("","", "공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다.", "공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다.", "20200902110522"));
        for(int i=0; i<10; i++){
            list.add(new NotiVO("",i+"", "test"+i, "content"+i, "20200802180522"));
        }
        return list;
    }
}
