package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.MapViewModel;
import com.genesis.apps.databinding.ActivitySearchAddressBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchAddressHMNFragment extends SubFragment<ActivitySearchAddressBinding> {
    private SearchAddressHMNAdapter adapter;
    private MapViewModel mapViewModel;
    private LGNViewModel lgnViewModel;

    public View.OnClickListener onClickListener = view -> onClickCommon(view);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.activity_search_address);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setLifecycleOwner(getViewLifecycleOwner());
        mapViewModel = new ViewModelProvider(getActivity()).get(MapViewModel.class);
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
        initView();

        mapViewModel.getPlayMapPoiItemList().observe(getViewLifecycleOwner(), result -> {

            switch (result.status){
                case LOADING:
                    ((SubActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    if(result.data!=null&&result.data.size()>0){
                        List<AddressVO> list = new Gson().fromJson( new Gson().toJson(result.data), new TypeToken<List<AddressVO>>(){}.getType());
                        adapter.setRows(list);
                        adapter.notifyDataSetChanged();
                    }
                default:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    if (adapter != null && adapter.getItemCount() < 1) {
                        me.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
                    } else {
                        me.lSearchParent.tvEmpty.setVisibility(View.GONE);
                    }
                    break;

            }
        });
    }

    private void initView() {
        adapter = new SearchAddressHMNAdapter(onSingleClickListener);
        me.lSearchParent.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        me.lSearchParent.rv.setHasFixedSize(true);
        me.lSearchParent.rv.setAdapter(adapter);
//        me.lSearchParent.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (!me.lSearchParent.rv.canScrollVertically(-1)) {
//                    //top
//                } else if (!me.lSearchParent.rv.canScrollVertically(1)) {
//                    //end
//                    if(adapter.getItemCount()>19) searchAddress();
//                } else {
//                    //idle
//                }
//            }
//        });

        me.lSearchParent.etSearch.setOnEditorActionListener(editorActionListener);
        me.lSearchParent.etSearch.setHint(R.string.gm_carlst_02_30);
        me.lSearchParent.tvTitleSub.setText(R.string.mg00_word_3);
        me.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onBackPressed() {
        return true;
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.l_whole:
//                AddressZipVO addressZipVO = (AddressZipVO)v.getTag(R.id.addr);
//                setResult(ResultCodes.REQ_CODE_ADDR_ZIP.getCode(), new Intent().putExtra(KeyNames.KEY_NAME_ZIP_ADDR, addressZipVO));
//                finish();
                break;
        }
    }


    @Override
    public void onRefresh() {

    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if(actionId== EditorInfo.IME_ACTION_SEARCH){
            searchAddress();
        }
        return false;
    };


    private void searchAddress(){
        //end
        String keyword = me.lSearchParent.etSearch.getText().toString().trim();
        if(!TextUtils.isEmpty(keyword)) {
            mapViewModel.reqFindAllPOI(keyword, lgnViewModel.getMyPosition().get(0), lgnViewModel.getMyPosition().get(1), 3, "", 0, 20, 3);
        }else{
            me.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
        }
        SoftKeyboardUtil.hideKeyboard(getActivity(), getActivity().getWindow().getDecorView().getWindowToken());
    }



}
