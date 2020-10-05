package com.genesis.apps.ui.main.home;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.genesis.apps.R;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.MapViewModel;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.databinding.FragmentHome1Binding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.hmns.playmap.extension.PlayMapPoiItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.google.android.exoplayer2.Player.REPEAT_MODE_ALL;

public class FragmentHome1 extends SubFragment<FragmentHome1Binding> {

    private SimpleExoPlayer simpleExoPlayer;

    private MapViewModel mapViewModel;
    private LGNViewModel lgnViewModel;

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

        mapViewModel = new ViewModelProvider(getActivity()).get(MapViewModel.class);
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setActivity((MainActivity)getActivity());

        getViewLifecycleOwnerLiveData().observe(getViewLifecycleOwner(), new Observer<LifecycleOwner>() {
            @Override
            public void onChanged(LifecycleOwner lifecycleOwner) {

            }
        });


        mapViewModel.getPlayMapPoiItemList().observe(getActivity(), new Observer<NetUIResponse<ArrayList<PlayMapPoiItem>>>() {
            @Override
            public void onChanged(NetUIResponse<ArrayList<PlayMapPoiItem>> arrayListNetUIResponse) {
                Log.v("test", "test1:" + arrayListNetUIResponse);

            }
        });

        mapViewModel.getTestCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.v("test", "First:" + integer);
            }
        });

        setVideo();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void onRefresh() {
        videoPauseAndResume(true);
//        Glide.with(this).load(R.drawable.snow).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(me.gifImage);
//.optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(circleCrop))
//        Glide.with(this).load(setupSampleFile()).transition(DrawableTransitionOptions.with(new DrawableAlwaysCrossFadeFactory())) .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)).   into(me.gifImage);

//        Glide.with(this).load(setupSampleFile()).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)).into(me.gifImage);

//        me.lottieView.playAnimation();
//        me.lottieView.loop(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        videoPauseAndResume(false);
    }

    class DrawableAlwaysCrossFadeFactory implements TransitionFactory<Drawable> {
        private DrawableCrossFadeTransition resourceTransition = new DrawableCrossFadeTransition(2000, true);

        @Override
        public Transition<Drawable> build(DataSource dataSource, boolean isFirstResource) {
            return resourceTransition;
        }
    }



    private String setupSampleFile() {
        AssetManager assetManager = getActivity().getAssets();
        String srcFile = "rainfall_s.webp";
        String destFile = getActivity().getFilesDir().getAbsolutePath() + File.separator + srcFile;
        copyFile(assetManager, srcFile, destFile);
        return destFile;
    }

    private void copyFile(AssetManager assetManager, String srcFile, String destFile) {
        try {
            InputStream is = assetManager.open(srcFile);
            FileOutputStream os = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) != -1) {
                os.write(buffer, 0, read);
            }
            is.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        simpleExoPlayer.setPlayWhenReady(isResume);
        simpleExoPlayer.getPlaybackState();
    }

}
