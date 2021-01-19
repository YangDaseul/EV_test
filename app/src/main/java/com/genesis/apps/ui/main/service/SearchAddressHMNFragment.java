package com.genesis.apps.ui.main.service;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.MapViewModel;
import com.genesis.apps.databinding.ActivitySearchAddressBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchAddressHMNFragment extends SubFragment<ActivitySearchAddressBinding> {
    private SearchAddressHMNAdapter adapter;
    private MapViewModel mapViewModel;
    private LGNViewModel lgnViewModel;
    int titleId=0;
    int msgId=0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme);
        LayoutInflater localInflater = getActivity().getLayoutInflater().cloneInContext(contextThemeWrapper);
//        View v = localInflater.inflate(R.layout.fragment_work_detail, container, false);

        return super.setContentView(localInflater, R.layout.activity_search_address);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        me.setActivity((SubActivity) getActivity());

        setViewArgument();
    }

    private void setViewArgument() {
        try {
             titleId = getArguments().getInt(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, 0);
             msgId = getArguments().getInt(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, 0);

            switch (titleId) {
                case R.string.service_drive_address_search_from_title://대리운전 출발지
                    me.llMyPosition.setVisibility(View.VISIBLE);

                    break;
                case R.string.service_drive_address_search_to_title://대리운전 도착지
                case 0:
                default://그 외
                    me.llMyPosition.setVisibility(View.GONE);
            }
        }catch (Exception ignore){

        }
    }
    private void setViewMsg(){
        if (titleId != 0) {
            me.lTitle.setValue(getString(titleId));
        }else{
            me.lTitle.setValue(getString(R.string.gm_carlst_01_01_1));
        }
        if (msgId != 0) {
            me.tvMsg.setVisibility(View.VISIBLE);
            me.tvMsg.setText(getString(msgId));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setLifecycleOwner(getViewLifecycleOwner());
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);

        mapViewModel.getPlayMapPoiItemList().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                switch (result.status) {
                    case LOADING:
                        ((SubActivity) getActivity()).showProgressDialog(true);
                        break;
                    case SUCCESS:
                        ((SubActivity) getActivity()).showProgressDialog(false);
                        if (result.data != null && result.data.size() > 0) {
                            List<AddressVO> list = new Gson().fromJson(new Gson().toJson(result.data), new TypeToken<List<AddressVO>>() {
                            }.getType());
                            setListView(SearchAddressHMNAdapter.TYPE_NORMAL, list);
                            break;
                        }
                    default:
                        ((SubActivity) getActivity()).showProgressDialog(false);
                        setListView(SearchAddressHMNAdapter.TYPE_NORMAL, new ArrayList<>());
                        break;
                }
            }
        });

        initView();
    }

    private void initView() {
        adapter = new SearchAddressHMNAdapter(onSingleClickListener);
        me.lSearchParent.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        me.lSearchParent.rv.setHasFixedSize(true);
        me.lSearchParent.rv.setAdapter(adapter);
        me.lSearchParent.etSearch.setOnEditorActionListener(editorActionListener);
        me.lSearchParent.etSearch.setHint(R.string.map_title_3);
        me.lSearchParent.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0){
                    me.lSearchParent.etSearch.setBackgroundResource(R.drawable.bg_ffffff_stroke_e5e5e5);
                }else{
                    me.lSearchParent.etSearch.setBackgroundResource(R.drawable.bg_ffffff_stroke_000000);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        me.lTitle.back.setOnClickListener(onSingleClickListener);
        SoftKeyboardUtil.showKeyboard(getContext());
        setViewMsg();
        reqRecentlyData();
    }

    private void reqRecentlyData(){
        List<AddressVO> list = new ArrayList<>();
        try {
            ((SubActivity) getActivity()).showProgressDialog(true);
            list = mapViewModel.getRecentlyAddressVO();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ((SubActivity) getActivity()).showProgressDialog(false);
            setListView(SearchAddressHMNAdapter.TYPE_RECENTLY, list);
        }
    }

    private void setListView(int type, List<AddressVO> list) {
        me.lSearchParent.tvTitleSub.setText(type == SearchAddressHMNAdapter.TYPE_RECENTLY ? R.string.mg00_word_2 : R.string.mg00_word_3);
        adapter.setType(type);
        adapter.setRows(list);
        adapter.notifyDataSetChanged();
        setEmptyView();
    }

    private void setEmptyView(){
        if (adapter != null && adapter.getItemCount() < 1) {
            me.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            me.lSearchParent.tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onBackPressed(){
        return true;
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.back:
                ((SubActivity) getActivity()).hideFragment(this);
                break;
            case R.id.l_whole:
                try {
                    AddressVO addressVO = (AddressVO) v.getTag(R.id.addr);
                    addressVO.set_id(0);
                    if (addressVO != null) {
                        if (mapViewModel.insertRecentlyAddressVO(addressVO)) {
                            ((SubActivity)getActivity()).hideFragment(this);
                            if(getActivity() instanceof ServiceRelapseApply1Activity){ //TODO 리펙토링..
                                ((ServiceRelapseApply1Activity)getActivity()).setAddressInfo(addressVO);
                            }else{
                                ((MapSearchMyPositionActivity)getActivity()).setAddressInfo(addressVO);
                            }

                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }finally{

                }
                break;
            case R.id.btn_del:
                try {
                    AddressVO addressVO = (AddressVO) v.getTag(R.id.addr);
                    if (addressVO != null) {
                        if (mapViewModel.deleteRecentlyAddressVO(addressVO)) {
                            int position = adapter.getPosition(addressVO);
                            if(position>-1){
                                adapter.remove(position);
                                adapter.notifyItemRemoved(position);
                            }
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }finally{
                    setEmptyView();
                }
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
        SoftKeyboardUtil.hideKeyboard(getActivity(), getActivity().getWindow().getDecorView().getWindowToken());
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
