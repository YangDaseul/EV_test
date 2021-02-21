package com.genesis.apps.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.BAR_1001;
import com.genesis.apps.comm.model.api.gra.OIL_0005;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.util.VibratorUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.OILViewModel;
import com.genesis.apps.databinding.ActivityBarcodeBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.ItemMoveCallback;
import com.genesis.apps.ui.main.insight.InsightExpnMembershipActivity;
import com.genesis.apps.ui.myg.MyGOilIntegrationActivity;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;

public class BarcodeActivity extends SubActivity<ActivityBarcodeBinding> {
    private CMNViewModel cmnViewModel;
    private OILViewModel oilViewModel;
    private BarcodeAdapter barcodeAdapter;
    private BarcodeAdapter barcodeAdapter2;
    private final int offsetPageLimit=4;
//    private boolean animationStartNeeded = true;
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

    @Override
    protected void onResume() {
        super.onResume();
//        if(ui.lTitle.ivTitlebarImgBtn.getVisibility()==View.VISIBLE) {
//            cmnViewModel.reqBAR1001(new BAR_1001.Request(APPIAInfo.Bcode01.getId()));
//        }
    }

    private void initCardView(){
        barcodeAdapter = new BarcodeAdapter(onSingleClickListener);
        ui.viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        ui.viewPager.setOffscreenPageLimit(offsetPageLimit);
        ui.viewPager.setAdapter(barcodeAdapter);

        ui.pagerContainer.setOverlapSlider(0f,0.95f,0.5f,-120f);
        ui.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                VibratorUtil.doVibrator(getApplication());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                }

                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void initLineView(){
        barcodeAdapter2 = new BarcodeAdapter(onSingleClickListener);
        barcodeAdapter2.setViewType(BarcodeAdapter.TYPE_LINE);
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
        ui.lTitle.setTextBtnListener(onSingleClickListener); //완료
        ui.lTitle.ivTitlebarImgBtn.setOnClickListener(onSingleClickListener); //설정
        initTitleBar();
    }

    private void initTitleBar(){
        ui.lTitle.setValue(""); //타이틀 없음
        ui.lTitle.setBtnText(""); //완료버튼제거
        ui.lTitle.setIconId(getDrawable(R.drawable.ic_setting_b)); //설정버튼
        ui.lTitle.lTitleBar.setBackgroundColor(getColor(R.color.x_f8f8f8));
        SubActivity.setStatusBarColor(this, R.color.x_f8f8f8);
    }

    private void openViewer(){
        initCardView();
        ui.pagerContainer.setVisibility(View.VISIBLE);
        ui.recyclerView.setVisibility(View.GONE);
        barcodeAdapter.setRows(barcodeAdapter2.getItems());
        barcodeAdapter.notifyDataSetChanged();
        initTitleBar();
    }

    private void editViewer(){
        ui.pagerContainer.setVisibility(View.GONE);
        ui.recyclerView.setVisibility(View.VISIBLE);
        barcodeAdapter2.setRows(barcodeAdapter.getItems());
        barcodeAdapter2.notifyDataSetChanged();

        ui.lTitle.setValue(getString(R.string.bcode01_1)); //멤버십 편집
        ui.lTitle.setBtnText(getString(R.string.bcode01_2)); //완료버튼
        ui.lTitle.setIconId(null); //설정버튼
        ui.lTitle.lTitleBar.setBackgroundColor(getColor(R.color.x_ffffff));
        SubActivity.setStatusBarColor(this, R.color.x_ffffff);
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
            case R.id.iv_titlebar_img_btn:
                if(barcodeAdapter!=null){
                    editViewer();
                }
            case R.id.tv_integration:
                CardVO item = (CardVO)v.getTag(R.id.item);
                if(item!=null){
                    if(StringUtil.isValidString(item.getRgstYn()).equalsIgnoreCase(OilPointVO.OIL_JOIN_CODE_R)){
                        oilViewModel.reqOIL0005(new OIL_0005.Request(APPIAInfo.MG01.getId(), StringUtil.isValidString(item.getIsncCd())));
                    }else{
                        startActivitySingleTop(new Intent(this, MyGOilIntegrationActivity.class).putExtra(OilCodes.KEY_OIL_CODE, StringUtil.isValidString(item.getIsncCd())), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }
                }
                break;
            case R.id.tv_membership_info:
                CardVO cardVO = (CardVO)v.getTag(R.id.item);
                if(cardVO!=null){
                    startActivitySingleTop(new Intent(this, InsightExpnMembershipActivity.class).putExtra(OilCodes.KEY_OIL_CODE, StringUtil.isValidString(cardVO.getIsncCd())), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        cmnViewModel = new ViewModelProvider(this).get(CMNViewModel.class);
        oilViewModel = new ViewModelProvider(this).get(OILViewModel.class);
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
                            barcodeAdapter = new BarcodeAdapter(onSingleClickListener);
                            barcodeAdapter.setRows(cmnViewModel.getCardVO(result.data.getCardList()));
                            ui.viewPager.setAdapter(barcodeAdapter);
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            showProgressDialog(false);
                            ui.tvEmpty.setVisibility(barcodeAdapter.getItemCount()==0 ? View.VISIBLE : View.GONE);
                        }
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(this, serverMsg);
                        ui.lTitle.ivTitlebarImgBtn.setEnabled(false);
                        ui.tvEmpty.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        });


        oilViewModel.getRES_OIL_0005().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)){
                        cmnViewModel.reqBAR1001(new BAR_1001.Request(APPIAInfo.Bcode01.getId()));
                        SnackBarUtil.show(this, getString(R.string.mg_con02_p01_3));
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if(TextUtils.isEmpty(serverMsg)) serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                    }
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
        if(ui.lTitle.ivTitlebarImgBtn.getVisibility()!=View.VISIBLE){
            openViewer();
        }else{
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == ResultCodes.REQ_CODE_NORMAL.getCode()){
            String msg="";
            try {
                if(data!=null) msg = data.getStringExtra("msg");
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                if(!TextUtils.isEmpty(msg)){
                    SnackBarUtil.show(this, msg);
                    cmnViewModel.reqBAR1001(new BAR_1001.Request(APPIAInfo.Bcode01.getId()));
                }
            }
        }
    }

}
