package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.PUB_1002;
import com.genesis.apps.comm.model.api.gra.WSH_1002;
import com.genesis.apps.comm.model.vo.AddressCityVO;
import com.genesis.apps.comm.model.vo.WashBrnVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.PUBViewModel;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.FragmentCarWashFindSonaxBranchBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static com.genesis.apps.ui.main.service.CarWashSearchActivity.LATITUDE;
import static com.genesis.apps.ui.main.service.CarWashSearchActivity.LONGITUDE;

public class CarWashFindSonaxBranchFragment extends SubFragment<FragmentCarWashFindSonaxBranchBinding> {
    private static final String TAG = CarWashFindSonaxBranchFragment.class.getSimpleName();

    private WSHViewModel wshViewModel;
    private PUBViewModel pubViewModel;
    private CarWashFindSonaxBranchAdapter adapter;

    private List<String> areaList;
    private boolean showDialog = false;
    private BottomListDialog areaListDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_car_wash_find_sonax_branch);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setViewModel();
        setAdapter();
        setObserver();

        //?????? ????????? ????????? ?????? ?????? ??????
        reqAreaList();
    }

    public void setViewModel() {
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);
        wshViewModel = new ViewModelProvider(this).get(WSHViewModel.class);
        pubViewModel = new ViewModelProvider(this).get(PUBViewModel.class);
    }

    public void setObserver() {
        //?????? ?????? ?????????
        pubViewModel.getRES_PUB_1002().observe(getViewLifecycleOwner(), result -> {
            Log.d(TAG, "setObserver AreaObs: " + result.status);

            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getSidoList() != null) {
                        //?????? ????????? ??????
                        setAreaList(result.data.getSidoList());

                        //???????????? ???????????? ????????? ????????? ????????? ??????(????????? false),
                        // ??? ??? ??????????????? ?????? ????????? ?????? ???????????? ?????????????????? ?????? ?????????(true??? ???????????? ?????????).
                        if (showDialog) {
                            showAreaDialog();
                        }

                        //?????? ??? ????????? ???????????? ??? ?????? ?????? ????????? break;
                        ((SubActivity) getActivity()).showProgressDialog(false);
                        break;
                    }
                    //not break; ????????? ???????????? default??? ????????????

                default:
                    setAreaList(null);
                    ((MainActivity) getActivity()).showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if (TextUtils.isEmpty(serverMsg)){
                            serverMsg = getString(R.string.instability_network);
                        }
                        SnackBarUtil.show(getActivity(), serverMsg);
                    }
                    break;
            }
        });

        //?????? ?????? ?????? ?????? ?????????
        wshViewModel.getRES_WSH_1002().observe(getViewLifecycleOwner(), result -> {
            Log.d(TAG, "setObserver branchObs: " + result.status);

            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getBrnhList() != null) {
                        List<WashBrnVO> list = result.data.getBrnhList();
                        adapter.setRows(list);
                        adapter.notifyDataSetChanged();

                        setResultCount(list.size());

                        //?????? ??? ????????? ???????????? ??? ?????? ?????? ????????? break;
                        ((SubActivity) getActivity()).showProgressDialog(false);
                        break;
                    }
                    //?????? ????????? ????????? ?????????
                    setResultCount(0);

                    //not break; ????????? ???????????? default??? ????????????

                default:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if (TextUtils.isEmpty(serverMsg)){
                            serverMsg = getString(R.string.instability_network);
                        }
                        SnackBarUtil.show(getActivity(), serverMsg);
                    }
                    break;
            }

        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon()");
        int id = v.getId();

        switch (id) {
            //?????? ?????? ??????
            case R.id.tv_car_wash_find_branch_location_select:
                Log.d(TAG, "onClickCommon(areaList): " + areaList);
                if (areaList == null) {
                    //???????????? ??? ??? ???????????? ????????? ??????????????? ?????????(?????? ??? ?????? ?????? ????????? ???????????? ??????????????? ??????).
                    showDialog = true;
                    reqAreaList();
                } else {
                    //????????? ??????????????? ????????? ????????? ??????
                    showAreaDialog();
                }
                break;

            //?????? ??????
            case R.id.l_map_find_result_item:
                ((CarWashSearchActivity) getActivity()).setBranchList(new ArrayList<>(adapter.getItems()));
                ((CarWashSearchActivity) getActivity()).showBranchInfo(adapter.getItem((int) v.getTag(R.id.item_position)), true);

                ((SubActivity) getActivity()).hideFragment(this);
                return;

            default:
                //do nothing
                break;
        }

    }

    //?????? ?????? ?????? ?????????. ?????? ????????? ?????? ??????????????? ?????? ?????? ?????? ?????? ???????????? ???
    private void setResultCount(int count) {
        me.setResultCount(count);
        me.lSonaxFindList.setResultCount("" + count);
    }

    private void setAdapter() {
        //?????? ?????? ????????? (???????????? ?????? ?????? ??????)
        adapter = new CarWashFindSonaxBranchAdapter(onSingleClickListener);
        me.lSonaxFindList.rvMapFindResultList.setLayoutManager(new LinearLayoutManager(getContext()));
        me.lSonaxFindList.rvMapFindResultList.setHasFixedSize(true);
        me.lSonaxFindList.rvMapFindResultList.setAdapter(adapter);
    }

    //?????? ?????? ??????
    private void reqAreaList() {
        Log.d(TAG, "reqAreaList: ");
        pubViewModel.reqPUB1002(new PUB_1002.Request(APPIAInfo.SM_CW01_A02.getId()));
    }

    //AddressCityVO List ??? ?????? ???????????? ???????????? ????????? ????????? string ????????? ??????
    private void setAreaList(List<AddressCityVO> list) {
        Log.d(TAG, "setAreaList: " + list);
        if (list == null) {
            areaList = null;
            return;
        }

        areaList = new ArrayList<>();
        for (AddressCityVO areaData : list) {
            areaList.add(areaData.getSidoNm());
        }
    }

    //?????? ?????? ???????????????
    private void showAreaDialog() {
        Log.d(TAG, "showAreaDialog: " + areaListDialog);
        if (areaListDialog == null) {
            areaListDialog = new BottomListDialog(getContext(), R.style.BottomSheetDialogTheme);
            areaListDialog.setTitle(getString(R.string.cw_branch_find_area));
            areaListDialog.setDatas(areaList);
            areaListDialog.setOnDismissListener(
                    dialog -> {
                        if (areaListDialog.getSelectItem() != null) {
                            me.tvCarWashFindBranchLocationSelect.setText(areaListDialog.getSelectItem());
                            reqBranchListInArea(areaListDialog.getSelectItem());
                        }
                    }
            );
        }

        areaListDialog.setSelectItem(null);
        areaListDialog.show();
    }

    //?????? ????????? ?????? ?????? ??????
    private void reqBranchListInArea(String area) {
        Log.d(TAG, "reqBranchListInArea: ");

        String godsSeqNo = ((CarWashSearchActivity) getActivity()).getGodsSeqNo();
        double[] gps = ((CarWashSearchActivity) getActivity()).getMyPosition();

        wshViewModel.reqWSH1002(
                new WSH_1002.Request(
                        APPIAInfo.SM_CW01_A02.getId(),
                        godsSeqNo,
                        WSHViewModel.SONAX,
                        "" + gps[LONGITUDE],
                        "" + gps[LATITUDE],
                        area));
    }
}
