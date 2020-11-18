package com.genesis.apps.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.BAR_1001;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.util.VibratorUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.databinding.ActivityBarcodeBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.activity.test.CardViewAadapter;
import com.genesis.apps.ui.common.activity.test.ItemMoveCallback;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

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

    private void initCardView(){
        barcodeAdapter = new BarcodeAdapter();
        ui.viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        ui.viewPager.setOffscreenPageLimit(offsetPageLimit);
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
    }

    private void initLineView(){
        barcodeAdapter2 = new BarcodeAdapter();
        barcodeAdapter2.setViewType(CardViewAadapter.TYPE_LINE);
        ItemTouchHelper.Callback callback = new ItemMoveCallback(barcodeAdapter2);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(ui.recyclerView);
        ui.recyclerView.setLayoutManager(new LinearLayoutManager(BarcodeActivity.this));
        ui.recyclerView.setAdapter(barcodeAdapter2);
        ui.recyclerView.addItemDecoration(new RecyclerViewDecoration((int) DeviceUtil.dip2Pixel(this,1.0f)));
    }

    private void initView() {
        initCardView();
        initLineView();
        ui.lTitle.setTextBtnListener(onSingleClickListener);
        ui.btnSettings.setOnClickListener(onSingleClickListener);
    }

    private void openViewer(){
        initCardView();
        ui.pagerContainer.setVisibility(View.VISIBLE);
        ui.recyclerView.setVisibility(View.GONE);
        ui.lTitle.lTitleBar.setVisibility(View.GONE);
        ui.btnSettings.setVisibility(View.VISIBLE);
        barcodeAdapter.setRows(barcodeAdapter2.getItems());
        barcodeAdapter.notifyDataSetChanged();
    }

    private void editViewer(){
        ui.pagerContainer.setVisibility(View.GONE);
        ui.lTitle.lTitleBar.setVisibility(View.VISIBLE);
        ui.recyclerView.setVisibility(View.VISIBLE);
        barcodeAdapter2.setRows(barcodeAdapter.getItems());
        barcodeAdapter2.notifyDataSetChanged();
        ui.btnSettings.setVisibility(View.GONE);
    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.tv_titlebar_text_btn:
                try {
                    showProgressDialog(true);
                    if (cmnViewModel.changeCardOrder(barcodeAdapter2.getItems())) {
                        openViewer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    showProgressDialog(false);
                }
                break;
            case R.id.btn_settings:
                if(barcodeAdapter!=null){
                    editViewer();
                }
                break;
        }

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


    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public void onBackButton() {
        exit();
    }

    private void exit() {
        if(ui.btnSettings.getVisibility()!=View.VISIBLE){
            openViewer();
        }else{
            finish();
        }
    }

}
