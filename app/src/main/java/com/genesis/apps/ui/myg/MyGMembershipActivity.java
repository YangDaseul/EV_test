package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.MYP_2001;
import com.genesis.apps.comm.model.gra.viewmodel.MYPViewModel;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.databinding.ActivityMygMembershipBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.CardHorizontalAdapter;
import com.google.gson.Gson;

public class MyGMembershipActivity extends SubActivity<ActivityMygMembershipBinding> {

    private MYPViewModel mypViewModel;
    private CardHorizontalAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_membership);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
        ui.setLifecycleOwner(this);



        adapter = new CardHorizontalAdapter(this);
        ui.viewpager.setAdapter(adapter);
        ui.viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        ui.viewpager.setCurrentItem(0);

        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.offset2);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset2);

        ui.viewpager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float myOffset = position * -(2 * pageOffset + pageMargin);
                if (position < -1) {
                    page.setTranslationX(-myOffset);
                } else if (position <= 1) {
                    float scaleFactor = Math.max(0.8f, 1 - Math.abs(position - 0.14285715f));
                    page.setTranslationX(myOffset);
                    page.setScaleY(scaleFactor);
                    page.setAlpha(scaleFactor);
                } else {
                    page.setAlpha(0f);
                    page.setTranslationX(myOffset);
                }

            }
        });



        mypViewModel.getRES_MYP_2001().observe(this, new Observer<NetUIResponse<MYP_2001.Response>>() {
            @Override
            public void onChanged(NetUIResponse<MYP_2001.Response> result) {

                String test ="{\n" +
                        "  \"rsltCd\": \"0000\",\n" +
                        "  \"rsltMsg\": \"성공\",\n" +
                        "  \"blueMbrYn\": \"Y\",\n" +
                        "  \"bludMbrPoint\": \"1000000\",\n" +
                        "  \"usedBlueMbrPoint\": \"2000\",\n" +
                        "  \"savgPlanPont\": \"30000\",\n" +
                        "  \"extncDtm\": \"20200930\",\n" +
                        "  \"extncPont\": \"215487\",\n" +
                        "  \"extncPont6mm\": \"15481512\",\n" +
                        "  \"blueMbrCrdCnt\": \"3\",\n" +
                        "  \"blueMbrCrdList\": [\n" +
                        "    {\n" +
                        "      \"cardNo\": \"1234567890123456\",\n" +
                        "      \"cardNm\": \"블루멤버스\",\n" +
                        "      \"cardStusNm\": \"정상\",\n" +
                        "      \"cardClsNm\": \"신용카드\",\n" +
                        "      \"cardKindNm\": \"현대가상화카드1\",\n" +
                        "      \"cardIsncSubspDt\": \"20200901\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"cardNo\": \"2222222220123456\",\n" +
                        "      \"cardNm\": \"블루멤버스\",\n" +
                        "      \"cardStusNm\": \"정상\",\n" +
                        "      \"cardClsNm\": \"신용카드\",\n" +
                        "      \"cardKindNm\": \"현대가상화카드2\",\n" +
                        "      \"cardIsncSubspDt\": \"20200902\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"cardNo\": \"3333333330123456\",\n" +
                        "      \"cardNm\": \"블루멤버스\",\n" +
                        "      \"cardStusNm\": \"정상\",\n" +
                        "      \"cardClsNm\": \"신용카드\",\n" +
                        "      \"cardKindNm\": \"현대가상화카드3\",\n" +
                        "      \"cardIsncSubspDt\": \"20200903\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";
                MYP_2001.Response sample = new Gson().fromJson(test, MYP_2001.Response.class);
                ui.setData(sample);
                ui.viewpager.setOffscreenPageLimit(sample.getBlueMbrCrdList().size());
                adapter.setRows(sample.getBlueMbrCrdList());
                adapter.applyFilter();
                adapter.addCard();
                adapter.notifyDataSetChanged();
            }
        });

        mypViewModel.reqMYP2001(new MYP_2001.Request(APPIAInfo.MG_MEMBER01.getId()));

    }

    @Override
    public void onSingleClick(View v) {
    }
}
