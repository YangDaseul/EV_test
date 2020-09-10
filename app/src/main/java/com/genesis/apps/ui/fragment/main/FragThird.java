package com.genesis.apps.ui.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.ExampleResVO;
import com.genesis.apps.comm.model.ExampleViewModel;
import com.genesis.apps.databinding.Frame3pBinding;
import com.genesis.apps.ui.fragment.SubFragment;
import com.genesis.apps.ui.view.listview.test.TestItemAdapter;

import java.util.ArrayList;


public class FragThird extends SubFragment<Frame3pBinding> {
    TestItemAdapter testItemAdapter;
    ExampleViewModel exampleViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_3p);
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

