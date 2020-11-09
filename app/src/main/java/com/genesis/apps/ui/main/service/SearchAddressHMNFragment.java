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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.activity_search_address);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewArgument();
    }

    private void setViewArgument() {
        try {
            int titleId = getArguments().getInt(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, 0);
            int msgId = getArguments().getInt(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, 0);
            if (titleId != 0) {
                me.lTitle.setValue(getString(titleId));
            }
            if (msgId != 0) {
                me.tvMsg.setVisibility(View.VISIBLE);
                me.tvMsg.setText(getString(msgId));
            }
        }catch (Exception ignore){

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
        me.lTitle.back.setOnClickListener(onSingleClickListener);
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
    public boolean onBackPressed() {
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
                            ((MapSearchMyPositionActivity)getActivity()).setAddressInfo(addressVO);
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
                            int position = adapter.getPosition(addressVO.getAddrRoad());
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
