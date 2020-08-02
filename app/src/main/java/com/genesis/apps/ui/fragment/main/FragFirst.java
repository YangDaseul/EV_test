package com.genesis.apps.ui.fragment.main;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.ExampleReqVO;
import com.genesis.apps.comm.model.ExampleViewModel;
import com.genesis.apps.databinding.Frame1pBinding;
import com.genesis.apps.ui.fragment.SubFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ListAdapter;

public class FragFirst extends SubFragment<Frame1pBinding> {
    private ExampleViewModel exampleViewModel;

//    private WeatherPointViewModel weatherPointViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_1p);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        exampleViewModel = new ViewModelProvider(getActivity()).get(ExampleViewModel.class);


        ExampleReqVO exampleReqVO = new ExampleReqVO();
        exampleReqVO.setRequestID("park");
        exampleViewModel.reqData(exampleReqVO,"","");

//        final Observer<NetUIResponse<ExampleResVO>> observer = new Observer<NetUIResponse<ExampleResVO>>() {
//            @Override
//            public void onChanged(NetUIResponse<ExampleResVO> data) {
//                Log.v("testLiveData","DATA:" + data.data.getValue());
//            }
//        };
//        exampleViewModel.getResVo().observe(getActivity(), observer);

        me.tvName1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exampleViewModel.setAdd();
            }
        });


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
        Glide.with(this).load(setupSampleFile()).transition(DrawableTransitionOptions.with(new DrawableAlwaysCrossFadeFactory())) .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)).   into(me.gifImage);

//        Glide.with(this).load(setupSampleFile()).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)).into(me.gifImage);
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
        String srcFile = "sample1.webp";
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
