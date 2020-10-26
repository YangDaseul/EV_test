package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.AutoScrollHelper;
import androidx.core.widget.ListViewAutoScrollHelper;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.constants.WeatherCodes;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.LGN_0003;
import com.genesis.apps.comm.model.gra.api.LGN_0005;
import com.genesis.apps.comm.model.vo.FloatingMenuVO;
import com.genesis.apps.comm.model.vo.MessageVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.RecordUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.FragmentHome1Binding;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.main.SimilarCarActivity;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dagger.hilt.android.AndroidEntryPoint;

import static android.app.Activity.RESULT_OK;
import static com.google.android.exoplayer2.Player.REPEAT_MODE_ALL;

@AndroidEntryPoint
public class FragmentHome1 extends SubFragment<FragmentHome1Binding> {
    private SimpleExoPlayer simpleExoPlayer;
    private LGNViewModel lgnViewModel;
    private CMNViewModel cmnViewModel;
    private HomeInsightHorizontalAdapter adapter=null;
    private RecordUtil recordUtil;
    private Timer timer = null;
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
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);
        initView();
        recordUtil = new RecordUtil(this, visibility -> {
            //TODO 배경 및 차량 리소스가 결정되면 녹화해야할 VIEW가 요건 정의 된 후 여기에서 해당 뷰 셋팅 정의 필요
            //EX ui.layout.setVisibility(visibility);

        });
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
        cmnViewModel = new ViewModelProvider(getActivity()).get(CMNViewModel.class);

        //TODO 뱃지 알람을 여기에서 처리하면안됨. 수정필요
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
            switch (result.status){
                case SUCCESS:
                    if(result.data!=null){
                        try {
                            WeatherCodes weatherCodes = WeatherCodes.decideCode(result.data.getLgt(), result.data.getPty(), result.data.getSky());
                            MessageVO weather = cmnViewModel.getHomeWeatherInsight(weatherCodes);
                            if(weather!=null) adapter.addRow(weather);
                            adapter.addRow(cmnViewModel.getTmpInsight());
                            adapter.notifyDataSetChanged();
                            me.setActivity((MainActivity)getActivity());
                            me.setWeatherCode(weatherCodes);
                        }catch (Exception e){

                        }
                    }
                    break;
                default:
                    break;
            }

        });

        lgnViewModel.getPosition().observe(getViewLifecycleOwner(), doubles -> {
            lgnViewModel.reqLGN0005(new LGN_0005.Request(APPIAInfo.GM01.getId(), String.valueOf(doubles.get(0)), String.valueOf(doubles.get(1))));
        });


        setVideo();
        setViewWeather();
    }

    private void initView() {
        adapter = new HomeInsightHorizontalAdapter(onSingleClickListener);
        me.vpInsight.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        me.vpInsight.setAdapter(adapter);



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
        ((MainActivity)getActivity()).showProgressDialog(true);
        ((MainActivity)getActivity()).findMyLocation(location -> {
            ((MainActivity)getActivity()).showProgressDialog(false);
            if (location == null) {
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
            case R.id.btn_floating_1:
            case R.id.btn_floating_2:
            case R.id.btn_floating_3:
                String menuId = v.getTag(R.id.menu_id).toString();
                //TODO menuId로 activity 이동 구현 필요
                break;
            case R.id.btn_carinfo://차량정보설정
                ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), MyCarActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.btn_share:
                recordUtil.checkRecordPermission();
                break;
            case R.id.btn_location:

                if(!((MainActivity)getActivity()).isGpsEnable()) {
                    MiddleDialog.dialogGPS(getActivity(), () -> ((MainActivity)getActivity()).turnGPSOn(isGPSEnable -> {
                    }), () -> {
                        //TODO 확인 클릭
                    });
                }else{
                    //todo 현재 강제로 하드코딩한 주소값. 제네시스 디벨로퍼에서 가저오도록 수정 필요
                    List<String> position = new ArrayList<>();
                    position.add("37.463936");
                    position.add("127.042953");
                    ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), MyLocationActivity.class).putExtra(KeyNames.KEY_NAME_VEHICLE_LOCATION, new Gson().toJson(position)), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }

                break;
        }
    }


    @Override
    public void onRefresh() {
        Log.e("onResume","onReusme FragmentHome1");
        videoPauseAndResume(true);
        setViewVehicle();
        recordUtil.regReceiver();
        ((MainActivity)getActivity()).setGNB(false, false, 1, View.VISIBLE);

        startTimer();

        //TODO 알람뱃지뉴 표시하는 부분 요청처리 필요

    }

    private void startTimer() {

        if(timer==null)
            timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {
                    try{
                        me.vpInsight.setCurrentItem(me.vpInsight.getCurrentItem()+1, true);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
        },6000,5000);

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
                me.tvCarCode.setText(vehicleVO.getMdlCd());
                me.tvCarModel.setText(vehicleVO.getMdlNm());
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

//                        me.lDistance.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), SimilarCarActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                            }
//                        });
                        //TODO LGN-0003 요청을 여기서 해야할듯
                        //TODO 위치정보 확인 요청
                        //TODO 거리 확인 요청 등 다 여기서 해야함..
                        break;
                    case VariableType.MAIN_VEHICLE_TYPE_CV:
                        me.btnCarinfo.setVisibility(View.VISIBLE);
                        makeFloatingMenu(vehicleVO.getCustGbCd());
                        //TODO QUICKMENU 만드는 로직 넣어야함
                        break;
                    case VariableType.MAIN_VEHICLE_TYPE_NV:
                        makeFloatingMenu(vehicleVO.getCustGbCd());
                        //TODO QUICKMENU 만드는 로직 넣어야함
                        break;
                    default:
                        makeFloatingMenu(vehicleVO.getCustGbCd());
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseTimer();
        videoPauseAndResume(false);
        recordUtil.unRegReceiver();
    }

    private void makeFloatingMenu(String custGbCd){
        final TextView[] floatingBtns={me.btnFloating1, me.btnFloating2, me.btnFloating3};
        List<FloatingMenuVO> list = cmnViewModel.getFloatingMenuList(custGbCd);
        if(list==null||list.size()==0){
            me.lFloating.setVisibility(View.GONE);
        }else{
            me.lFloating.setVisibility(View.VISIBLE);
            me.btnFloating1.setVisibility(View.GONE);
            me.btnFloating2.setVisibility(View.GONE);
            me.btnFloating3.setVisibility(View.GONE);
            for(int i=0; i<list.size(); i++){
                floatingBtns[i].setVisibility(View.VISIBLE);
                floatingBtns[i].setText(list.get(i).getMenuNm());
                floatingBtns[i].setTag(R.id.menu_id, list.get(i).getMenuId());
            }

        }




    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseVideo();

    }

    private void releaseVideo(){
        if(simpleExoPlayer!=null){
            me.exoPlayerView.getOverlayFrameLayout().removeAllViews();
            me.exoPlayerView.setPlayer(null);
            simpleExoPlayer.release();
            simpleExoPlayer=null;
        }
    }


    private void setVideo() {
        try {
//            String path = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
            DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.rain_mob));
            final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(getContext());
            rawResourceDataSource.open(dataSpec);
            com.google.android.exoplayer2.upstream.DataSource.Factory factory = () -> rawResourceDataSource;
            MediaSource audioSource = new ProgressiveMediaSource.Factory(factory).createMediaSource(rawResourceDataSource.getUri());
            LoopingMediaSource mediaSource = new LoopingMediaSource(audioSource);


