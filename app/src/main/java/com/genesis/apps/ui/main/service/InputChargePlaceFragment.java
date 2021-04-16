package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.databinding.FragmentInputChargePlaceBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;

import java.util.ArrayList;

/**
 * Class Name : InputChargePlaceFragment
 * 충전소 검색 입력 및 필터 Fragment.
 *
 * @author Ki-man Kim
 * @since 2021-04-09
 */
public class InputChargePlaceFragment extends SubFragment<FragmentInputChargePlaceBinding> {
    private ChargeSearchFilterAdapter adapter;

    public static InputChargePlaceFragment newInstance() {
        Bundle args = new Bundle();
        InputChargePlaceFragment fragment = new InputChargePlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private InputChargePlaceFragment() {
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_input_charge_place);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(InputChargePlaceFragment.this);
        initView();
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
        switch (v.getId()) {
            // 필터 열기/닫기 버튼
            case R.id.tv_btn_filter: {
                v.setSelected(!v.isSelected());
                me.rvFilter.setVisibility(v.isSelected() ? View.VISIBLE : View.GONE);
                break;
            }
            // 필터 선택 초기화 버튼
            case R.id.tv_btn_filter_reset: {
                adapter.clearCheckState();
                break;
            }
        }
    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void initView() {
        ArrayList<ChargeSearchFilterAdapter.DummyFilterData> testlist = new ArrayList<>();
        testlist.add(new ChargeSearchFilterAdapter.DummyFilterData().setName("제네시스 전용 충전소"));
        testlist.add(new ChargeSearchFilterAdapter.DummyFilterData().setName("예약가능 충전소"));
        testlist.add(new ChargeSearchFilterAdapter.DummyFilterData().setName("E-PIT 충전소"));
        testlist.add(new ChargeSearchFilterAdapter.DummyFilterData().setName("초고속"));
        testlist.add(new ChargeSearchFilterAdapter.DummyFilterData().setName("급속"));
        testlist.add(new ChargeSearchFilterAdapter.DummyFilterData().setName("완속"));
        testlist.add(new ChargeSearchFilterAdapter.DummyFilterData().setName("PNC 결제 가능"));
        testlist.add(new ChargeSearchFilterAdapter.DummyFilterData().setName("카페이 결제 가능"));

        adapter = new ChargeSearchFilterAdapter();
        adapter.setRows(testlist);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        me.rvFilter.setLayoutManager(layoutManager);
        me.rvFilter.setAdapter(adapter);
    }
} // end of class InputChargePlaceFragment
