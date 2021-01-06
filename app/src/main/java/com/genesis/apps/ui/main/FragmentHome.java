package com.genesis.apps.ui.main;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.FragmentHomeBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.home.view.VehicleViewpagerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FragmentHome extends SubFragment<FragmentHomeBinding> {
//    private ExampleViewModel exampleViewModel;
//    private WeatherPointViewModel weatherPointViewModel;
//    private MapViewModel mapViewModel;

    private final int VIEWPAGER_VERTICAL_NUMBER=2;
    private LGNViewModel lgnViewModel;
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
        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
        me.vpVehicle.setAdapter(new VehicleViewpagerAdapter(this, VIEWPAGER_VERTICAL_NUMBER));
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

                if (position == 0) {
                    ((MainActivity) getActivity()).ui.lGnb.lWhole.setVisibility(View.VISIBLE);
                } else {
                    ((MainActivity) getActivity()).ui.lGnb.lWhole.setVisibility(View.INVISIBLE);
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
            ((MainActivity)getActivity()).setGNB(false, 1, View.VISIBLE);
        } else {
            ((MainActivity)getActivity()).setGNB(false, 1, View.GONE);
        }

        VehicleVO vehicleVO = null;
        try{
            vehicleVO = lgnViewModel.getMainVehicleFromDB();
        }catch (Exception e){
            vehicleVO = null;
        }finally {
            //소유차량이 아닌 고객은
            if (vehicleVO==null || TextUtils.isEmpty(vehicleVO.getCustGbCd()) || !vehicleVO.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV)) {
                me.vpVehicle.setUserInputEnabled(false);
            } else {
                me.vpVehicle.setUserInputEnabled(true);
            }
        }

    }

}
