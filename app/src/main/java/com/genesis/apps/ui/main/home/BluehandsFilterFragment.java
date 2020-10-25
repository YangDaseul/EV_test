package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.PUB_1002;
import com.genesis.apps.comm.model.gra.api.PUB_1003;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.PUBViewModel;
import com.genesis.apps.databinding.FragmentBluehandsFilterBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BluehandsFilterFragment extends SubFragment<FragmentBluehandsFilterBinding> {
    private PUBViewModel pubViewModel;
    private final int[] filterIds = {R.id.tv_category_1, R.id.tv_category_3, R.id.tv_category_4, R.id.tv_category_2};
    private final boolean[] isSelectFilter = {false, false, false, false};
    private String fillerCd = "";
    private String addr = "";
    private String addrDtl = "";
    public View.OnClickListener onClickListener = view -> onClickCommon(view);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_bluehands_filter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);
        pubViewModel = new ViewModelProvider(getActivity()).get(PUBViewModel.class);

        pubViewModel.getRES_PUB_1002().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    me.tvPosition1.setText(R.string.bt06_4);
                    me.tvPosition1.setTextAppearance(R.style.BtrPositionDisable);
                    me.tvPosition2.setText(R.string.bt06_5);
                    me.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
                    addr = "";
                    addrDtl = "";
                    break;
                default:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    break;
            }
        });
        pubViewModel.getRES_PUB_1003().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    break;
                default:
                    me.tvPosition1.setText(R.string.bt06_4);
                    me.tvPosition1.setTextAppearance(R.style.BtrPositionDisable);
                    me.tvPosition2.setText(R.string.bt06_5);
                    me.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    addr = "";
                    addrDtl = "";
                    break;
            }
        });

        if(pubViewModel.getRES_PUB_1002().getValue()!=null&&pubViewModel.getFilterInfo().getValue()!=null) {
            new Handler().postDelayed(() -> updateView(),100);
        }else {
            pubViewModel.reqPUB1002(new PUB_1002.Request(APPIAInfo.GM_BT06_01.getId()));
        }
    }

    private void updateView() {

        List<String> filterInfo = pubViewModel.getFilterInfo().getValue();

        try {
            fillerCd = filterInfo.get(0);
        } catch (Exception e) {
            fillerCd = "";
        }finally {
            setFilterView();
        }
        try {
            addr = filterInfo.get(1);
        } catch (Exception e) {
            addr = "";
        }finally {
            if(!TextUtils.isEmpty(addr)){
                me.tvPosition1.setTextAppearance(R.style.BtrPositionEnable);
                me.tvPosition1.setText(addr);
            }
        }
        try {
            addrDtl = filterInfo.get(2);
        } catch (Exception e) {
            addrDtl = "";
        }finally {
            if(!TextUtils.isEmpty(addrDtl)){
                me.tvPosition2.setTextAppearance(R.style.BtrPositionEnable);
                me.tvPosition2.setText(addrDtl);
            }
        }

    }


    @Override
    public boolean onBackPressed() {
        return true;
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.tv_category_1:
            case R.id.tv_category_2:
            case R.id.tv_category_3:
            case R.id.tv_category_4:
                setFilter(v.getId());
                break;
            case R.id.tv_position_1:
                List<String> listSidoNm = pubViewModel.getAddrNm();
                showMapDialog(v.getId(), listSidoNm, R.string.gm_carlst_p01_9);
                break;
            case R.id.tv_position_2:
                List<String> listGuNm = pubViewModel.getAddrGuNm();
                showMapDialog(v.getId(), listGuNm, R.string.gm_carlst_p01_10);
                break;
            case R.id.btn_apply:
                pubViewModel.setFilterInfo(getFillerCd(), addr, addrDtl);
                ((SubActivity)getActivity()).hideFragment(this);
//                getActivity().setResult(ResultCodes.REQ_CODE_ADDR_FILTER.getCode(), new Intent().putExtra(KeyNames.KEY_NAME_MAP_FILTER, getFillerCd()).putExtra(KeyNames.KEY_NAME_MAP_CITY, addr).putExtra(KeyNames.KEY_NAME_MAP_GU, addrDtl));
//                finish();
                break;
        }
    }

    private void showMapDialog(int id, List<String> list, int title) {
        if (list != null && list.size() > 0) {
            final BottomListDialog bottomListDialog = new BottomListDialog(getContext(), R.style.BottomSheetDialogTheme);
            bottomListDialog.setOnDismissListener(dialogInterface -> {
                String selectItem = bottomListDialog.getSelectItem();
                if (!TextUtils.isEmpty(selectItem)) {
                    selectItem(selectItem, id);
                }
            });
            bottomListDialog.setDatas(list);
            bottomListDialog.setTitle(getString(title));
            bottomListDialog.show();
        } else {
            SnackBarUtil.show(getActivity(), id == R.id.tv_position_2 ? "시/도가 선택되지 않았습니다.\n시/도 정보를 선택 후 다시 시도해 주세요." : "시/도 정보가 없습니다.\n네트워크 상태를 확인 후 다시 시도해 주십시오.");
        }
    }

    private void selectItem(String selectNm, int id) {
        switch (id) {
            case R.id.tv_position_1:
                if (!me.tvPosition1.getText().toString().equalsIgnoreCase(selectNm)) {
                    me.tvPosition1.setText(selectNm);
                    me.tvPosition1.setTextAppearance(R.style.BtrPositionEnable);
                    me.tvPosition2.setText(R.string.bt06_5);
                    me.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
                    addr = selectNm;
                    addrDtl = "";
                    pubViewModel.reqPUB1003(new PUB_1003.Request(APPIAInfo.GM_BT06_01.getId(), pubViewModel.getSidoCode(selectNm)));
                }
                break;
            case R.id.tv_position_2:
                if (!me.tvPosition2.getText().toString().equalsIgnoreCase(selectNm)) {
                    me.tvPosition2.setText(selectNm);
                    me.tvPosition2.setTextAppearance(R.style.BtrPositionEnable);
                    addrDtl = selectNm;
                }
                break;
        }
    }

    private void setFilterView(){
        for (int i = 0; i < filterIds.length; i++) {

            String filterCd = ((TextView) getActivity().findViewById(filterIds[i])).getTag().toString();

            if(fillerCd.contains(filterCd)){
                isSelectFilter[i]=true;
                ((TextView) getActivity().findViewById(filterIds[i])).setTextAppearance(R.style.BtrFilterEnable);
                ((TextView) getActivity().findViewById(filterIds[i])).setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_141414_2dp);
            }
        }

    }


    private void setFilter(int selectId) {
        for (int i = 0; i < filterIds.length; i++) {

            if(selectId == filterIds[i]){
                if(isSelectFilter[i]){
                    isSelectFilter[i]=false;
                    ((TextView) getActivity().findViewById(filterIds[i])).setTextAppearance(R.style.BtrFilterDisable);
                    ((TextView) getActivity().findViewById(filterIds[i])).setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_4d141414);
                }else{
                    isSelectFilter[i]=true;
                    ((TextView) getActivity().findViewById(filterIds[i])).setTextAppearance(R.style.BtrFilterEnable);
                    ((TextView) getActivity().findViewById(filterIds[i])).setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_141414_2dp);
                }
                break;
            }
        }
    }

    private String getFillerCd() {
        fillerCd = "";
        for (int i = 0; i < filterIds.length; i++) {
            if (isSelectFilter[i]) {
                fillerCd += ((TextView) getActivity().findViewById(filterIds[i])).getTag().toString();
            }
        }
        return fillerCd;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    }



}
