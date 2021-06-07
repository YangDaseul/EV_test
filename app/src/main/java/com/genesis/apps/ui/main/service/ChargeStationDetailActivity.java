package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.EPT_1002;
import com.genesis.apps.comm.model.api.gra.EPT_1003;
import com.genesis.apps.comm.model.api.gra.STC_1002;
import com.genesis.apps.comm.model.api.gra.STC_1003;
import com.genesis.apps.comm.model.api.gra.STC_1004;
import com.genesis.apps.comm.model.api.gra.STC_1006;
import com.genesis.apps.comm.model.constants.ChargeSearchCategorytype;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ChargeEptInfoVO;
import com.genesis.apps.comm.model.vo.ChargeSttInfoVO;
import com.genesis.apps.comm.model.vo.ChargerEptVO;
import com.genesis.apps.comm.model.vo.ChargerSttVO;
import com.genesis.apps.comm.model.vo.ReserveDtVO;
import com.genesis.apps.comm.model.vo.ReviewVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.EPTViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.comm.viewmodel.STCViewModel;
import com.genesis.apps.databinding.ActivityChargeStationDetailBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomChargerReserveDialog;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.genesis.apps.comm.model.constants.VariableType.CHARGE_STATION_TYPE_EPT;
import static com.genesis.apps.comm.model.constants.VariableType.CHARGE_STATION_TYPE_STC;

/**
 * Class Name : ChargeStationDetailActivity
 *
 * @author Ki-man Kim
 * @since 2021-04-27
 */
public class ChargeStationDetailActivity extends GpsBaseActivity<ActivityChargeStationDetailBinding> implements NestedScrollView.OnScrollChangeListener {

    private int stationType = CHARGE_STATION_TYPE_EPT;
    /**
     * 환경부-기관ID
     * 환경부에서 발급한 ID
     */
    private String spid;
    /**
     * 충전소ID
     */
    private String csid;
    /**
     * 위도
     * 현재위치/차량위치/지명검색위치
     */
    private String lat;
    /**
     * 경도
     * 현재위치/차량위치/지명검색위치
     */
    private String lot;

    /**
     * 충전소 거리 표시 여부.
     */
    private boolean isShowDist = true;

    // 조회된 충전소 정보 - E-PIT
    private ChargeEptInfoVO chargeEptInfoVO;
    // 조회된 충전소 정보 - 에스트레픽
    private ChargeSttInfoVO chargeStcInfoVO;

    /**
     * 선택한 충전소 ID
     * 예약을 위한 저장 변수.
     */
    private String selectedSid;
    /**
     * 선택한 충전기 ID
     * 예약을 위한 저장 변수.
     */
    private String selectedCid;

    // 리뷰 현재 페이지 번호
    private int pageNo = 1;
    // 리뷰를 조회 단위 개수
    private final String pageCnt = "10";
    // 리뷰 조회 요청 중복 방지 플래그
    private boolean isReviewReq = false;

    private final ArrayList<ReviewVO> reviewList = new ArrayList<>();
    private ReviewListAdapter reviewListAdapter;

    private VehicleVO mainVehicleVO;

    private LGNViewModel lgnViewModel;
    private REQViewModel reqViewModel;
    private EPTViewModel eptViewModel;
    private STCViewModel stcViewModel;

    private EvChargeStatusFragment evChargeStatusFragment;

    private BottomChargerReserveDialog bottomChargerReserveDialog;

