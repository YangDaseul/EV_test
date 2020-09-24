package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.OilCodes;
import com.genesis.apps.comm.model.ResultCodes;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.MYP_2001;
import com.genesis.apps.comm.model.gra.MYP_2002;
import com.genesis.apps.comm.model.gra.MYP_8005;
import com.genesis.apps.comm.model.gra.viewmodel.MYPViewModel;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ActivityMygMembershipBinding;
import com.genesis.apps.databinding.ActivityMygMembershipUseListBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.CardHorizontalAdapter;
import com.genesis.apps.ui.myg.view.PointUseListAdapter;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class MyGMembershipUseListActivity extends SubActivity<ActivityMygMembershipUseListBinding> {

    private MYPViewModel mypViewModel;
    private PointUseListAdapter adapter;
    private String mbrshMbrMgmtNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_membership_use_list);
        getMemberNo();
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
        ui.setLifecycleOwner(this);
        ui.setActivity(this);

        adapter = new PointUseListAdapter();
        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);

        mypViewModel.getRES_MYP_2002().observe(this, result -> {

            String test ="{\n" +
                    "  \"rsltCd\": \"0000\",\n" +
                    "  \"rsltMsg\": \"성공\",\n" +
                    "  \"blueMbrYn\": \"Y\",\n" +
                    "  \"mbrshMbrMgmtNo\": \"1000000\",\n" +
                    "  \"transTotCnt\": \"3\",\n" +
                    "  \"transList\": [\n" +
                    "    {\n" +
                    "      \"seqNo\": \"1\",\n" +
                    "      \"transDtm\": \"20200901111111\",\n" +
                    "      \"frchsNm\": \"가맹점1\",\n" +
                    "      \"transTypNm\": \"사용\",\n" +
                    "      \"useMlg\": \"124574\",\n" +
                    "      \"rmndPont\": \"1111111\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"seqNo\": \"2\",\n" +
                    "      \"transDtm\": \"20200902222222\",\n" +
                    "      \"frchsNm\": \"가맹점2\",\n" +
                    "      \"transTypNm\": \"사용\",\n" +
                    "      \"useMlg\": \"222222\",\n" +
                    "      \"rmndPont\": \"333333\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"seqNo\": \"3\",\n" +
                    "      \"transDtm\": \"20200920000000\",\n" +
                    "      \"frchsNm\": \"가맹점3\",\n" +
                    "      \"transTypNm\": \"적립\",\n" +
                    "      \"useMlg\": \"222222\",\n" +
                    "      \"rmndPont\": \"333333\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"seqNo\": \"4\",\n" +
                    "      \"transDtm\": \"20200930000000\",\n" +
                    "      \"frchsNm\": \"가맹점4\",\n" +
                    "      \"transTypNm\": \"취소\",\n" +
                    "      \"useMlg\": \"2222221\",\n" +
                    "      \"rmndPont\": \"3333331\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            MYP_2002.Response sample = new Gson().fromJson(test, MYP_2002.Response.class);

            if(sample!=null&&sample.getTransList()!=null&&sample.getTransList().size()>0){
                int itemSizeBefore = adapter.getItemCount();
                if (adapter.getPageNo() == 0) {
                    adapter.setRows(sample.getTransList());
                } else {
                    adapter.addRows(sample.getTransList());
                }
                adapter.setPageNo(adapter.getPageNo() + 1);
//                      adapter.notifyDataSetChanged();
                adapter.notifyItemRangeInserted(itemSizeBefore, adapter.getItemCount());

                ui.tvPointSave.setText(StringUtil.getDigitGrouping(adapter.getTotalSavePoint()));
                ui.tvPointUse.setText(StringUtil.getDigitGrouping(adapter.getTotalUsePoint()));
            }
        });


        ui.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!ui.rv.canScrollVertically(-1)) {
                    //top
                } else if (!ui.rv.canScrollVertically(1)) {
                    //end
                    mypViewModel.reqMYP2002(new MYP_2002.Request(APPIAInfo.MG_MEMBER04.getId(), mbrshMbrMgmtNo,"","",adapter.getPageNo()+1+"","20"));
                } else {
                    //idle
                }

            }
        });
        mypViewModel.reqMYP2002(new MYP_2002.Request(APPIAInfo.MG_MEMBER04.getId(), mbrshMbrMgmtNo,"","","1","20"));
    }

    private void getMemberNo(){
        try {
            mbrshMbrMgmtNo = getIntent().getStringExtra("mbrshMbrMgmtNo");
            if(TextUtils.isEmpty(mbrshMbrMgmtNo)){
                exitPage("블루멤버스 회원번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }catch (Exception e){
            e.printStackTrace();
            exitPage("블루멤버스 회원번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_query:

                break;


        }
    }
}
