package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.PUB_1001;
import com.genesis.apps.comm.model.vo.AddressZipVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.PUBViewModel;
import com.genesis.apps.databinding.ActivitySearchAddressBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.home.view.SearchAddressAdapter;

import java.util.List;

public class SearchAddressActivity extends SubActivity<ActivitySearchAddressBinding> {
    private static final int PAGE_SIZE = 20;

    private PUBViewModel pubViewModel;
    private SearchAddressAdapter adapter;
    int titleId=0;
    int msgId=0;
    private boolean isPrivilege=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        setViewMsg();
        adapter = new SearchAddressAdapter(onSingleClickListener);
        ui.setActivity(this);
        ui.lSearchParent.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.lSearchParent.rv.setHasFixedSize(true);
        ui.lSearchParent.rv.setAdapter(adapter);
        ui.lSearchParent.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!ui.lSearchParent.rv.canScrollVertically(1)&&ui.lSearchParent.rv.getScrollState()==RecyclerView.SCROLL_STATE_IDLE) {//scroll end
                    if(adapter.getItemCount()>0&&adapter.getItemCount() >= adapter.getPageNo() * PAGE_SIZE) searchAddress();
                }
            }
        });

        ui.lSearchParent.etSearch.setOnEditorActionListener(editorActionListener);
        ui.lSearchParent.etSearch.setHint(R.string.gm_carlst_02_30);
        ui.lSearchParent.tvTitleSub.setText(R.string.mg00_word_3);
        ui.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
    }

    private void setViewMsg(){
        ui.lTitle.setValue(titleId != 0 ? getString(titleId) : getString(R.string.gm_carlst_01_01_1));
        if (msgId != 0) {
            ui.tvMsg.setVisibility(View.VISIBLE);
            ui.tvMsg.setText(getString(msgId));
        }
    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){

            case R.id.l_whole:
                AddressZipVO addressZipVO = (AddressZipVO)v.getTag(R.id.addr);
                setResult(!isPrivilege ? ResultCodes.REQ_CODE_ADDR_ZIP.getCode() : ResultCodes.REQ_CODE_ADDR_ZIP_PRIVILEGE.getCode(), new Intent().putExtra(KeyNames.KEY_NAME_ZIP_ADDR, addressZipVO));
                finish();
                break;
        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        pubViewModel = new ViewModelProvider(this).get(PUBViewModel.class);
    }

    @Override
    public void setObserver() {

        pubViewModel.getRES_PUB_1001().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getZipList()!=null&&result.data.getZipList().size()>0){
                        if (adapter.getPageNo() == 0) {
                            adapter.setRows(result.data.getZipList());
                        } else {
                            adapter.addRows(result.data.getZipList());
                        }
                        adapter.setPageNo(adapter.getPageNo() + 1);
                        adapter.notifyDataSetChanged();
                    }
                default:
                    showProgressDialog(false);
                    if(result.data!=null&&(StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("9020")||StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("2022"))){
                        SnackBarUtil.show(this, StringUtil.isValidString(result.data.getRtMsg()));
                        return;
                    }

                    if ((adapter != null && adapter.getItemCount() < 1) || adapter.getPageNo()==0) {
                        if(adapter!=null) adapter.clear();

                        ui.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
                    } else {
                        ui.lSearchParent.tvEmpty.setVisibility(View.GONE);
                    }
                    break;

            }

        });

    }

    @Override
    public void getDataFromIntent() {
        try {
            titleId = getIntent().getIntExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, 0);
            msgId = getIntent().getIntExtra(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, 0);
            isPrivilege = getIntent().getBooleanExtra(KeyNames.KEY_NAME_MAP_SEARCH_PRIVILEGE, false);
        }catch (Exception ignore){

        }
    }

    private void setListView(List<AddressZipVO> list) {
        if (list == null || list.size() < 1) {
            ui.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            ui.lSearchParent.tvEmpty.setVisibility(View.GONE);
        }
        adapter.setRows(list);
        adapter.notifyDataSetChanged();
    }

    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if(actionId== EditorInfo.IME_ACTION_SEARCH){
            if(adapter!=null){
                adapter.setPageNo(0);
            }
            searchAddress();
        }
        return false;
    };


    private void searchAddress(){
        //end
        String keyword = ui.lSearchParent.etSearch.getText().toString().trim();
        if(!TextUtils.isEmpty(keyword)) {
            pubViewModel.reqPUB1001(new PUB_1001.Request(APPIAInfo.GM_CARLST_01_A01.getId(), keyword, adapter.getPageNo() + 1 + "", "" + PAGE_SIZE));
        }else{
            ui.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
        }
        SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
    }

}
