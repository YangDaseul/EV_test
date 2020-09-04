package com.genesis.apps.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.VibratorUtil;
import com.genesis.apps.databinding.ActivityVerticalOverlapExampleBinding;
import com.genesis.apps.ui.activity.test.TestCardViewAdapter;
import com.genesis.apps.ui.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.view.listview.Link;
import com.genesis.apps.ui.view.listview.LinkDiffCallback;

import java.util.LinkedList;
import java.util.List;

public class CardViewActivity extends SubActivity<ActivityVerticalOverlapExampleBinding> {

    private TestCardViewAdapter testCardViewAdapter;
    private final int offsetPageLimit=3;
    private boolean animationStartNeeded = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_overlap_example);

        ui.viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        ui.viewPager.setOffscreenPageLimit(offsetPageLimit);
        testCardViewAdapter = new TestCardViewAdapter(new LinkDiffCallback(), null);
        testCardViewAdapter.submitList(getListData());
        ui.viewPager.setAdapter(testCardViewAdapter);

        ui.pagerContainer.setOverlapSlider(0f,0f,0.2f,0f);
//        ui.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//
//                switch (state) {
//                    case ViewPager2.SCROLL_STATE_IDLE:
//                        animationStartNeeded = true;
//                        VibratorUtil.doVibratorLong(getApplication());
//                        break;
//                    default:
//                        if(animationStartNeeded){
//                            animationStartNeeded=false;
//                        }
//                        break;
//                }
//
//                super.onPageScrollStateChanged(state);
//            }
//        });

//        new Handler().postDelayed(() -> {
//            testCardViewAdapter.submitList(getListData());
//        }, 2000);

        ui.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(testCardViewAdapter!=null){
                    switch (testCardViewAdapter.getVIEW_TYPE()) {
                        case TestCardViewAdapter.TYPE_CARD:
                            testCardViewAdapter = new TestCardViewAdapter(new LinkDiffCallback(), null);
                            testCardViewAdapter.setVIEW_TYPE(TestCardViewAdapter.TYPE_LINE);
                            ui.pagerContainer.setVisibility(View.GONE);
                            ui.recyclerView.setLayoutManager(new LinearLayoutManager(CardViewActivity.this));
                            ui.recyclerView.setVisibility(View.VISIBLE);
                            ui.recyclerView.setAdapter(testCardViewAdapter);

                            break;
                        case TestCardViewAdapter.TYPE_LINE:
                            testCardViewAdapter = new TestCardViewAdapter(new LinkDiffCallback(), null);
                            testCardViewAdapter.setVIEW_TYPE(TestCardViewAdapter.TYPE_CARD);
                            ui.pagerContainer.setVisibility(View.VISIBLE);
                            ui.recyclerView.setVisibility(View.GONE);
                            ui.viewPager.setAdapter(testCardViewAdapter);
                            break;
                    }

                    testCardViewAdapter.submitList(getListData());
                }
            }
        });
    }

    private List<Link> getListData(){
        List<Link> links = new LinkedList<Link>();

        Link link = new Link();
        link.setIcon(R.drawable.building5);
        link.setTitle("HMKCODE BLOG");
        link.setUrl("hmkcode.com");

        links.add(link);

        link = new Link();
        link.setIcon(R.drawable.building4);
        link.setTitle("@HMKCODE");
        link.setUrl("twitter.com/hmkcode");

        links.add(link);

        link = new Link();
        link.setIcon(R.drawable.building3);
        link.setTitle("HMKCODE");
        link.setUrl("github.com/hmkcode");

        links.add(link);
        return links;
    }
}
