package com.genesis.apps.ui.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.RequestCodes;
import com.genesis.apps.databinding.ActivityMainBinding;
import com.genesis.apps.fcm.FirebaseMessagingService;
import com.genesis.apps.fcm.PushCode;
import com.genesis.apps.fcm.PushVO;
import com.genesis.apps.ui.common.fragment.main.FragFourth;
import com.genesis.apps.ui.common.fragment.main.MainViewpagerAdapter;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.material.tabs.TabLayoutMediator;

import static com.google.android.exoplayer2.Player.REPEAT_MODE_ALL;

public class MainActivity extends SubActivity<ActivityMainBinding> {
    public FragmentStateAdapter pagerAdapter;
    private int num_page = 5;
    private SimpleExoPlayer simpleExoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


//        ui.button.setOnClickListener(view -> startActivitySingleTop(new Intent(MainActivity.this, EntranceActivity.class),RequestCodes.REQ_CODE_DEFAULT.getCode()));

//        String test="{\n" +
//                "  \"rtCd\": \"0000\",\n" +
//                "  \"rtMsg\": \"Success\",\n" +
//                "  \"appVer\": \"01.03\",\n" +
//                "  \"appUpdType\": \"M\",\n" +
//                "  \"contVer\": \"01.00\",\n" +
//                "  \"notiList\": [\n" +
//                "    {\n" +
//                "      \"notiType\": \"EMGR\",\n" +
//                "      \"notiSeq\": \"11\",\n" +
//                "      \"notiTitle\": \"긴급공지1\",\n" +
//                "      \"notiCont\": \"긴급공지내용1\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"notiType\": \"NOTI\",\n" +
//                "      \"notiSeq\": \"10\",\n" +
//                "      \"notiTitle\": \"일반공지1\",\n" +
//                "      \"notiCont\": \"일반공지내용1\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"notiType\": \"ANNC\",\n" +
//                "      \"notiSeq\": \"9\",\n" +
//                "      \"notiTitle\": \"필독공지1\",\n" +
//                "      \"notiCont\": \"필독공지내용1\"\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//        CMN_0001.Response data = new Gson().fromJson(test, CMN_0001.Response.class);

//        GNS_1012.Response testdata1 = new GNS_1012.Response();
//        GNS_1012.Response2 testdata2 = new GNS_1012.Response2();
//        testdata1.setAprvDtm("1");
//        testdata1.setAsnCd("12");
//
//        SubcribeCarVO2 tmpdata = new SubcribeCarVO2("2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2");
//        testdata2.setVo(tmpdata);
//        new Gson().toJson(testdata1);
//        new Gson().toJson(testdata2);
//        new Gson().fromJ
//        new Gson().fromJson("{\"aprvDtm\":\"2\",\"aprvStusCd\":\"2\",\"asnCd\":\"2\",\"asnNm\":\"2\",\"attachFilName\":\"2\",\"cnttUrl\":\"2\",\"crdRcvAdr\":\"2\",\"crdRcvDtlAdr\":\"2\",\"crdRcvScnCd\":\"2\",\"crdRcvZip\":\"2\",\"csmrScnCd\":\"2\",\"pbzAdr\":\"2\",\"rentPeriod\":\"2\",\"repTn\":\"2\",\"rtnRsnMsg\":\"2\",\"seqNo\":\"2\",\"subspDtm\":\"2\",\"vin\":\"2\"}", SubcribeCarVO2.class)
        //ViewPager2
        //Adapter
        pagerAdapter = new MainViewpagerAdapter(this, num_page);
        ui.viewpager.setAdapter(pagerAdapter);
        //Indicator
        ui.indicator.setViewPager(ui.viewpager);
        ui.indicator.createIndicators(num_page,0);

        new TabLayoutMediator(ui.tabs, ui.viewpager, (tab, position) -> tab.setText("Tab " + (position + 1))).attach();

        //ViewPager Setting
        ui.viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        ui.viewpager.setCurrentItem(0);
        ui.viewpager.setOffscreenPageLimit(4);


        ui.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    ui.viewpager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ui.indicator.animatePageSelected(position%num_page);
            }

        });


        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        ui.viewpager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float myOffset = position * -(2 * pageOffset + pageMargin);
                if (ui.viewpager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(ui.viewpager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.setTranslationX(-myOffset);
                    } else {
                        page.setTranslationX(myOffset);
                    }
                } else {
                    page.setTranslationY(myOffset);
                }
            }
        });
        try {
            setVideo();
        }catch (Exception e){

        }
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void onResume(){
        super.onResume();
        checkPushCode();

        FirebaseMessagingService.notifyMessageTest(this, new PushVO(), PushCode.CAT_0E);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        releaseVideo();
    }

    private void releaseVideo(){
        if(simpleExoPlayer!=null){
            ui.exoPlayerView.getOverlayFrameLayout().removeAllViews();
            ui.exoPlayerView.setPlayer(null);
            simpleExoPlayer.release();
            simpleExoPlayer=null;
        }
    }


    private void setVideo() throws RawResourceDataSource.RawResourceDataSourceException {
//            String path = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";

        DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.rain_mob));
        final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(this);
        rawResourceDataSource.open(dataSpec);
        DataSource.Factory factory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return rawResourceDataSource;
            }
        };

        MediaSource audioSource = new ProgressiveMediaSource.Factory(factory).createMediaSource(rawResourceDataSource.getUri());
        LoopingMediaSource mediaSource = new LoopingMediaSource(audioSource);


//            String path = "android_asset://sky.mp4";
//            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));
//            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(path));


            simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.setVolume(0);
            simpleExoPlayer.setRepeatMode(REPEAT_MODE_ALL);
            simpleExoPlayer.setSeekParameters(null);
            ui.exoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.prepare(mediaSource);
            ui.exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            ui.exoPlayerView.setUseController(false);

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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( (requestCode == RequestCodes.REQ_CODE_PERMISSIONS_MEDIAPROJECTION.getCode() && resultCode == RESULT_OK)
                ||(requestCode == RequestCodes.REQ_CODE_PLAY_VIDEO.getCode())
        ) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof FragFourth) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                    return;
                }
            }
        }

    }

    public ViewPager2 getViewPager(){
        return ui.viewpager;
    }
}
