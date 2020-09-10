package com.genesis.apps.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.MYP_2006;
import com.genesis.apps.comm.model.gra.MYP_8005;
import com.genesis.apps.comm.model.gra.viewmodel.MYPViewModel;
import com.genesis.apps.comm.model.vo.MembershipPointVO;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ActivityMygMembershipExtncBinding;
import com.genesis.apps.databinding.ActivityMygMembershipInfoBinding;
import com.genesis.apps.ui.view.listview.ExtncPlanPontAdapter;
import com.genesis.apps.ui.view.listview.NotiAccodianRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyGMembershipExtncActivity extends SubActivity<ActivityMygMembershipExtncBinding> {

    private MYPViewModel mypViewModel;
    private ExtncPlanPontAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_membership_extnc);
        ui.lTitle.title.setVisibility(View.INVISIBLE);
        ui.setLifecycleOwner(this);

        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
        mypViewModel.getRES_MYP_2006().observe(this, responseNetUI -> {

//            if(responseNetUI.status == NetUIResponse.Status.LOADING)
//                return;
//
//            adapter.setRows(getListData());
//            adapter.notifyDataSetChanged();
//            setEmptyView(adapter.getItemCount());
//            setExtncPlanPont();

            switch (responseNetUI.status) {
                case SUCCESS:
                    showProgressDialog(false);
                    //추가할 아이템이 있을 경우만 adaper 갱신
                    if (responseNetUI.data != null && responseNetUI.data.getExtncPlanList() != null) {
                        adapter.setRows(responseNetUI.data.getExtncPlanList());
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case LOADING:
                    showProgressDialog(true);
                    return;
                default:
                    showProgressDialog(false);
                    break;
            }

            setExtncPlanPont();
            setEmptyView(adapter.getItemCount());

        });


        adapter = new ExtncPlanPontAdapter();
//        ((SimpleItemAnimator) ui.rvNoti.getItemAnimator()).setSupportsChangeAnimations(true);
        ui.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);

        mypViewModel.reqMYP2006(new MYP_2006.Request());
    }

    private void setEmptyView(int itemCount){
        if(itemCount>0){
            ui.rv.setVisibility(View.VISIBLE);
            ui.tvEmpty.setText("");
        }else{
            ui.rv.setVisibility(View.GONE);
            ui.tvEmpty.setText(R.string.msg_membership_11);
        }
    }

    private void setExtncPlanPont(){
        ui.tvExtncPont6mm.setText(StringUtil.getDigitGrouping(adapter.getTotalExtncPlanPont()));
    }


    private List<MembershipPointVO> getListData(){
        List<MembershipPointVO> list = new ArrayList<>();
        for(int i=0; i<10; i++){
            list.add(new MembershipPointVO(i+"","","","","","","20200910","12345"));
        }
        return list;
    }
}
