package com.genesis.apps.ui.common.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.VibratorUtil;
import com.genesis.apps.databinding.ActivityVerticalOverlapExampleBinding;
import com.genesis.apps.ui.common.activity.test.CardViewAadapter;
import com.genesis.apps.ui.common.activity.test.ItemMoveCallback;
import com.genesis.apps.ui.common.view.listview.test.Link;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

public class CardViewActivity extends SubActivity<ActivityVerticalOverlapExampleBinding> {

    private CardViewAadapter testCardViewAdapter;
    private final int offsetPageLimit=3;
    private boolean animationStartNeeded = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_overlap_example);
        getDataFromIntent();
        setViewModel();
        setObserver();
        ui.viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        ui.viewPager.setOffscreenPageLimit(offsetPageLimit);
        testCardViewAdapter = new CardViewAadapter();
        testCardViewAdapter.addRows(getListData());
        ui.viewPager.setAdapter(testCardViewAdapter);

        ui.pagerContainer.setOverlapSlider(0f,0f,0.2f,0f);
        ui.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                VibratorUtil.doVibrator(getApplication());
            }

            @Override
            public void onPageScrollStateChanged(int state) {


                switch (state) {
                    case ViewPager2.SCROLL_STATE_IDLE:
                        animationStartNeeded = true;
                        break;
                    default:
                        if(animationStartNeeded){
                            animationStartNeeded=false;
                        }
                        break;
                }

                super.onPageScrollStateChanged(state);
            }
        });

//        new Handler().postDelayed(() -> {
//            testCardViewAdapter.submitList(getListData());
//        }, 2000);

        ui.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(testCardViewAdapter!=null){
                    switch (testCardViewAdapter.getViewType()) {
                        case CardViewAadapter.TYPE_CARD:
                            testCardViewAdapter = new CardViewAadapter();
                            testCardViewAdapter.setViewType(CardViewAadapter.TYPE_LINE);


                            ItemTouchHelper.Callback callback = new ItemMoveCallback(testCardViewAdapter);
                            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                            touchHelper.attachToRecyclerView(ui.recyclerView);


                            ui.pagerContainer.setVisibility(View.GONE);
                            ui.recyclerView.setLayoutManager(new LinearLayoutManager(CardViewActivity.this));
                            ui.recyclerView.setVisibility(View.VISIBLE);
                            ui.recyclerView.setAdapter(testCardViewAdapter);

                            break;
                        case CardViewAadapter.TYPE_LINE:
                            testCardViewAdapter = new CardViewAadapter();
                            testCardViewAdapter.setViewType(CardViewAadapter.TYPE_CARD);
                            ui.pagerContainer.setVisibility(View.VISIBLE);
                            ui.recyclerView.setVisibility(View.GONE);
                            ui.viewPager.setAdapter(testCardViewAdapter);
                            break;
                    }

                    testCardViewAdapter.setRows(getListData());
                    testCardViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {

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
