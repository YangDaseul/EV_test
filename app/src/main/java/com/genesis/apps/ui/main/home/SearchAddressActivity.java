package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.MYP_2002;
import com.genesis.apps.comm.model.gra.api.PUB_1001;
import com.genesis.apps.comm.model.vo.AddressZipVO;
import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.comm.model.vo.NotiInfoVO;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.PUBViewModel;
import com.genesis.apps.databinding.ActivitySearchAddressBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.AlarmCenterRecyclerAdapter;
import com.genesis.apps.ui.main.home.view.SearchAddressAdapter;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAddressActivity extends SubActivity<ActivitySearchAddressBinding> {

    private PUBViewModel pubViewModel;
    private SearchAddressAdapter adapter;

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
        adapter = new SearchAddressAdapter(onSingleClickListener);
        ui.lSearchParent.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.lSearchParent.rv.setHasFixedSize(true);
        ui.lSearchParent.rv.setAdapter(adapter);
        ui.lSearchParent.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!ui.lSearchParent.rv.canScrollVertically(-1)) {
                    //top
                } else if (!ui.lSearchParent.rv.canScrollVertically(1)) {
                    //end
                    if(adapter.getItemCount()>19) searchAddress();
                } else {
                    //idle
                }
            }
        });

        ui.lSearchParent.etSearch.setOnEditorActionListener(editorActionListener);
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){

            case R.id.l_whole:
                AddressZipVO addressZipVO = (AddressZipVO)v.getTag(R.id.addr);
                setResult(ResultCodes.REQ_CODE_ADDR_ZIP.getCode(), new Intent().putExtra(KeyNames.KEY_NAME_ZIP_ADDR, addressZipVO));
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
                        int itemSizeBefore = adapter.getItemCount();
                        if (adapter.getPageNo() == 0) {
                            adapter.setRows(result.data.getZipList());
                        } else {
                            adapter.addRows(result.data.getZipList());
                        }
                        adapter.setPageNo(adapter.getPageNo() + 1);
                        adapter.notifyItemRangeInserted(itemSizeBefore, adapter.getItemCount());

                    }

                    if (adapter != null && adapter.getItemCount() < 1) {
                        ui.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
                    } else {
                        ui.lSearchParent.tvEmpty.setVisibility(View.GONE);
                    }


                    break;
                default:
                    showProgressDialog(false);
                    break;

            }

        });

    }

    @Override
    public void getDataFromIntent() {

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


//    private void reqListData(String keyword) {
//        if (TextUtils.isEmpty(keyword)) {
//            ui.lSearchParent.etSearch.setBackgroundResource(R.drawable.bg_ffffff_stroke_dadde3);
//            ui.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
//        } else {
//            ui.lSearchParent.etSearch.setBackgroundResource(R.drawable.bg_ffffff_stroke_141414);
//            try {
//                setListView(cmnViewModel.getNotiInfoFromDB("", "%"+keyword+"%"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//
//            }
//        }
//    }

//    /**
//     * @brief 키보드에서 search 버튼 클릭할 경우 정의
//     * (스토리보드에 정의되어 있지 않아 제거)
//     *
//     */
    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if(actionId== EditorInfo.IME_ACTION_SEARCH){
            searchAddress();
        }
        return false;
    };


    private void searchAddress(){
        //end
        String keyword = ui.lSearchParent.etSearch.getText().toString().trim();
        if(!TextUtils.isEmpty(keyword))
            pubViewModel.reqPUB1001(new PUB_1001.Request(APPIAInfo.GM_CARLST_01_A01.getId(), keyword, adapter.getPageNo() + 1 + "", "20"));

    }

}
