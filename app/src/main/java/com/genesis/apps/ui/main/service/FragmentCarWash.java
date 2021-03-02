package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.InteractionUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.FragmentServiceCarWashBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

public class FragmentCarWash extends SubFragment<FragmentServiceCarWashBinding> {
    private static final String TAG = FragmentCarWash.class.getSimpleName();

    private LGNViewModel lgnViewModel;

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
        setObserver();
    }

    private void setOnSingleClickListener() {
        me.lServiceCarWashHistoryBtn.lServiceCarWash.setOnClickListener(onSingleClickListener);
        me.lServiceCarWashRequestBtn.lServiceCarWash.setOnClickListener(onSingleClickListener);
        me.llCost.setOnClickListener(onSingleClickListener);
    }

    private void setViewModel() {
        me.setLifecycleOwner(getViewLifecycleOwner());
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
    }

    private void setObserver() {

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
        int id = v.getId();
        Log.d(TAG, "onClickCommon: view id :" + id);

        if(v.getId() == R.id.ll_cost) {
            if(me.clSonaxCostImg.getVisibility() == View.GONE) {
                me.ivArrow.setImageResource(R.drawable.btn_arrow_close);
                InteractionUtil.expand2(me.clSonaxCostImg, new Runnable() {
                    @Override
                    public void run() {
                        me.nsScroll.smoothScrollTo(0, me.llCost.getTop());
                    }
                });

            } else {
                me.ivArrow.setImageResource(R.drawable.btn_arrow_open);
                InteractionUtil.collapse(me.clSonaxCostImg, null, 200);
            }
        } else {
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
                    ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), CarWashRequestActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);

                    break;

                default:
                    //do nothing
                    break;
            }
        }
    }
}