    /****************************************************************************************************
     * Override Method - LifeCycle
     ****************************************************************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_station_detail);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initialize();
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.tv_btn_reserve:
                try {
                    if (mainVehicleVO == null || TextUtils.isEmpty(mainVehicleVO.getVin()) || lgnViewModel.getUserInfoFromDB().getCustGbCd().equals(VariableType.MAIN_VEHICLE_TYPE_NV)) {
                        // 계약상태여서 차대번호가 없거나 미 보유 고객인 경우 스낵바 알림 메시지 노출
                        SnackBarUtil.show(ChargeStationDetailActivity.this, getString(R.string.sm01_snack_bar));
                        return;
                    }

                    Object tag = v.getTag();
                    selectedSid = null;
                    selectedCid = null;
                    if (tag instanceof ChargerEptVO) {
                        // 충전소 찾기에서 진입한 충전소 상세 화면인 경우.
                        if (chargeEptInfoVO != null) {
                            selectedSid = chargeEptInfoVO.getEcsid();
                        }
                        selectedCid = ((ChargerEptVO) tag).getCpid();
                    } else if (tag instanceof ChargerSttVO) {
                        // 충전소 예약에서 진입한 충전소 상세 화면인 경우.
                        if (chargeStcInfoVO != null) {
                            selectedSid = chargeStcInfoVO.getSid();
                        }
                        selectedCid = ((ChargerSttVO) tag).getCid();
                    }

                    if (selectedSid != null && selectedCid != null) {
                        getReserveDate(selectedSid, selectedCid, DateUtil.getCurrentDate(DateUtil.DATE_FORMAT_yyyyMMdd));
                    }
                /*
                MiddleDialog.dialogEVServiceInfo(this, (Runnable) () -> {

                });
                 */
                } catch (Exception ignore) {

                }
                break;
            case R.id.tv_btn_bottom:
                ChargeStationDetailListAdapter.DetailType type = (ChargeStationDetailListAdapter.DetailType) v.getTag(R.id.item);
                if (type != null) {
                    switch (type) {
                        case ADDRESS:
                            try {
                                PackageUtil.runAppWithScheme(this, PackageUtil.PACKAGE_CONNECTED_CAR, chargeStcInfoVO != null ? VariableType.getGCSScheme(chargeStcInfoVO.getLat(), chargeStcInfoVO.getLot()) : VariableType.getGCSScheme(chargeEptInfoVO.getLat(), chargeEptInfoVO.getLot()));
                            } catch (Exception e) {

                            }

                            break;
                        case SPNM:
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + (chargeStcInfoVO != null ? chargeStcInfoVO.getBcall() : chargeEptInfoVO.getSpcall()))));
                            } catch (Exception e) {

                            }
                            break;
                        default:
                            //do nothing.
                            break;
                    }

                }
                break;
        }

    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        // 리뷰 목록이 표시중이지 않거나 이미 데이테를 요청 중일 때는 넘어감
        if (ui.rvReviewList.getVisibility() != View.VISIBLE || isReviewReq) {
            return;
        }

        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
            getReviewList(chargeEptInfoVO, pageNo + 1);
        }
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(ChargeStationDetailActivity.this);
        lgnViewModel = new ViewModelProvider(ChargeStationDetailActivity.this).get(LGNViewModel.class);
        reqViewModel = new ViewModelProvider(ChargeStationDetailActivity.this).get(REQViewModel.class);
        eptViewModel = new ViewModelProvider(ChargeStationDetailActivity.this).get(EPTViewModel.class);
        stcViewModel = new ViewModelProvider(ChargeStationDetailActivity.this).get(STCViewModel.class);
    }

    @Override
    public void setObserver() {
        eptViewModel.getRES_EPT_1002().observe(ChargeStationDetailActivity.this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    EPT_1002.Response data = result.data;
                    if (data != null && "0000".equalsIgnoreCase(data.getRtCd()) && data.getChgInfo() != null) {
                        updateStation(data);
                        break;
                    }
                }
                default: {
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        showProgressDialog(false);
                        exitPage(TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg, ResultCodes.RES_CODE_NETWORK.getCode());
                    }
                    break;
                }
            }
        });

        eptViewModel.getRES_EPT_1003().observe(ChargeStationDetailActivity.this, result -> {
            switch (result.status) {
                case LOADING: {
                    // Nothing
                }
                case SUCCESS: {
                    EPT_1003.Response data = result.data;
                    if (data != null && "0000".equalsIgnoreCase(data.getRtCd())) {
                        isReviewReq = false;
                        pageNo++;
                        reviewList.addAll(data.getRevwList());
                        updateReview(reviewList);
                        break;
                    }
                }
                default: {
                    isReviewReq = false;
                    break;
                }
            }
        });

        stcViewModel.getRES_STC_1002().observe(ChargeStationDetailActivity.this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    STC_1002.Response data = result.data;
                    if (data != null && "0000".equalsIgnoreCase(data.getRtCd()) && data.getChgSttnInfo() != null) {
                        updateStation(data);
                        break;
                    }
                }
                default: {
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        showProgressDialog(false);
                        exitPage(TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg, ResultCodes.RES_CODE_NETWORK.getCode());
                    }
                    break;
                }
            }
        });

        stcViewModel.getRES_STC_1003().observe(ChargeStationDetailActivity.this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    STC_1003.Response data = result.data;
                    if (data != null) {
                        if ("0000".equalsIgnoreCase(data.getRtCd())) {
                            // 에스트레픽 회원인 경우 rtCd 값이 0000으로 전달되어 회원인 것으로 간주.
                            if (TextUtils.isEmpty(data.getReservNo())) {
                                // 충전소 예약 번호가 없음 - 예약 프로세스 진행.
                                showSelectReserveDate(data.getReservDtList());
                            } else {
                                // 충전소 예약 번호가 존재 - 안내 팝업 표시.
                                MiddleDialog.dialogChargeReserveCancelFromDetail(ChargeStationDetailActivity.this,
                                        () -> {
                                            reqReserveCancel(data.getReservNo());
                                        },
                                        () -> {
                                        });
                            }
                            break;
                        } else if ("2463".equalsIgnoreCase(data.getRtCd())) {
                            // 에스트레픽 회원이 아닌 경우 rtCd 값이 2463으로 전달되어 회원이 아닌 것으로 간주. - 안내 팝업 표시.
                            MiddleDialog.dialogNeedRegistSTC(ChargeStationDetailActivity.this, () -> {
                            });
                            break;
                        }

                    }
                }
                default: {
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        showProgressDialog(false);
                        SnackBarUtil.show(ChargeStationDetailActivity.this,
                                TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
                }
            }
        });

        stcViewModel.getRES_STC_1004().observe(ChargeStationDetailActivity.this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    STC_1004.Response data = result.data;
                    if (data != null && "0000".equalsIgnoreCase(data.getRtCd()) && data.getReservInfo() != null) {
                        // 충전소 예약 완료 - 충전오 예약 내역으로 이동.
                        if (bottomChargerReserveDialog != null && bottomChargerReserveDialog.isShowing()) {
                            bottomChargerReserveDialog.dismiss();
                            bottomChargerReserveDialog = null;
                        }
                        exitPage(new Intent().putExtra(KeyNames.KEY_NAME_CHARGE_RESERVE_INFO, data.getReservInfo()),
                                ResultCodes.REQ_CODE_CHARGE_RESERVATION_FINISH.getCode());
                        break;
                    }
                }
                default: {
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        showProgressDialog(false);
                        Toast.makeText(ChargeStationDetailActivity.this,
                                TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg, Toast.LENGTH_LONG).show();
                    }
                    break;
                }
            }
        });

        stcViewModel.getRES_STC_1006().observe(ChargeStationDetailActivity.this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    STC_1006.Response data = result.data;
                    if (data != null && "0000".equalsIgnoreCase(data.getRtCd())) {
                        // 예약 취소 성공
                        SnackBarUtil.show(ChargeStationDetailActivity.this, getString(R.string.sm_evss04_21));
                        break;
                    }
                }
                default: {
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        showProgressDialog(false);
                        SnackBarUtil.show(ChargeStationDetailActivity.this,
                                TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
                }
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        Intent getIntent = getIntent();
        spid = getIntent.getStringExtra(KeyNames.KEY_NAME_CHARGE_STATION_SPID);
        csid = getIntent.getStringExtra(KeyNames.KEY_NAME_CHARGE_STATION_CSID);
        lat = getIntent.getStringExtra(KeyNames.KEY_NAME_LAT);
        lot = getIntent.getStringExtra(KeyNames.KEY_NAME_LOT);
        stationType = getIntent.getIntExtra(KeyNames.KEY_STATION_TYPE, CHARGE_STATION_TYPE_EPT);
        isShowDist = getIntent.getBooleanExtra(KeyNames.KEY_IS_SHOW_DIST, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //고객 위치 정보가 없을 경우 os로부터 좌표 관련 코드를 전달받은 경우
        if (!hasAddress() && requestCode == RequestCodes.REQ_CODE_GPS.getCode()) {
            if (resultCode == RESULT_OK)
                //GPS OS 팝업에서 GPS ON을 승인한 경우
                reqMyLocation();
            else {
                //GPS OFF 된 경우
                //현대양재사옥위치
                setAddress(VariableType.DEFAULT_POSITION[0] + "", VariableType.DEFAULT_POSITION[1] + "");
            }
        }
    }


    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void initialize() {
        evChargeStatusFragment = EvChargeStatusFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(ui.vgEvStatusConstainer.getId(), evChargeStatusFragment)
                .commitAllowingStateLoss();

        ui.rvStationDetail.setLayoutManager(new LinearLayoutManager(ChargeStationDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        ui.rvChargerList.setLayoutManager(new LinearLayoutManager(ChargeStationDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        switch (stationType) {
            // 에스트레픽 충전소
            case CHARGE_STATION_TYPE_STC: {
                // 리뷰 정보가 없기에 비노출
                ui.tvReviewTitle.setVisibility(View.GONE);
                ui.rvReviewList.setVisibility(View.GONE);
                break;
            }
            default:
            case CHARGE_STATION_TYPE_EPT: {
                // 리뷰 정보가 있기에 설정.
                ui.rvReviewList.setLayoutManager(new LinearLayoutManager(ChargeStationDetailActivity.this, LinearLayoutManager.VERTICAL, false));
                reviewListAdapter = new ReviewListAdapter();
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ChargeStationDetailActivity.this, DividerItemDecoration.VERTICAL);
                dividerItemDecoration.setDrawable(new ColorDrawable(getColor(R.color.x_e5e5e5)));
                ui.rvReviewList.addItemDecoration(dividerItemDecoration);
                ui.rvReviewList.setAdapter(reviewListAdapter);
                ui.vgNsv.setOnScrollChangeListener(ChargeStationDetailActivity.this);
                break;
            }
        }

        try {
            mainVehicleVO = reqViewModel.getMainVehicle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //intent로 확인된 고객 위치 정보가 있을 경우
        if (hasAddress()) {
            getChargeStationInfo();
        } else {
            //고객 위치 정보가 없을 경우 (충전소 예약 내역 등에서 이동 시)
            checkEnableGPS(() -> { //GPS ON/OFF 유무 확인
                //GPS 팝업에서 GPS를 OFF 할 경우
                //현대양재사옥위치
                setAddress(VariableType.DEFAULT_POSITION[0] + "", VariableType.DEFAULT_POSITION[1] + "");
            }, this::reqMyLocation);//GPS가 켜져 있는 경우
        }
    }

    private void setAddress(String lat, String lot) {
        this.lat = lat;
        this.lot = lot;
        getChargeStationInfo();
    }

    private boolean hasAddress() {
        return !TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lot);
    }

    private void reqMyLocation() {
        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                //GPS에서 주소정보를 가져오지 못한 경우
                setAddress(VariableType.DEFAULT_POSITION[0] + "", VariableType.DEFAULT_POSITION[1] + "");
                return;
            }
            //GPS에서 주소정보를 정상적으로 가져온 경우
            runOnUiThread(() -> setAddress(location.getLatitude() + "", location.getLongitude() + ""));

        }, 5000, GpsRetType.GPS_RETURN_FIRST, false);
    }


    private void getChargeStationInfo() {
        switch (stationType) {
            case CHARGE_STATION_TYPE_STC: {
                stcViewModel.reqSTC1002(new STC_1002.Request(
                        APPIAInfo.SM_EVSS04.getId(),
                        mainVehicleVO.getVin(),
                        lat,
                        lot,
                        csid
                ));
                break;
            }
            default:
            case CHARGE_STATION_TYPE_EPT: {
                eptViewModel.reqEPT1002(new EPT_1002.Request(
                        APPIAInfo.SM_EVSS04.getId(),
                        mainVehicleVO.getVin(),
                        spid,
                        csid,
                        lat,
                        lot));
                // 리뷰 목록 페이지 번호 초기화
                pageNo = 0;
                reviewList.clear();
                break;
            }
        }
    }

    private void getReviewList(ChargeEptInfoVO chargeEptInfoVO, int pageNo) {
        if (chargeEptInfoVO == null) {
            return;
        }
        if (TextUtils.isEmpty(chargeEptInfoVO.getEspid()) || TextUtils.isEmpty(chargeEptInfoVO.getEcsid())) {
            return;
        }

        isReviewReq = true;

        eptViewModel.reqEPT1003(new EPT_1003.Request(
                APPIAInfo.SM_EVSS04.getId(),
                chargeEptInfoVO.getEspid(),
                chargeEptInfoVO.getEcsid(),
                String.valueOf(pageNo),
                pageCnt));
    }

    /**
     * 충전소 정보 업데이트(에스트레픽)
     *
     * @param data
     */
    private void updateStation(STC_1002.Response data) {
        ui.tvGuide.setVisibility(View.VISIBLE);
        chargeStcInfoVO = data.getChgSttnInfo();

        // 충전소 정보 목록 셋팅
        ArrayList<ChargeStationDetailListAdapter.ItemVO> list = new ArrayList<>();
        list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.ADDRESS, getAddr(chargeStcInfoVO.getChgName(), chargeStcInfoVO.getDaddr(), chargeStcInfoVO.getDaddrDtl(), chargeStcInfoVO.getDist())));
        list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.TIME, chargeStcInfoVO.getUseStartTime() + "-" + chargeStcInfoVO.getUseEndTime()));
        list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.SPNM, chargeStcInfoVO.getBname()));

        List<String> payTypes = chargeStcInfoVO.getPayType();
        if (payTypes != null && payTypes.size() > 0) {
            StringBuilder payStringBuilder = new StringBuilder();
            if (payTypes.contains(ChargeSearchCategorytype.CREDIT_CARD.getCode())) {
                payStringBuilder.append(getString(R.string.sm_evss04_06));
            }
            if (payTypes.contains(ChargeSearchCategorytype.S_TRAFFIC_CRADIT_PAY.getCode())) {
                if (payStringBuilder.length() > 0) {
                    payStringBuilder.append("\n");
                }
                payStringBuilder.append(getString(R.string.sm_evss04_07));
            }
            if (payTypes.contains(ChargeSearchCategorytype.CAR_PAY.getCode())) {
                if (payStringBuilder.length() > 0) {
                    payStringBuilder.append("\n");
                }
                payStringBuilder.append(getString(R.string.sm_evss04_08));
            }
            list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.PAY_TYPE, payStringBuilder.toString()));
        }

        // 충전소 상세 표시
        ChargeStationDetailListAdapter adapter = new ChargeStationDetailListAdapter(onSingleClickListener);
        adapter.setRows(list);
        ui.rvStationDetail.setAdapter(adapter);
        ui.tvChargerCount.setText(Html.fromHtml(VariableType.getChargeStatus(this, new Gson().toJson(chargeStcInfoVO)), Html.FROM_HTML_MODE_COMPACT));
        if (!TextUtils.isEmpty(chargeStcInfoVO.getChgrUpdDtm()) && !chargeStcInfoVO.getChgrUpdDtm().equalsIgnoreCase("null")) {
            ui.tvDate.setVisibility(View.VISIBLE);
            ui.tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(chargeStcInfoVO.getChgrUpdDtm(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_hh_mm));
        } else {
            ui.tvDate.setVisibility(View.GONE);
        }
        ChargerSTCListAdapter chargerListAdapter = new ChargerSTCListAdapter(onSingleClickListener);
        chargerListAdapter.setRows(data.getChgrList());
        ui.rvChargerList.setAdapter(chargerListAdapter);

        // 지도 표시
        try {
            double lat = Double.parseDouble(chargeStcInfoVO.getLat());
            double lot = Double.parseDouble(chargeStcInfoVO.getLot());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.vg_map, FragmentChargeStationMap.newInstance(lat, lot, chargeStcInfoVO.getChgName(), getAddr(chargeStcInfoVO.getDaddr(), chargeStcInfoVO.getDaddrDtl())))
                    .commit();
        } catch (Exception e) {
            // 좌표 파싱 에러로 지도를 표시할 수 없음.
            ui.vgMap.setVisibility(View.GONE);
        }
    }

    private final String BR = "<br/>";

    private String getAddr(String chgName, String addr, String addrDtl, String distance) {

        String totalAddress = StringUtil.isValidString(addr) + (!TextUtils.isEmpty(addrDtl) ? (" " + addrDtl) : "");
        String dist = TextUtils.isEmpty(distance) ? "- km" : distance + " km";

        return String.format(Locale.getDefault(), getString(R.string.sm_evss04_20),
                TextUtils.isEmpty(chgName) ? "" : chgName,
                TextUtils.isEmpty(dist) || !isShowDist ? "" : BR + dist,    // 거리 값이 없거나 isShowDist 값이 false라면 미표시.
                TextUtils.isEmpty(totalAddress) ? "" : BR + totalAddress);
    }

    private String getAddr(String addr, String addrDtl) {
        String totalAddress = StringUtil.isValidString(addr) + (!TextUtils.isEmpty(addrDtl) ? (" " + addrDtl) : "");
        return totalAddress;
    }

    /**
     * 충전소 정보 업데이트 함수(E-PIT)
     *
     * @param data
     */
    private void updateStation(EPT_1002.Response data) {
        ui.tvGuide.setVisibility(View.VISIBLE);
        chargeEptInfoVO = data.getChgInfo();
        // 충전소 정보 목록 셋팅
        ArrayList<ChargeStationDetailListAdapter.ItemVO> list = new ArrayList<>();
        list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.ADDRESS, getAddr(chargeEptInfoVO.getCsnm(), chargeEptInfoVO.getDaddr(), chargeEptInfoVO.getAddrDtl(), chargeEptInfoVO.getDist())));
        list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.TIME, chargeEptInfoVO.getUseTime()));
        list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.SPNM, chargeEptInfoVO.getSpnm()));

        List<String> payTypes = chargeEptInfoVO.getPayType();
        if (payTypes != null && payTypes.size() > 0) {
            StringBuilder payStringBuilder = new StringBuilder();
            if (payTypes.contains(ChargeSearchCategorytype.CREDIT_CARD.getCode())) {
                payStringBuilder.append(getString(R.string.sm_evss04_06));
            }
            if (payTypes.contains(ChargeSearchCategorytype.S_TRAFFIC_CRADIT_PAY.getCode())) {
                if (payStringBuilder.length() > 0) {
                    payStringBuilder.append("\n");
                }
                payStringBuilder.append(getString(R.string.sm_evss04_07));
            }
            if (payTypes.contains(ChargeSearchCategorytype.CAR_PAY.getCode())) {
                if (payStringBuilder.length() > 0) {
                    payStringBuilder.append("\n");
                }
                payStringBuilder.append(getString(R.string.sm_evss04_08));
            }
            list.add(new ChargeStationDetailListAdapter.ItemVO(ChargeStationDetailListAdapter.DetailType.PAY_TYPE, payStringBuilder.toString()));
        }

        // 충전소 상세 표시
        ChargeStationDetailListAdapter adapter = new ChargeStationDetailListAdapter(onSingleClickListener);
        adapter.setRows(list);
        ui.rvStationDetail.setAdapter(adapter);

        ui.tvChargerCount.setText(Html.fromHtml(VariableType.getChargeStatus(this, new Gson().toJson(chargeEptInfoVO)), Html.FROM_HTML_MODE_COMPACT));
        if (!TextUtils.isEmpty(chargeEptInfoVO.getChgrUpdDtm()) && !chargeEptInfoVO.getChgrUpdDtm().equalsIgnoreCase("null")) {
            ui.tvDate.setVisibility(View.VISIBLE);
            ui.tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(chargeEptInfoVO.getChgrUpdDtm(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_hh_mm));
        } else {
            ui.tvDate.setVisibility(View.GONE);
        }
        ChargerListAdapter chargerListAdapter = new ChargerListAdapter(onSingleClickListener);
        chargerListAdapter.setRows(data.getChgrList());
        ui.rvChargerList.setAdapter(chargerListAdapter);

        // 지도 표시
        try {
            double lat = Double.parseDouble(chargeEptInfoVO.getLat());
            double lot = Double.parseDouble(chargeEptInfoVO.getLot());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.vg_map, FragmentChargeStationMap.newInstance(lat, lot, chargeEptInfoVO.getCsnm(), getAddr(chargeEptInfoVO.getDaddr(), chargeEptInfoVO.getAddrDtl())))
                    .commit();
        } catch (Exception e) {
            // 좌표 파싱 에러로 지도를 표시할 수 없음.
            ui.vgMap.setVisibility(View.GONE);
        }
        // 리뷰 데이터 요청
        getReviewList(chargeEptInfoVO, 1);
    }

    private void updateReview(List<ReviewVO> list) {
        if (list == null || list.size() == 0) {
            ui.tvReviewTitle.setVisibility(View.GONE);
            ui.rvReviewList.setVisibility(View.GONE);
            return;
        }
        ui.tvReviewTitle.setVisibility(View.VISIBLE);
        ui.rvReviewList.setVisibility(View.VISIBLE);

        reviewListAdapter.setRows(list);
        reviewListAdapter.notifyDataSetChanged();
    }

    private void showSelectReserveDate(List<ReserveDtVO> dateList) {
        try {
            if (bottomChargerReserveDialog != null && bottomChargerReserveDialog.isShowing()) {
                bottomChargerReserveDialog.dismiss();
                bottomChargerReserveDialog = null;
            }
            // 현재 시간 객체.
            Calendar calendar = Calendar.getInstance();
            List<ReserveDtVO> reservableList = dateList.stream().filter(it -> it.isAfter(calendar) && "Y".equalsIgnoreCase(it.getReservYn())).collect(Collectors.toList());
            bottomChargerReserveDialog = new BottomChargerReserveDialog(ChargeStationDetailActivity.this, R.style.BottomSheetDialogTheme);
            bottomChargerReserveDialog.setAmDatas(getTimeData(reservableList, false));
            bottomChargerReserveDialog.setPmDatas(getTimeData(reservableList, true));
            bottomChargerReserveDialog.setEventListener(this::reqReserveCharger);
            bottomChargerReserveDialog.show();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        SnackBarUtil.show(ChargeStationDetailActivity.this, getString(R.string.sm_evsb01_p01_7));
    }

    private List<ReserveDtVO> getTimeData(List<ReserveDtVO> timeData, boolean isPm) {
        if (timeData == null) {
            return new ArrayList<>();
        }

        // 정오 시간 객체 생성.
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);

        if (isPm) {
            // 오후
            return timeData.stream().filter(it -> it.isAfter(calendar)).collect(Collectors.toList());
        } else {
            // 오전
            return timeData.stream().filter(it -> it.isBefore(calendar)).collect(Collectors.toList());
        }
    }

    /**
     * 해당 충전기의 충전 예약 가능 날짜 요청 함수.
     *
     * @param sid      충전소 ID
     * @param cid      충전기 ID
     * @param reservDt 예약 날짜(당일만 가능.)
     */
    private void getReserveDate(String sid, String cid, String reservDt) {
        stcViewModel.reqSTC1003(new STC_1003.Request(
                APPIAInfo.SM_EVSS04.getId(),
                mainVehicleVO.getVin(),
                sid,
                cid,
                reservDt
        ));
    }

    /**
     * 해당 시간 정보로 충전기 예약 요청하는 함수.
     *
     * @param reserveDtVO 예약 시간
     */
    private void reqReserveCharger(ReserveDtVO reserveDtVO) {
        stcViewModel.reqSTC1004(new STC_1004.Request(
                APPIAInfo.SM_EVSS04.getId(),
                mainVehicleVO.getVin(),
                selectedSid,
                selectedCid,
                DateUtil.getCurrentDate(DateUtil.DATE_FORMAT_yyyyMMdd),
                reserveDtVO.getReservDt().replace(":", "")  // 시간정보에 있는 콜른 제거.
        ));
    }

    /**
     * 충전기 예약 취소 요청 함수.
     *
     * @param reservNo 예약 번호.
     */
    private void reqReserveCancel(String reservNo) {
        stcViewModel.reqSTC1006(new STC_1006.Request(
                APPIAInfo.SM_EVSS04.getId(),
                reservNo
        ));
    }
} // end of class ChargeStationDetailActivity
