package com.genesis.apps.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.STO_1001;
import com.genesis.apps.comm.model.vo.SimilarVehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivitySimilarCarBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author hjpark
 * @brief 공지사항
 */
public class SimilarCarActivity extends SubActivity<ActivitySimilarCarBinding> {
    private LGNViewModel lgnViewModel;
    private SimilarCarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_car);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();

        lgnViewModel.reqSTO1001(new STO_1001.Request(APPIAInfo.GM02_INV01.getId()));
    }

    private void initView() {
        adapter = new SimilarCarAdapter(ResourcesCompat.getFont(this, R.font.regular_genesissanstextglobal));
        ui.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {

        lgnViewModel.getRES_STO_1001().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:

                    //todo 유사 차량이 없을 때 계약차량과 분리해야하는지 확인 필요요
                   if(result.data!=null&&result.data.getEstmVhclList()!=null&&result.data.getSmlrVhclList()!=null){
                        List<SimilarVehicleVO> list = new ArrayList<>();
                        list.addAll(result.data.getSmlrVhclList());
                        list.add(0, result.data.getEstmVhclList());
                        adapter.setRows(list);
                        adapter.notifyDataSetChanged();
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

    }
}
