package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.developers.Distance;
import com.genesis.apps.comm.model.api.developers.Dte;
import com.genesis.apps.comm.model.api.developers.Odometer;
import com.genesis.apps.comm.model.api.developers.ParkLocation;
import com.genesis.apps.comm.model.api.gra.LGN_0003;
import com.genesis.apps.comm.model.api.gra.LGN_0005;
import com.genesis.apps.comm.model.api.gra.STO_1002;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.constants.WeatherCodes;
import com.genesis.apps.comm.model.vo.DownMenuVO;
import com.genesis.apps.comm.model.vo.MessageVO;
import com.genesis.apps.comm.model.vo.QuickMenuVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.developers.OdometerVO;
import com.genesis.apps.comm.util.RecordUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.FragmentHome1Binding;
import com.genesis.apps.ui.common.activity.GAWebActivity;
import com.genesis.apps.ui.common.activity.WebviewActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.main.home.view.HomeInsightHorizontalAdapter;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import dagger.hilt.android.AndroidEntryPoint;

import static android.app.Activity.RESULT_OK;
import static com.google.android.exoplayer2.Player.REPEAT_MODE_ALL;
import static com.google.android.exoplayer2.Player.STATE_IDLE;

@AndroidEntryPoint
public class FragmentHome1 extends SubFragment<FragmentHome1Binding> {
    private SimpleExoPlayer player;
    private LGNViewModel lgnViewModel;
    private CMNViewModel cmnViewModel;
    private DevelopersViewModel developersViewModel;
    private HomeInsightHorizontalAdapter adapter=null;
    private RecordUtil recordUtil;
    private Timer timer = null;
    private boolean isRecord=false;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_home_1);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        initView();
        setVideo();
        setViewWeather();
        recordUtil.regReceiver();
    }

    private void initViewModel() {
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
        cmnViewModel = new ViewModelProvider(getActivity()).get(CMNViewModel.class);
        developersViewModel= new ViewModelProvider(getActivity()).get(DevelopersViewModel.class);

        lgnViewModel.getRES_STO_1002().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    ((MainActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null&&!TextUtils.isEmpty(result.data.getHtmlFilUri())){
                        ((MainActivity)getActivity()).showProgressDialog(false);
                        ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), GAWebActivity.class).putExtra(KeyNames.KEY_NAME_URL, result.data.getHtmlFilUri()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        break;
                    }
                default:
                    ((MainActivity)getActivity()).showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(getActivity(), serverMsg);
                    }
                    break;
            }
        });


        lgnViewModel.getRES_LGN_0003().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case SUCCESS:
                    if(result.data!=null&&!TextUtils.isEmpty(result.data.getAsnStatsNm())){
                        me.tvRepairStatus.setVisibility(View.VISIBLE);
                        me.tvRepairStatus.setText(result.data.getAsnStatsNm());
                    }
                default:
                    break;
            }
        });
        lgnViewModel.getRES_LGN_0005().observe(getViewLifecycleOwner(), result -> {
            //날씨 정보 요청 전문에서는 에러가 발생되어도 기본 값으로 표시
            //todo 2020-11-20 park 날씨에 대한 리소스를 받으면 디폴트 값을  SKY_1 로 변경 필요
            WeatherCodes weatherCodes = WeatherCodes.PTY1;

            if (result.data != null) {
                try {
                    weatherCodes = WeatherCodes.decideCode(result.data.getLgt(), result.data.getPty(), result.data.getSky());
                }catch (Exception e){
                    weatherCodes = WeatherCodes.PTY1;
                }
            }

            try {
                MessageVO weather = cmnViewModel.getHomeWeatherInsight(weatherCodes);
                if (weather != null) {
                    adapter.addRow(weather);
                    adapter.setRealItemCnt(adapter.getRealItemCnt()+1);
                }
                adapter.addRow(cmnViewModel.getTmpInsight());
                adapter.setRealItemCnt(adapter.getRealItemCnt()+1);
                adapter.notifyDataSetChanged();
                me.setActivity((MainActivity) getActivity());
                me.setWeatherCode(weatherCodes);
            }catch (Exception e){

            }

        });

        lgnViewModel.getPosition().observe(getViewLifecycleOwner(), doubles -> {
            lgnViewModel.reqLGN0005(new LGN_0005.Request(APPIAInfo.GM01.getId(), String.valueOf(doubles.get(1)), String.valueOf(doubles.get(0))));
        });

        //주행가능거리표기
        developersViewModel.getRES_DTE().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    break;
                case SUCCESS:
                    if(result.data!=null){
                        me.tvDistancePossible.setText(StringUtil.getDigitGrouping(result.data.getValue()) +developersViewModel.getDistanceUnit(result.data.getUnit()));
                    }
                default:
                    me.tvDistancePossible.setText("--");
                    break;
            }
        });

        //총주행거리표기
        developersViewModel.getRES_ODOMETER().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getOdometers()!=null){
                        me.tvDistanceTotal.setText(StringUtil.getDigitGrouping(result.data.getOdometers().getValue())+developersViewModel.getDistanceUnit(result.data.getOdometers().getUnit()));
                        break;
                    }
                default:
                    me.tvDistanceTotal.setText("--");
                    break;
            }
        });

        //최근주행거리표기
        developersViewModel.getRES_DISTANCE().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getDistances()!=null&&result.data.getDistances().size()>0){
                        OdometerVO odometerVO = result.data.getDistances().stream().max(Comparator.comparingInt(data -> Integer.parseInt(data.getDate()))).get();
                        me.tvDistanceRecently.setText(StringUtil.getDigitGrouping(odometerVO.getValue())+developersViewModel.getDistanceUnit(odometerVO.getUnit()));
                        break;
                    }
                default:
                    me.tvDistanceRecently.setText("--");
                    break;
            }
        });

        developersViewModel.getRES_PARKLOCATION().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getLat()!=0&&result.data.getLon()!=0){
                        me.btnLocation.setVisibility(View.VISIBLE);
                        break;
                    }
                default:
                    me.btnLocation.setVisibility(View.GONE);
                    break;
            }
        });

    }

    private void initView() {
        adapter = new HomeInsightHorizontalAdapter(onSingleClickListener);
        me.vpInsight.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        me.vpInsight.setAdapter(adapter);

        recordUtil = new RecordUtil(this, visibility -> {
            ((MainActivity)getActivity()).ui.lGnb.lWhole.setVisibility(visibility);
            ((MainActivity)getActivity()).ui.tabs.setVisibility(visibility);
            me.vpInsight.setVisibility(visibility);
            me.tvCarCode.setVisibility(visibility);
            me.tvCarModel.setVisibility(visibility);
//            me.tvRepairStatus.setVisibility(visibility);
            me.tvCarVrn.setVisibility(visibility);
//            me.btnCarinfo.setVisibility(visibility);
//            me.btnLocation.setVisibility(visibility);
//            me.btnShare.setVisibility(visibility);

            me.flDim.setVisibility(visibility);
            me.lQuickMenu.setVisibility(visibility);
            //TODO 배경 및 차량 리소스가 결정되면 녹화해야할 VIEW가 요건 정의 된 후 여기에서 해당 뷰 셋팅 정의 필요
        });
    }

    private void setViewWeather() {

        if(!((MainActivity)getActivity()).isGpsEnable()) {
            MiddleDialog.dialogGPS(getActivity(), () -> ((MainActivity)getActivity()).turnGPSOn(isGPSEnable -> {
            }), () -> {
                //TODO 확인 클릭
            });

            //현대양재사옥위치
            lgnViewModel.setPosition(37.463936,127.042953);
        }else{
            reqMyLocation();
        }

    }

    private void reqMyLocation(){
        Log.v("test","test start");
        ((MainActivity)getActivity()).showProgressDialog(true);
        ((MainActivity)getActivity()).findMyLocation(location -> {
            Log.v("test","test finish");
            ((MainActivity)getActivity()).showProgressDialog(false);
            if (location == null) {
                Log.v("location","location null");
                return;
            }

            getActivity().runOnUiThread(() -> {
                //TODO 테스트 필요
                lgnViewModel.setPosition(location.getLatitude(),location.getLongitude());
            });
        },5000);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_carinfo://차량정보설정
                ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), MyCarActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.btn_share:
                recordUtil.checkRecordPermission();
                break;
            case R.id.btn_quick:
                toggleQuickMenu();
                break;
            case R.id.btn_location:

                if(!((MainActivity)getActivity()).isGpsEnable()) {
                    MiddleDialog.dialogGPS(getActivity(), () -> ((MainActivity)getActivity()).turnGPSOn(isGPSEnable -> {
                    }), () -> {
                        //TODO 확인 클릭
                    });
                }else{
                    try {
                        List<Double> position = new ArrayList<>();
                        position.add(developersViewModel.getRES_PARKLOCATION().getValue().data.getLat());
                        position.add(developersViewModel.getRES_PARKLOCATION().getValue().data.getLon());
                        ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), MyLocationActivity.class).putExtra(KeyNames.KEY_NAME_VEHICLE_LOCATION, new Gson().toJson(position)), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.fl_dim:
                goneQuickMenu();
                break;
        }
    }

    private void toggleQuickMenu() {
        if(me.lQuickMenu.getVisibility()==View.VISIBLE){
            goneQuickMenu();
        }else{
            visibleQuickMenu();
        }
    }

    private void visibleQuickMenu(){
        me.lQuickMenu.setVisibility(View.VISIBLE);
        me.flDim.setVisibility(View.VISIBLE);
    }

    private void goneQuickMenu(){
        me.lQuickMenu.setVisibility(View.GONE);
        me.flDim.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        if(isRecord)
            return;

        videoPauseAndResume(true);
        setViewVehicle();
//        recordUtil.regReceiver();
        ((MainActivity)getActivity()).setGNB(false, 1, View.VISIBLE);

        startTimer();
        goneQuickMenu();
    }

    private void startTimer() {
        if(adapter.getRealItemCnt()>1) {

            if (timer == null)
                timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(() -> {
                        try {
                            me.vpInsight.setCurrentItem(me.vpInsight.getCurrentItem() + 1, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }, 6000, 5000);

        }
    }

    private void pauseTimer(){
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }


    private void setViewVehicle() {
        VehicleVO vehicleVO = null;
        try{
            vehicleVO = lgnViewModel.getMainVehicleFromDB();
            if(vehicleVO!=null){
                me.tvCarCode.setText(vehicleVO.getMdlNm());
                me.tvCarModel.setText(vehicleVO.getSaleMdlNm());
                me.tvCarVrn.setText(vehicleVO.getCarRgstNo());
                Glide
                        .with(getContext())
                        .load(vehicleVO.getVhclImgUri())
                        .centerInside()
                        .error(R.drawable.car_new_44)
                        .placeholder(R.drawable.car_new_44)
                        .into(me.ivCar);

                me.btnShare.setVisibility(View.GONE);
                me.btnCarinfo.setVisibility(View.GONE);
                me.ivMore.setVisibility(View.GONE);
                me.tvRepairStatus.setVisibility(View.GONE);
                me.lDistance.setVisibility(View.GONE);
                me.lFloating.setVisibility(View.GONE);

                switch (vehicleVO.getCustGbCd()){
                    case VariableType.MAIN_VEHICLE_TYPE_OV:
                        me.btnShare.setVisibility(View.VISIBLE);
                        me.btnCarinfo.setVisibility(View.VISIBLE);
                        me.ivMore.setVisibility(View.VISIBLE);
                        me.lDistance.setVisibility(View.VISIBLE);
                        lgnViewModel.reqLGN0003(new LGN_0003.Request(APPIAInfo.GM01.getId(), vehicleVO.getVin()));
                        reqCarInfoToDevelopers(vehicleVO.getVin());
                        makeQuickMenu(vehicleVO.getCustGbCd());
                        break;
                    case VariableType.MAIN_VEHICLE_TYPE_CV:
                        me.btnCarinfo.setVisibility(View.VISIBLE);
                        makeDownMenu(vehicleVO.getCustGbCd());
                        makeQuickMenu(vehicleVO.getCustGbCd());
                        break;
                    case VariableType.MAIN_VEHICLE_TYPE_NV:
                        makeDownMenu(vehicleVO.getCustGbCd());
                        makeQuickMenu(vehicleVO.getCustGbCd());
                        break;
                    default:
                        makeDownMenu(vehicleVO.getCustGbCd());
                        makeQuickMenu(vehicleVO.getCustGbCd());
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void reqCarInfoToDevelopers(String vin) {
        String carId = developersViewModel.getCarId(vin);
        if(!TextUtils.isEmpty(carId)){
            developersViewModel.reqDte(new Dte.Request(carId));
            developersViewModel.reqOdometer(new Odometer.Request(carId));
            developersViewModel.reqDistance(new Distance.Request(carId, developersViewModel.getDateYyyyMMdd(-7), developersViewModel.getDateYyyyMMdd(0)));
            developersViewModel.reqParkLocation(new ParkLocation.Request(carId));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseTimer();
        videoPauseAndResume(false);
    }

    private void makeQuickMenu(String custGbCd) {
        final TextView[] quickBtn={me.btnQuick1, me.btnQuick2};
        List<QuickMenuVO> list = cmnViewModel.getQuickMenuList(custGbCd);
        quickBtn[0].setVisibility(View.GONE);
        quickBtn[1].setVisibility(View.GONE);

        int menuSize = list.size()>quickBtn.length ? quickBtn.length : list.size();

        for(int i=0; i<menuSize; i++){
            quickBtn[i].setVisibility(View.VISIBLE);
            quickBtn[i].setText(list.get(i).getMenuNm());
            quickBtn[i].setTag(R.id.menu_id, list.get(i));
            quickBtn[i].setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    QuickMenuVO quickMenuVO = (QuickMenuVO)v.getTag(R.id.menu_id);
                    if(quickMenuVO !=null){
                        String qckMenuDivCd = quickMenuVO.getQckMenuDivCd();
                        String lnkUri = quickMenuVO.getLnkUri();
                        String wvYn = quickMenuVO.getWvYn();
                        if(!TextUtils.isEmpty(qckMenuDivCd)&&!TextUtils.isEmpty(lnkUri)){
                            if(qckMenuDivCd.equalsIgnoreCase("IM")){
                                if(lnkUri.startsWith(KeyNames.KEY_NAME_INTERNAL_LINK)){
                                    if(!TextUtils.isEmpty(lnkUri)){
                                        moveToNativePage(lnkUri);
                                    }
                                }
                                //네이티브 링크로 이동
                                //TODO 네이티브로 이동하는 부분은 처리 필요
                            }else{
                                if(TextUtils.isEmpty(wvYn)||wvYn.equalsIgnoreCase(VariableType.COMMON_MEANS_YES)){
                                    ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), WebviewActivity.class).putExtra(KeyNames.KEY_NAME_URL, lnkUri),RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                                }else{
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(lnkUri));
                                    startActivity(intent); //TODO 테스트 필요 0002
                                }
                                //외부 링크로 이동
                            }
                        }
                    }
                }
            });
        }

    }

    private void moveToNativePage(String lnkUri) {
        lnkUri = lnkUri.replace(KeyNames.KEY_NAME_INTERNAL_LINK, "");
        switch (APPIAInfo.findCode(lnkUri)){
            case GM_BTO1://BTO
            case GM_BTO2://견적내기
                //todo 견적내기 처리 방안 확인 필요.
                lgnViewModel.reqSTO1002(new STO_1002.Request(APPIAInfo.GM01.getId()));
                break;
            case GM02_CTR01://계약서 조회
                //todo 전문 확인 필요
                break;
            case GM02_INV01://유사 재고 조회
                ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), APPIAInfo.GM02_INV01.getActivity()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case GM_CARLST_01://렌트/리스 실 운행자 등록
                ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), APPIAInfo.GM_CARLST_01.getActivity()), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case GM_CARLST_03://중고차 등록
                ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), APPIAInfo.GM_CARLST_03.getActivity()), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case GM_CARLST01://MY 차고
                ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), APPIAInfo.GM_CARLST01.getActivity()), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
        }
    }

    private void makeDownMenu(String custGbCd){
        final TextView[] floatingBtns={me.btnFloating1, me.btnFloating2, me.btnFloating3};
        List<DownMenuVO> list = cmnViewModel.getDownMenuList(custGbCd);
        if(list==null||list.size()==0){
            me.lFloating.setVisibility(View.GONE);
        }else{
            me.lFloating.setVisibility(View.VISIBLE);
            me.btnFloating1.setVisibility(View.GONE);
            me.btnFloating2.setVisibility(View.GONE);
            me.btnFloating3.setVisibility(View.GONE);
            int menuSize = list.size()>3 ? 3 : list.size();

            for(int i=0; i<menuSize; i++){
                floatingBtns[i].setVisibility(View.VISIBLE);
                floatingBtns[i].setText(list.get(i).getMenuNm());
                floatingBtns[i].setTag(R.id.menu_id, list.get(i));
                floatingBtns[i].setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        DownMenuVO downMenuVO = (DownMenuVO)v.getTag(R.id.menu_id);
                        if(downMenuVO !=null){
                            String qckMenuDivCd = downMenuVO.getQckMenuDivCd();
                            String lnkUri = downMenuVO.getLnkUri();
                            String wvYn = downMenuVO.getWvYn();
                            if(!TextUtils.isEmpty(qckMenuDivCd)&&!TextUtils.isEmpty(lnkUri)){
                                if(qckMenuDivCd.equalsIgnoreCase("IM")){

                                    if(lnkUri.startsWith(KeyNames.KEY_NAME_INTERNAL_LINK)){
                                        if(!TextUtils.isEmpty(lnkUri)){
                                            moveToNativePage(lnkUri);
                                        }
                                    }

                                    //네이티브 링크로 이동
                                    //TODO 네이티브로 이동하는 부분은 처리 필요
                                }else{
                                    if(TextUtils.isEmpty(wvYn)||wvYn.equalsIgnoreCase(VariableType.COMMON_MEANS_YES)){
                                        ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), WebviewActivity.class).putExtra(KeyNames.KEY_NAME_URL, lnkUri),RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                                    }else{
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(lnkUri));
                                        startActivity(intent); //TODO 테스트 필요 0002
                                    }
                                    //외부 링크로 이동
                                }
                            }
                        }
                    }
                });
            }

            if(list.size()<2){
                me.ivFloatingMark1.setVisibility(View.GONE);
                me.ivFloatingMark2.setVisibility(View.GONE);
            }else if(list.size()==2){
                me.ivFloatingMark1.setVisibility(View.VISIBLE);
                me.ivFloatingMark2.setVisibility(View.GONE);
            }else{
                me.ivFloatingMark1.setVisibility(View.VISIBLE);
                me.ivFloatingMark2.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseVideo();
        recordUtil.unRegReceiver();
    }

    private void releaseVideo(){
        if(player !=null){
            me.exoPlayerView.getOverlayFrameLayout().removeAllViews();
            me.exoPlayerView.setPlayer(null);
            player.release();
            player =null;
        }
    }


    private void setVideo() {
        try {

            if(player==null){
                player = new SimpleExoPlayer.Builder(getContext()).build();
                player.setVolume(0);
                player.setRepeatMode(REPEAT_MODE_ALL);
                player.setSeekParameters(null);
                me.exoPlayerView.setPlayer(player);
                me.exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                me.exoPlayerView.setUseController(false);


                DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.rain_mob));
                final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(getContext());
                rawResourceDataSource.open(dataSpec);
                com.google.android.exoplayer2.upstream.DataSource.Factory factory = () -> rawResourceDataSource;
                MediaSource audioSource = new ProgressiveMediaSource.Factory(factory).createMediaSource(rawResourceDataSource.getUri());
                LoopingMediaSource mediaSource = new LoopingMediaSource(audioSource);
                player.prepare(mediaSource);
            }


        }catch (Exception e){

        }
    }

    private void videoPauseAndResume(boolean isResume){
        Log.v("video player status","isResume:"+isResume);

        if(isResume&&player!=null&&player.getPlaybackState()==STATE_IDLE){
            setVideo();
        }

        player.setPlayWhenReady(isResume);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RequestCodes.REQ_CODE_GPS.getCode() && resultCode == RESULT_OK){
            reqMyLocation();
        }else if (requestCode == RequestCodes.REQ_CODE_PERMISSIONS_MEDIAPROJECTION.getCode() && resultCode == RESULT_OK) {
            Log.v("testRecord","testRecord:resultOk");
            isRecord=true;
            recordUtil.doRecordService(me.vClickReject, resultCode, data);
            return;
        }else if (requestCode == RequestCodes.REQ_CODE_PLAY_VIDEO.getCode()){
            isRecord=false;
            recordUtil.requestShare();
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }


//        if(requestCode == RequestCodes.REQ_CODE_GPS.getCode() && resultCode == RESULT_OK){
//            reqMyLocation();
//        }else if (requestCode == RequestCodes.REQ_CODE_PERMISSIONS_MEDIAPROJECTION.getCode() && resultCode == RESULT_OK) {
//            recordUtil.doRecordService(me.vClickReject, resultCode, data);
//            return;
//        }else if (requestCode == RequestCodes.REQ_CODE_PLAY_VIDEO.getCode()){
//            recordUtil.requestShare();
//        }else{
//            super.onActivityResult(requestCode, resultCode, data);
//        }
    }
}
