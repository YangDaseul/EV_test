package com.genesis.apps.ui.main.home;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.hybrid.core.WebViewFragment;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.WRT_1001;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.WarrantyVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.WRTViewModel;
import com.genesis.apps.databinding.ActivityWarrantyRepairGuideBinding;
import com.genesis.apps.databinding.ItemTabAlarmBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class WarrantyRepairGuideActivity extends SubActivity<ActivityWarrantyRepairGuideBinding> {
    private WRTViewModel wrtViewModel;
    private final int[] titleId = {R.string.gm01_02_3, R.string.gm01_02_4};
    private String vin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warranty_repair_guide);
        setViewModel();
        getDataFromIntent();
        setObserver();
        wrtViewModel.reqWRT1001(new WRT_1001.Request(APPIAInfo.GM01_02.getId(), vin));
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        wrtViewModel = new ViewModelProvider(this).get(WRTViewModel.class);
    }

    @Override
    public void setObserver() {
        wrtViewModel.getRES_WRT_1001().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getContList() != null && result.data.getContList().size() > 0) {
                        initTabView(result.data.getContList());
                        loadTerms(getUrl(result.data.getContList(), R.string.gm01_02_3));
                        showProgressDialog(false);
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
//            vin = getIntent().getStringExtra(KeyNames.KEY_NAME_VIN);
            vin = wrtViewModel.getMainVehicleSimplyFromDB().getVin();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (TextUtils.isEmpty(vin)) {
                exitPage("차대번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    private void initTabView(List<WarrantyVO> warrantyVOList) {
        for (int nm : titleId) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTabAlarmBinding itemTabAlarmBinding = DataBindingUtil.inflate(inflater, R.layout.item_tab_alarm, null, false);
            final View view = itemTabAlarmBinding.getRoot();
            itemTabAlarmBinding.tvTab.setText(nm);
            itemTabAlarmBinding.tvTab.setTag(R.id.url, getUrl(warrantyVOList, nm));
            ui.tabs.addTab(ui.tabs.newTab().setCustomView(view));
        }

        ui.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadTerms(((ItemTabAlarmBinding) DataBindingUtil.bind(tab.getCustomView())).tvTab.getTag(R.id.url).toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private String getUrl(List<WarrantyVO> warrantyVOList, int id) {

        for (WarrantyVO warrantyVO : warrantyVOList) {
            if (id == R.string.gm01_02_3 && warrantyVO.getType().equalsIgnoreCase("1010")) {
                return warrantyVO.getContents();
            } else if (id == R.string.gm01_02_4 && warrantyVO.getType().equalsIgnoreCase("1011")) {
                return warrantyVO.getContents();
            }
        }

        return "";
    }

    public void loadTerms(String html) {
        if (!TextUtils.isEmpty(html)) {
            Bundle bundle = new Bundle();
            bundle.putString(WebViewFragment.EXTRA_HTML_BODY, html);

            MyWebViewFrament fragment = new MyWebViewFrament();
            fragment.setArguments(bundle);
            fragment.setWebViewListener(webViewListener);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fm_holder, fragment);
            ft.commitAllowingStateLoss();
        }
    }


    public MyWebViewFrament.WebViewListener webViewListener = new MyWebViewFrament.WebViewListener() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageFinished(String url) {
        }

        @Override
        public boolean onBackPressed() {
            return false;
        }

        @Override
        public void onCloseWindow(WebView window) {
        }
    };

}
