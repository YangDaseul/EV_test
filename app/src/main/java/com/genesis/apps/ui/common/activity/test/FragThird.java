package com.genesis.apps.ui.common.activity.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.ExampleResVO;
import com.genesis.apps.comm.model.ExampleViewModel;
import com.genesis.apps.databinding.Frame3pBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.common.view.listview.test.TestItemAdapter;

import java.util.ArrayList;


public class FragThird extends SubFragment<Frame3pBinding> {
    TestItemAdapter testItemAdapter;
    ExampleViewModel exampleViewModel;

    //TODO : 레이아웃 짤 때 이 테스트 목록에 추가하기
    private final int[] testLayoutList = {
            R.layout.fragment_service, //todo 스크롤 범위
            R.layout.fragment_service_maintenance, //todo 스크롤 범위
            R.layout.fragment_service_maintenance_item, //todo 스크롤 범위

            R.layout.fragment_service_car_wash, //todo 스크롤 범위
            R.layout.fragment_service_car_wash_item,
            R.layout.activity_car_wash_find_sonax_branch, //todo 스크롤 범위
            R.layout.fragment_car_wash_find_result,
            R.layout.fragment_car_wash_find_result_item,
            R.layout.fragment_car_wash_branch_preview,
            R.layout.activity_car_wash_history,
            R.layout.layout_car_wash_history_item,


            R.layout.fragment_service_service_driver, //todo 스크롤 범위
            R.layout.activity_service_drive_req,
            R.layout.layout_service_drive_req_price,
            R.layout.layout_service_drive_req_cant_service,
            R.layout.layout_service_drive_req_retry,
            R.layout.layout_service_drive_req_loading,
            R.layout.layout_service_drive_req_input_address,

            R.layout.activity_service_drive_req_result,
            R.layout.layout_service_drive_status_matching,
            R.layout.layout_service_drive_status_matched,
            R.layout.layout_service_drive_status_reserved,

//            R.layout.activity_map2,
//            R.layout.layout_map_overlay_ui_title_bar,
//            R.layout.layout_map_overlay_ui_bottom_address,
//            R.layout.layout_map_overlay_ui_bottom_select,
//            R.layout.layout_map_overlay_ui_bottom_sonax_branch,

    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.setContentView(inflater, R.layout.frame_3p);
        testItemAdapter = new TestItemAdapter(getActivity(), testLayoutList);
        me.testLayoutList.setLayoutManager(new LinearLayoutManager(getActivity()));
        me.testLayoutList.setAdapter(testItemAdapter);
        return me.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        if(exampleViewModel==null) {
//            exampleViewModel = new ViewModelProvider(getActivity()).get(ExampleViewModel.class);
//            me.setLifecycleOwner(this);
//            me.setViewModel(exampleViewModel);
//        }


//        me.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        testItemAdapter = new TestItemAdapter();
//        me.recyclerView.setAdapter(testItemAdapter);

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

    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, ArrayList<ExampleResVO> datas) {
//        TestItemAdapter adapter;
//        if (recyclerView.getAdapter() == null) {
//            adapter = new TestItemAdapter();
//            recyclerView.setAdapter(adapter);
//        } else {
//            adapter = (TestItemAdapter)recyclerView.getAdapter();
//        }
//        if(datas!=null)
//            adapter.updateItems(adapter.makeItems(datas,0));
    }


//    public void setItemsToAdvancedItemAdapter(List<Item> items) {
//        List<BaseRecyclerViewAdapter.Row<?>> rows = new ArrayList<>();
//
//        for (int i = 0; i < items.size(); i++) {
//            Item item = items.get(i);
//            if (TextUtils.equals(item.getType(), "A")) {
//                rows.add(BaseRecyclerViewAdapter.Row.create(item, TestItemAdapter.VIEW_TYPE_A));
//            } else {
//                rows.add(BaseRecyclerViewAdapter.Row.create(item, TestItemAdapter.VIEW_TYPE_B));
//            }
//        }
//
//        testItemAdapter.setRows(rows);
//        testItemAdapter.notifyDataSetChanged();
//    }

}