//            String path = "android_asset://sky.mp4";
//            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));
//            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(path));


            simpleExoPlayer = new SimpleExoPlayer.Builder(getContext()).build();
            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.setVolume(0);
            simpleExoPlayer.setRepeatMode(REPEAT_MODE_ALL);
            simpleExoPlayer.setSeekParameters(null);
            me.exoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.prepare(mediaSource);
            me.exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            me.exoPlayerView.setUseController(false);

//        ui.vVideo.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.rain));
//        ui.vVideo.setVideoURI(Uri.parse(path));
//        ui.vVideo.requestFocus();
//        ui.vVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setLooping(true);
//                ui.vVideo.start();
//            }
//        });

        }catch (Exception e){

        }
    }

    private void videoPauseAndResume(boolean isResume){
//        simpleExoPlayer.setPlayWhenReady(isResume);
//        simpleExoPlayer.getPlaybackState();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode == RequestCodes.REQ_CODE_GPS.getCode() && resultCode == RESULT_OK){
//          reqMyLocation();
//        }else{
//            super.onActivityResult(requestCode, resultCode, data);
//        }

        if(requestCode == RequestCodes.REQ_CODE_GPS.getCode() && resultCode == RESULT_OK){
            reqMyLocation();
        }else if (requestCode == RequestCodes.REQ_CODE_PERMISSIONS_MEDIAPROJECTION.getCode() && resultCode == RESULT_OK) {
            recordUtil.doRecordService(me.vClickReject, resultCode, data);
            return;
        }else if (requestCode == RequestCodes.REQ_CODE_PLAY_VIDEO.getCode()){
            recordUtil.requestShare();
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
