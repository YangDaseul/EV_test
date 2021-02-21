package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_2006;
import com.genesis.apps.comm.model.vo.MembershipPointVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.databinding.ActivityMygMembershipExtncBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.ExtncPlanPontAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MyGMembershipExtncActivity extends SubActivity<ActivityMygMembershipExtncBinding> {

    private MYPViewModel mypViewModel;
    private ExtncPlanPontAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_membership_extnc);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        mypViewModel.reqMYP2006(new MYP_2006.Request(APPIAInfo.MG_MEMBER01_P01.getId()));
    }

    private void initView() {
        adapter = new ExtncPlanPontAdapter();
//        ((SimpleItemAnimator) ui.rvNoti.getItemAnimator()).setSupportsChangeAnimations(true);
        ui.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_MYP_2006().observe(this, result -> {

//            if(result.status == NetUIResponse.Status.LOADING)
//                return;
//
//            adapter.setRows(getListData());
//            adapter.notifyDataSetChanged();
//            setEmptyView(adapter.getItemCount());
//            setExtncPlanPont();

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    return;
                case SUCCESS:
                    showProgressDialog(false);
                    //추가할 아이템이 있을 경우만 adaper 갱신
                    if (result.data != null && result.data.getExtncPlanList() != null) {
                        adapter.setRows(result.data.getExtncPlanList());
                    }
                    adapter.notifyDataSetChanged();
                    setExtncPlanPont();
                    setEmptyView(adapter.getItemCount());
                    break;
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if(TextUtils.isEmpty(serverMsg)) serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }

        });
    }

    @Override
    public void getDataFromIntent() {

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


//    private void initTabView() {
//        final List<String> list = new ArrayList<>();
//        list.add(getString(R.string.word_membership_7));
//        list.add(getString(R.string.word_membership_8));
//        list.add(getString(R.string.word_membership_9));
//        list.add(getString(R.string.word_membership_10));
//        for (String codeNm : list) {
//            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final ItemTabGoldBinding itemTabGoldBinding = DataBindingUtil.inflate(inflater, R.layout.item_tab_gold, null, false);
//            final View view = itemTabGoldBinding.getRoot();
//            itemTabGoldBinding.tvTab.setText(codeNm);
//            ui.tabs.addTab(ui.tabs.newTab().setCustomView(view));
//        }
//
//        ui.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                getList(cmnViewModel.getAlarmTypeCd(((ItemTabGoldBinding) DataBindingUtil.bind(tab.getCustomView())).tvTab.getText().toString()), "");
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }
}
