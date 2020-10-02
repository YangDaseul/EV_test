package com.genesis.apps.ui.common.fragment.main;

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
import com.genesis.apps.databinding.FragVehicleTest1Binding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.hmns.playmap.extension.PlayMapPoiItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FragVehicle1 extends SubFragment<FragVehicleTest1Binding> {
    private MapViewModel mapViewModel;
    private LGNViewModel lgnViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frag_vehicle_test_1);
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


    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void onRefresh() {
//        Glide.with(this).load(R.drawable.snow).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(me.gifImage);
//.optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(circleCrop))
//        Glide.with(this).load(setupSampleFile()).transition(DrawableTransitionOptions.with(new DrawableAlwaysCrossFadeFactory())) .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)).   into(me.gifImage);

//        Glide.with(this).load(setupSampleFile()).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)).into(me.gifImage);

//        me.lottieView.playAnimation();
//        me.lottieView.loop(true);
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


}
