package com.genesis.apps.ui.fragment.main;

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
import com.genesis.apps.comm.model.map.AroundPOIReqVO;
import com.genesis.apps.comm.model.map.MapViewModel;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.databinding.Frame1pBinding;
import com.genesis.apps.ui.fragment.SubFragment;
import com.hmns.playmap.extension.PlayMapPoiItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FragFirst extends SubFragment<Frame1pBinding> {
//    private ExampleViewModel exampleViewModel;
//    private WeatherPointViewModel weatherPointViewModel;

    private MapViewModel mapViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_1p);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.tvName1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mapViewModel.reqPlayMapPoiItemList(new AroundPOIReqVO("주유소",37.56719394162535,126.97875114212447,1000,1,1,0,20));
                mapViewModel.reqTestCount();
            }
        });


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


//        exampleViewModel = new ViewModelProvider(getActivity()).get(ExampleViewModel.class);
//
//
//        ExampleReqVO exampleReqVO = new ExampleReqVO();
//        exampleReqVO.setRequestID("park");
//        exampleViewModel.reqData(exampleReqVO,"","");

//        weatherPointViewModel = new ViewModelProvider(getActivity()).get(WeatherPointViewModel.class);
//        weatherPointViewModel.reqData();
//
//        final Observer<NetUIResponse<WeatherPointResVO>> weatherPointResVOObserver = new Observer<NetUIResponse<WeatherPointResVO>>() {
//            @Override
//            public void onChanged(NetUIResponse<WeatherPointResVO> weatherPointResVONetUIResponse) {
//
//            }
//        };
//
//        weatherPointViewModel.getWeatherPoint().observe(getActivity(), weatherPointResVOObserver);
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
        String srcFile = "snow.webp";
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
