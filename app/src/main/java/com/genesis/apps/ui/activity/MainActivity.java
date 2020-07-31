package com.genesis.apps.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityMainBinding;
import com.genesis.apps.ui.fragment.main.MyAdapter;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.ogg.OggExtractor;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;

import static com.google.android.exoplayer2.Player.REPEAT_MODE_ALL;
import static com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL;
import static com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT;

public class MainActivity extends SubActivity<ActivityMainBinding> {
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 4;
    private SimpleExoPlayer simpleExoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ui.button.setOnClickListener(view -> startActivitySingleTop(new Intent(MainActivity.this, EntranceActivity.class),RequestCodes.REQ_CODE_DEFAULT.getCode()));

        //ViewPager2
        //Adapter
        pagerAdapter = new MyAdapter(this, num_page);
        ui.viewpager.setAdapter(pagerAdapter);
        //Indicator
        ui.indicator.setViewPager(ui.viewpager);
        ui.indicator.createIndicators(num_page,0);

        new TabLayoutMediator(ui.tabs, ui.viewpager, (tab, position) -> tab.setText("Tab " + (position + 1))).attach();

        //ViewPager Setting
        ui.viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        ui.viewpager.setCurrentItem(0);
        ui.viewpager.setOffscreenPageLimit(3);


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
    public void onResume(){
        super.onResume();
        checkPushCode();
    }

    private void setVideo() throws RawResourceDataSource.RawResourceDataSourceException {
//            String path = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";

        DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.sky));
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
            ui.exoPlayerView.setResizeMode(RESIZE_MODE_FIXED_HEIGHT);
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


}