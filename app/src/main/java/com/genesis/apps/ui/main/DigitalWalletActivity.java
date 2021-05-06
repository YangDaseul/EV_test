package com.genesis.apps.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityDigitalWalletBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class DigitalWalletActivity extends SubActivity<ActivityDigitalWalletBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_wallet);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        ui.lTitle.setTextBtnListener(onSingleClickListener); //완료
        ui.lTitle.ivTitlebarImgBtn.setOnClickListener(onSingleClickListener); //설정
        initTitleBar();


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.vg_container, FragmentCardFront.newInstance());
        ft.commit();
    }

    private void initTitleBar() {
        ui.lTitle.setValue(""); //타이틀 없음
        ui.lTitle.setBtnText(""); //완료버튼제거
        ui.lTitle.setIconId(getDrawable(R.drawable.ic_setting_b)); //설정버튼
        ui.lTitle.lTitleBar.setBackgroundColor(getColor(R.color.x_f8f8f8));
        SubActivity.setStatusBarColor(this, R.color.x_f8f8f8);
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.tv_titlebar_text_btn:
                try {
                    showProgressDialog(true);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    showProgressDialog(false);
                }
                break;
            case R.id.btn_card_mgmt:
                break;

            case R.id.btn_card_open:
                verticalOpen();
                break;
            case R.id.btn_card_close:
                verticalClose();
                break;
        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {
    }

    private void verticalOpen() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.anim_bottom_in,
                R.anim.anim_bottom_out,
                R.anim.anim_top_in,
                R.anim.anim_top_out);
        ft.replace(R.id.vg_container, FragmentCardBack.newInstance());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void verticalClose() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
