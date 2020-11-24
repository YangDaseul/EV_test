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

        //지역 목록에 표시할 내용 미리 로딩
        reqAreaList();
    }

    public void setViewModel() {
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);
        wshViewModel = new ViewModelProvider(this).get(WSHViewModel.class);
        pubViewModel = new ViewModelProvider(this).get(PUBViewModel.class);
    }

    public void setObserver() {
        //지역 목록 옵저버
        pubViewModel.getRES_PUB_1002().observe(getViewLifecycleOwner(), result -> {
            Log.d(TAG, "setObserver AreaObs: " + result.status);

            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getSidoList() != null) {
                        //지역 목록을 저장
                        setAreaList(result.data.getSidoList());

                        //액티비티 실행직후 로딩인 경우는 가만히 있고(초기값 false),
                        // 이 때 실패했다가 다시 호출할 때는 지역선택 다이얼로그를 바로 띄운다(true로 바꾸면서 호출함).
                        if (showDialog) {
                            showAreaDialog();
                        }

                        //성공 후 데이터 로딩까지 다 되면 로딩 치우고 break;
                        ((SubActivity) getActivity()).showProgressDialog(false);
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

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
                    //todo : 구체적인 예외처리
                    break;
            }
        });

        //지정 지역 지점 목록 옵저버
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

                        //성공 후 데이터 로딩까지 다 되면 로딩 치우고 break;
                        ((SubActivity) getActivity()).showProgressDialog(false);
                        break;
                    }
                    //결과 없으면 카운트 초기화
                    setResultCount(0);

                    //not break; 데이터 이상하면 default로 진입시킴

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
                    //todo : 구체적인 예외처리
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
            //지역 선택 버튼
            case R.id.tv_car_wash_find_branch_location_select:
                Log.d(TAG, "onClickCommon(areaList): " + areaList);
                if (areaList == null) {
                    //액티비티 켤 때 지역목록 로딩에 실패했으면 재시도(로딩 후 바로 지역 선택창 띄우도록 플래그부터 세팅).
                    showDialog = true;
                    reqAreaList();
                } else {
                    //로딩된 지역목록이 있으면 선택창 띄움
                    showAreaDialog();
                }
                break;

            //지점 선택
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

    //검색 결과 갯수 바인딩. 결과 없으면 목록 사라지도록 하는 거랑 결과 수를 표시하는 거
    private void setResultCount(int count) {
        me.setResultCount(count);
        me.lSonaxFindList.setResultCount("" + count);
    }

    private void setAdapter() {
        //지점 검색 어댑터 (인스턴스 타입 맞나 확인)
        adapter = new CarWashFindSonaxBranchAdapter(onSingleClickListener);
        me.lSonaxFindList.rvMapFindResultList.setLayoutManager(new LinearLayoutManager(getContext()));
        me.lSonaxFindList.rvMapFindResultList.setHasFixedSize(true);
        me.lSonaxFindList.rvMapFindResultList.setAdapter(adapter);
    }

    //지역 목록 요청
    private void reqAreaList() {
        Log.d(TAG, "reqAreaList: ");
        pubViewModel.reqPUB1002(new PUB_1002.Request(APPIAInfo.SM_CW01_A02.getId()));
    }

    //AddressCityVO List 로 받은 목록에서 행정구역 이름만 꺼내서 string 목록에 저장
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

    //지역 선택 다이얼로그
    private void showAreaDialog() {
        Log.d(TAG, "showAreaDialog: " + areaListDialog);
        if (areaListDialog == null) {
            areaListDialog = new BottomListDialog(getContext(), R.style.BottomSheetDialogTheme);
            areaListDialog.setTitle(getString(R.string.cw_branch_find_area));
            areaListDialog.setDatas(areaList);
            areaListDialog.setOnDismissListener(
                    dialog -> {
                        if (areaListDialog.getSelectItem() != null) {
                            reqBranchListInArea(areaListDialog.getSelectItem());
                        }
                    }
            );
        }

        areaListDialog.setSelectItem(null);
        areaListDialog.show();
    }

    //지정 지역의 지점 목록 요청
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
