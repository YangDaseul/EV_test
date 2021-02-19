package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.WSH_1001;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.WashGoodsVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.FragmentServiceCarWashBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.activity.GAWebActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.concurrent.ExecutionException;

public class FragmentCarWash extends SubFragment<FragmentServiceCarWashBinding> {
    private static final String TAG = FragmentCarWash.class.getSimpleName();

    private WSHViewModel viewModel;
    private LGNViewModel lgnViewModel;
    private FragmentCarWashAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        return super.setContentView(inflater, R.layout.fragment_service_car_wash);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        setOnSingleClickListener();
        setViewModel();
        setAdapter();
        setObserver();
    }

    private void setOnSingleClickListener() {
        me.lServiceCarWashHistoryBtn.lServiceCarWash.setOnClickListener(onSingleClickListener);
        me.lServiceCarWashRequestBtn.lServiceCarWash.setOnClickListener(onSingleClickListener);
    }

    private void setViewModel() {
        me.setLifecycleOwner(getViewLifecycleOwner());
        viewModel = new ViewModelProvider(getActivity()).get(WSHViewModel.class);
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
    }

    private void setObserver() {
        //세차 쿠폰 목록
        viewModel.getRES_WSH_1001().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((MainActivity) getActivity()).showProgressDialog(true);
                    break;

                case SUCCESS:
                    //TODO 2020-12-08 가격표 URI가 추가되어 수정 필요
                    if (result.data != null && result.data.getGodsList() != null) {
                        adapter.setRows(result.data.getGodsList());
                        adapter.notifyDataSetChanged();

                        //성공 후 데이터 로딩까지 다 되면 로딩 치우고 break;
                        ((MainActivity) getActivity()).showProgressDialog(false);
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
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
    }

    @Override
    public void onRefresh() {
        try {
            viewModel.reqWSH1001(new WSH_1001.Request(APPIAInfo.SM_CW01_A01.getId(), WSHViewModel.SONAX, lgnViewModel.getMainVehicleFromDB().getVin()));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        int id = v.getId();
        Log.d(TAG, "onClickCommon: view id :" + id);

        try {
            if (!((FragmentService) getParentFragment()).checkCustGbCd(id, lgnViewModel.getUserInfoFromDB().getCustGbCd()))
                return;
        } catch (Exception e) {

        }

        switch (id) {
            //세차 서비스 예약내역 버튼
            case R.id.l_service_car_wash_history_btn:
                ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), CarWashHistoryActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
//            case R.id.l_service_car_wash_cost_btn:
//                String url = "";
//                try{
//                    url = viewModel.getRES_WSH_1001().getValue().data.getGodsCostUri();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }finally {
//                    if(TextUtils.isEmpty(url)){
//                        SnackBarUtil.show(getActivity(), "가격 정보가 존재하지 않습니다.");
//                    }else{
//                        ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), GAWebActivity.class).putExtra(KeyNames.KEY_NAME_URL, url).putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, R.string.sm01_car_wash_4), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                    }
//                }
//                break;
            case R.id.l_service_car_wash_request_btn:

                break;
            //세차 쿠폰 선택(지점 검색(지도) 액티비티 열기)
            case R.id.l_service_car_wash_item:
                //선택한 쿠폰 정보를 새 액티비티에 가지고 가야 한다
                String godsSeqNo = ((WashGoodsVO) v.getTag(R.id.tag_wash_item)).getGodsSeqNo();
                String godsNm = ((WashGoodsVO) v.getTag(R.id.tag_wash_item)).getGodsNm();

                Intent intent = new Intent(getActivity(), CarWashSearchActivity.class);
                intent.putExtra(KeyNames.KEY_NAME_WASH_GOODS_SEQ_NUM, godsSeqNo);
                intent.putExtra(KeyNames.KEY_NAME_WASH_GOODS_NAME, godsNm);

                ((BaseActivity) getActivity()).startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            default:
                //do nothing
                break;
        }
    }

    private void setAdapter() {
        //세차 아이템 어댑터 (인스턴스 타입 맞나 확인)
        adapter = new FragmentCarWashAdapter(onSingleClickListener);
        me.rvServiceCarWashItemList.setLayoutManager(new LinearLayoutManager(getContext()));
        me.rvServiceCarWashItemList.setHasFixedSize(true);
        me.rvServiceCarWashItemList.setAdapter(adapter);
    }
}
