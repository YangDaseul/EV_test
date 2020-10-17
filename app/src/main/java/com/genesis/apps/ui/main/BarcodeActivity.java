package com.genesis.apps.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.BAR_1001;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.VibratorUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityBarcodeBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.activity.test.CardViewAadapter;
import com.genesis.apps.ui.common.activity.test.ItemMoveCallback;
import com.genesis.apps.ui.common.view.listview.test.Link;

import java.util.LinkedList;
import java.util.List;

public class BarcodeActivity extends SubActivity<ActivityBarcodeBinding> {
    private CMNViewModel cmnViewModel;
    private BarcodeAdapter barcodeAdapter;
    private BarcodeAdapter barcodeAdapter2;
    private final int offsetPageLimit=5;
    private boolean animationStartNeeded = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        cmnViewModel.reqBAR1001(new BAR_1001.Request(APPIAInfo.Bcode01.getId()));
    }

    private void initView() {

        barcodeAdapter2 = new BarcodeAdapter();
        barcodeAdapter2.setViewType(CardViewAadapter.TYPE_LINE);
        ItemTouchHelper.Callback callback = new ItemMoveCallback(barcodeAdapter2);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(ui.recyclerView);
        ui.recyclerView.setLayoutManager(new LinearLayoutManager(BarcodeActivity.this));
        ui.recyclerView.setAdapter(barcodeAdapter2);

        ui.viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        ui.viewPager.setOffscreenPageLimit(offsetPageLimit);
        barcodeAdapter = new BarcodeAdapter();
        ui.viewPager.setAdapter(barcodeAdapter);

        ui.pagerContainer.setOverlapSlider(0f,0f,0.5f,-120f);
        ui.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                VibratorUtil.doVibrator(getApplication());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager2.SCROLL_STATE_IDLE:
                        animationStartNeeded = true;
                        break;
                    default:
                        if(animationStartNeeded){
                            animationStartNeeded=false;
                        }
                        break;
                }

                super.onPageScrollStateChanged(state);
            }
        });

        ui.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(barcodeAdapter!=null){
                    switch (barcodeAdapter.getViewType()) {
                        case CardViewAadapter.TYPE_CARD:
                            ui.pagerContainer.setVisibility(View.GONE);
                            ui.recyclerView.setVisibility(View.VISIBLE);
                            barcodeAdapter2.setRows(barcodeAdapter.getItems());
                            barcodeAdapter2.notifyDataSetChanged();
                            break;
                        case CardViewAadapter.TYPE_LINE:
                            ui.pagerContainer.setVisibility(View.VISIBLE);
                            ui.recyclerView.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });

        ui.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    showProgressDialog(true);
                    if(cmnViewModel.changeCardOrder(barcodeAdapter2.getItems())){
                        ui.pagerContainer.setVisibility(View.VISIBLE);
                        ui.recyclerView.setVisibility(View.GONE);
                        barcodeAdapter.setRows(barcodeAdapter2.getItems());
                        barcodeAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    showProgressDialog(false);
                }
            }
        });

    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        cmnViewModel = new ViewModelProvider(this).get(CMNViewModel.class);
    }

    @Override
    public void setObserver() {
        cmnViewModel.getRES_BAR_1001().observe(this, result -> {

            switch (result.status){

                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getCardList()!=null){
                        try {
                            barcodeAdapter.setRows(cmnViewModel.getCardVO(result.data.getCardList()));
                            barcodeAdapter.notifyDataSetChanged();
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            showProgressDialog(false);
                        }
                    }else{
                        showProgressDialog(false);
                    }
                    break;
                default:
                    showProgressDialog(false);
                    break;

            }


        });
    }

    @Override
    public void getDataFromIntent() {

    }
}
