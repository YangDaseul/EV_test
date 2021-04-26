package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1023;
import com.genesis.apps.comm.model.api.gra.CHB_1026;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.carlife.BookingVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.FragmentServiceChargeBtrHistoryBinding;
import com.genesis.apps.ui.common.activity.GAWebActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.service.view.ServiceChargeBtrHistoryAdapter;

public class FragmentServiceChargeBtrHistory extends SubFragment<FragmentServiceChargeBtrHistoryBinding> {

    private final int PAGE_SIZE = 5;

    private ServiceChargeBtrHistoryAdapter adapter;
    private CHBViewModel chbViewModel;
    private VehicleVO mainVehicle;

    private int currPgNo = 1;
    private int currSelectPos = 0;

    private boolean isLastPg = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_service_charge_btr_history);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setViewModel();
        setObserver();
        initView();
        initData();
    }


    private void initView() {
        me.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ServiceChargeBtrHistoryAdapter(onSingleClickListener);
        me.rv.setHasFixedSize(true);
        me.rv.setAdapter(adapter);
        me.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!me.rv.canScrollVertically(1) && me.rv.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {//scroll end
                    if (adapter.getItemCount() > 0 && adapter.getItemCount() >= adapter.getPageNo() * PAGE_SIZE && !isLastPg)
                        requestCHB1023((adapter.getPageNo() + 1));
                }
            }
        });
    }

    private void initData() {
        try {
            if (mainVehicle == null) mainVehicle = chbViewModel.getMainVehicleFromDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_detail:
            case R.id.iv_arrow_image:
                String orderId = (String) v.getTag(R.id.item);
                currSelectPos = Integer.parseInt(v.getTag(R.id.position).toString());
                boolean isOpened = (boolean) v.getTag(R.id.dtl_opened);

                if(!isOpened)
                    chbViewModel.reqCHB1026(new CHB_1026.Request(APPIAInfo.SM_CGRV04_04.getId(), orderId));
                else
                    adapter.eventAccordion(currSelectPos);

                break;
            case R.id.btn_charge_btr_tel:
                String telNm = (String) ((ConstraintLayout) v).getChildAt(0).getTag();
                if (!TextUtils.isEmpty(telNm)) {
                    getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + telNm)));
                }
                break;
            case R.id.btn_charge_btr_image:
                String strLink = (String) ((ConstraintLayout) v).getChildAt(0).getTag();
                if (!TextUtils.isEmpty(strLink)) {
                    ((SubActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), GAWebActivity.class)
                            .putExtra(KeyNames.KEY_NAME_URL, strLink)
                            .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, R.string.service_charge_btr_08), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (mainVehicle != null) {
            requestCHB1023(currPgNo);
        }
    }

    private void setViewModel() {
        chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);
    }

    private void setObserver() {
        chbViewModel.getRES_CHB_1023().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getBookingList() != null && result.data.getBookingList().size() > 0) {

                        try {
                            int totalPgSize = result.data.getTotalPage();

                            if(adapter.getPageNo() == 0) {
                                adapter.setRows(result.data.getBookingList());
                            } else {
                                adapter.remove(adapter.getItemCount()-1);
                                adapter.addRows(result.data.getBookingList());
                            }

                            adapter.addRow(new BookingVO());

                            adapter.setPageNo(adapter.getPageNo() + 1);
                            adapter.notifyDataSetChanged();

                            if(adapter.getPageNo() == totalPgSize)
                                isLastPg = true;

                        }catch (Exception e) {

                        }finally {
                            setViewEmpty();
                            ((SubActivity) getActivity()).showProgressDialog(false);
                        }
                        break;
                    }
                default:
                    setViewEmpty();
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("2005"))//조회된 정보가 없을 경우 에러메시지 출력하지 않음
                        return;

                    if (TextUtils.isEmpty(serverMsg)) {
                        serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                    }
                    SnackBarUtil.show(getActivity(), serverMsg);
                    break;
            }
        });

        chbViewModel.getRES_CHB_1026().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    ((SubActivity) getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null) {
                        BookingVO item = adapter.getItem(currSelectPos);
                        if(result.data.getWorkerCount() > 0)
                            item.setWorkerVO(result.data.getWorkerList().get(0));
                        item.setOrderVO(result.data.getOrderInfo());

                        adapter.eventAccordion(currSelectPos);
                        ((SubActivity) getActivity()).showProgressDialog(false);
                        break;
                    }
                default:
                    ((SubActivity) getActivity()).showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (TextUtils.isEmpty(serverMsg)) {
                        serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                    }
                    SnackBarUtil.show(getActivity(), serverMsg);
                    break;
            }
        });

    }

    private void requestCHB1023(int pageNo) {
        if (adapter != null && pageNo == 1)
            adapter.setPageNo(0);

        chbViewModel.reqCHB1023(new CHB_1023.Request(APPIAInfo.SM_CGRV04_04.getId(), mainVehicle.getVin(), "20210416", "20210416", PAGE_SIZE, pageNo));
    }

    private void setViewEmpty() {
        if (adapter == null || adapter.getItemCount() < 1) {
            me.rv.setVisibility(View.GONE);
            me.lEmpty.lWhole.setVisibility(View.VISIBLE); //empty 뷰
        } else {
            me.lEmpty.lWhole.setVisibility(View.GONE);
            me.rv.setVisibility(View.VISIBLE);
        }
    }
}
