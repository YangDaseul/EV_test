package com.genesis.apps.ui.main;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.genesis.apps.R;
import com.genesis.apps.databinding.FragmentHomeBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.home.FragmentHome1;
import com.genesis.apps.ui.main.home.FragmentHome2;
import com.genesis.apps.ui.main.home.view.VehicleViewpagerAdapter;
import com.genesis.apps.ui.main.insight.FragmentInsight;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FragmentHome extends SubFragment<FragmentHomeBinding> {
//    private ExampleViewModel exampleViewModel;
//    private WeatherPointViewModel weatherPointViewModel;
//    private MapViewModel mapViewModel;

    private final int VIEWPAGER_VERTICAL_NUMBER=2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        me.vpVehicle.setAdapter(new VehicleViewpagerAdapter(getActivity(), VIEWPAGER_VERTICAL_NUMBER));
        me.vpVehicle.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        me.vpVehicle.setCurrentItem(0);
        me.vpVehicle.setOffscreenPageLimit(VIEWPAGER_VERTICAL_NUMBER);
        me.vpVehicle.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    me.vpVehicle.setCurrentItem(position);
                }

                if(position==0){
                    ((MainActivity)getActivity()).ui.lGnb.lWhole.setVisibility(View.VISIBLE);
                }else{
                    ((MainActivity)getActivity()).ui.lGnb.lWhole.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

    }


    @Override
    public void onRefresh() {
        if (me.vpVehicle.getCurrentItem() == 0) {
            ((MainActivity)getActivity()).setGNB(false, false, 1, View.VISIBLE);
        } else {
            ((MainActivity)getActivity()).setGNB(false, false, 1, View.GONE);
        }
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
