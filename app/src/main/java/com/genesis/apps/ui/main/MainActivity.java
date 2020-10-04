package com.genesis.apps.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.databinding.ActivityMainBinding;
import com.genesis.apps.databinding.ItemTabBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
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

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import static com.google.android.exoplayer2.Player.REPEAT_MODE_ALL;

public class MainActivity extends SubActivity<ActivityMainBinding> {
    private final int pageNum = 4;
    public FragmentStateAdapter pagerAdapter;
    private SimpleExoPlayer simpleExoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getDataFromIntent();
        setViewModel();
        setObserver();

        initView();
        
        try {
            setVideo();
        }catch (Exception e){

        }
    }

    private void initView() {
        pagerAdapter = new MainViewpagerAdapter(this, pageNum);
        ui.viewpager.setAdapter(pagerAdapter);
        setTabView();

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

//                ui.indicator.animatePageSelected(position%num_page);
            }

        });

        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        ui.viewpager.setPageTransformer((page, position) -> {
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
        });
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void onResume(){
        super.onResume();
        checkPushCode();

//        FirebaseMessagingService.notifyMessageTest(this, new PushVO(), PushCode.CAT_0E);
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

    private final int TAB_INFO[][]={
            {R.string.main_word_1, R.drawable.ic_tabbar_home},
            {R.string.main_word_2, R.drawable.ic_tabbar_insight},
            {R.string.main_word_3, R.drawable.ic_tabbar_service},
            {R.string.main_word_4, R.drawable.ic_tabbar_contents}
    };

    private void setTabView(){
        new TabLayoutMediator(ui.tabs, ui.viewpager, (tab, position) -> {

        }).attach();

        for(int i=0 ; i<pageNum; i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTabBinding itemTabBinding = DataBindingUtil.inflate(inflater, R.layout.item_tab, null, false);
            final View view = itemTabBinding.getRoot();
            itemTabBinding.tvTab.setText(TAB_INFO[i][0]);
            itemTabBinding.ivTab.setImageResource(TAB_INFO[i][1]);
            ui.tabs.getTabAt(i).setCustomView(view);
        }
    }

}
