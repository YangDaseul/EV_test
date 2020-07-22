package com.genesis.apps.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityMainBinding;
import com.genesis.apps.ui.fragment.main.MyAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends SubActivity<ActivityMainBinding> {
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 4;


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
    }

    @Override
    public void onResume(){
        super.onResume();
        checkPushCode();
    }

}
