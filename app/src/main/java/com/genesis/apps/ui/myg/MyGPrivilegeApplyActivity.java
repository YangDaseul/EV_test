package com.genesis.apps.ui.myg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_1005;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.PrivilegeVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.databinding.ActivityPrivilegeApplyBinding;
import com.genesis.apps.ui.common.activity.GAWebActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.PrivilegeApplyAdapter;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author hjpark
 * @brief 프리빌리지 신청
 */
public class MyGPrivilegeApplyActivity extends SubActivity<ActivityPrivilegeApplyBinding> {
    private MYPViewModel mypViewModel;
    private PrivilegeApplyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privilege_apply);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //프리빌리지 신청 완료 후에 해당 화면으로 왔을 때 데이터를 갱신하기 위함
        mypViewModel.reqMYP1005(new MYP_1005.Request(APPIAInfo.MG01.getId(), ""));
    }

    private void initView() {
        adapter = new PrivilegeApplyAdapter(onSingleClickListener);
        ui.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.rv.setHasFixedSize(true);
        ui.rv.addItemDecoration(new RecyclerViewDecoration((int)DeviceUtil.dip2Pixel(this,4.0f)));
        ui.rv.setAdapter(adapter);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_status:
                PrivilegeVO data = (PrivilegeVO) v.getTag(R.id.item);

                if(data != null) {
                    if("EQ900".equals(data.getMdlNm()) || "G90".equals(data.getMdlNm()) || "G80".equals(data.getMdlNm())) {
                        startActivitySingleTop(new Intent(this, MyGPrivilegeStateActivity.class).putExtra(KeyNames.KEY_NAME_PRIVILEGE_VO, data), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    } else {
                        String url = data.getServiceUrl();
                        goPrivilege(v.getId(), url);
                    }
                }

                break;
//            case R.id.btn_benefit:
            case R.id.btn_apply:
                String url = v.getTag(R.id.url).toString();
                goPrivilege(v.getId(), url);
                break;
        }
    }

    private void goPrivilege(int id, String url) {
        if(!TextUtils.isEmpty(url.trim())){
            int titleId=0;
            switch (id){
                case R.id.btn_status:
                    titleId = R.string.mg_prvi01_word_1_2;
                    break;
                case R.id.btn_benefit:
                    titleId = R.string.mg_prvi01_word_1_3;
                    break;
                case R.id.btn_apply:
                    titleId = R.string.mg_prvi01_word_1;
                    break;
            }
            startActivitySingleTop(new Intent(this, GAWebActivity.class).putExtra(KeyNames.KEY_NAME_URL, url).putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, titleId), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        }else{
            SnackBarUtil.show(this, "이동 가능한 URL이 없습니다.");
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_MYP_1005().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getPvilList()!=null){
                        adapter.setRows(mypViewModel.getPossibleApplyPrivilegeList(result.data.getPvilList()));
                        adapter.notifyDataSetChanged();
                    }
                    setListData();
                    showProgressDialog(false);
                    break;
                default:
                    showProgressDialog(false);
                    setListData();
                    break;
            }
        });
    }

    private void setListData(){
        ui.tvCntValue.setText(adapter.getItemCount()+"");
        ui.tvEmpty.setVisibility(adapter.getItemCount()==0 ? View.VISIBLE: View.GONE);
    }

    @Override
    public void getDataFromIntent() {

    }
}
