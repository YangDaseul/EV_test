package com.genesis.apps.ui.common.fragment.main;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.genesis.apps.R;
import com.genesis.apps.databinding.Frame1pBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FragFirst extends SubFragment<Frame1pBinding> {
//    private ExampleViewModel exampleViewModel;
//    private WeatherPointViewModel weatherPointViewModel;
//    private MapViewModel mapViewModel;

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

        me.vpVehicle.setAdapter(new VehicleViewpagerAdapter(getActivity(), 2));
        //ViewPager Setting
        me.vpVehicle.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        me.vpVehicle.setCurrentItem(0);
        me.vpVehicle.setOffscreenPageLimit(2);

        me.vpVehicle.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    me.vpVehicle.setCurrentItem(position);
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


//        me.vpVehicle.setPageTransformer(new MarginPageTransformer(0));
//        me.vpVehicle.setPadding(0,0,0,0);

//        me.vpVehicle.setPageTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float myOffset = position * -(2 * pageOffset + pageMargin);
//                if (me.vpVehicle.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
//                    if (ViewCompat.getLayoutDirection(me.vpVehicle) == ViewCompat.LAYOUT_DIRECTION_LTR) {
//                        page.setTranslationX(-myOffset);
//                    } else {
//                        page.setTranslationX(myOffset);
//                    }
//                } else {
//                    page.setTranslationY(myOffset);
//                }
//            }
//        });



//        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
//        me.setLifecycleOwner(getViewLifecycleOwner());
//        me.tvName1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                mapViewModel.reqPlayMapPoiItemList(new AroundPOIReqVO("주유소",37.56719394162535,126.97875114212447,1000,1,1,0,20));
//                mapViewModel.reqTestCount();
//            }
//        });
//
//
//        getViewLifecycleOwnerLiveData().observe(getViewLifecycleOwner(), new Observer<LifecycleOwner>() {
//            @Override
//            public void onChanged(LifecycleOwner lifecycleOwner) {
//
//            }
//        });
//
//
//        mapViewModel.getPlayMapPoiItemList().observe(getActivity(), new Observer<NetUIResponse<ArrayList<PlayMapPoiItem>>>() {
//            @Override
//            public void onChanged(NetUIResponse<ArrayList<PlayMapPoiItem>> arrayListNetUIResponse) {
//                Log.v("test", "test1:" + arrayListNetUIResponse);
//
//            }
//        });
//
//        mapViewModel.getTestCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                Log.v("test", "First:" + integer);
//            }
//        });


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
