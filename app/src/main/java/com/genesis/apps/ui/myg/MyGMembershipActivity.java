package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.MYP_2001;
import com.genesis.apps.comm.model.gra.viewmodel.MYPViewModel;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.databinding.ActivityMygMembershipBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.CardHorizontalAdapter;
import com.google.gson.Gson;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

public class MyGMembershipActivity extends SubActivity<ActivityMygMembershipBinding> {

    private MYPViewModel mypViewModel;
    private CardHorizontalAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_membership);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
        ui.setLifecycleOwner(this);



        adapter = new CardHorizontalAdapter(onSingleClickListener);
        ui.viewpager.setAdapter(adapter);
        ui.viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//        ui.viewpager.setCurrentItem(0);

        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.offset2);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset2);
        ui.viewpager.setPageTransformer((page, position) -> {
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

        });



        mypViewModel.getRES_MYP_2001().observe(this, result -> {

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
                    "      \"cardNo\": \"5555555550123456\",\n" +
                    "      \"cardNm\": \"블루멤버스\",\n" +
                    "      \"cardStusNm\": \"정상\",\n" +
                    "      \"cardClsNm\": \"신용카드\",\n" +
                    "      \"cardKindNm\": \"현대가상화카드2\",\n" +
                    "      \"cardIsncSubspDt\": \"20200802\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"cardNo\": \"2222222220123456\",\n" +
                    "      \"cardNm\": \"블루멤버스\",\n" +
                    "      \"cardStusNm\": \"발급중\",\n" +
                    "      \"cardClsNm\": \"신용카드\",\n" +
                    "      \"cardKindNm\": \"현대가상화카드2\",\n" +
                    "      \"cardIsncSubspDt\": \"20200902\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"cardNo\": \"\",\n" +
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
            mypViewModel.reqNewCardList(sample.getBlueMbrCrdList());
        });

        mypViewModel.getCardVoList().observe(this, result -> {
            ui.viewpager.setOffscreenPageLimit(result.data.size());
            adapter.setRows(result.data);
//            adapter.applyFilter();
//            adapter.addCard();
            adapter.notifyDataSetChanged();
            //이동효과를 주는데 노티파이체인지와 딜레이없이 콜하면 효과가 중첩되어 사라저서 100ms 후 처리 진행
            new Handler().postDelayed(() -> ui.viewpager.setCurrentItem(0,true),100);
        });

        mypViewModel.reqMYP2001(new MYP_2001.Request(APPIAInfo.MG_MEMBER01.getId()));

    }

    @Override
    public void onClickCommon(View v) {

        int pos=-1;
        try{
            pos = Integer.parseInt(v.getTag(R.id.item_position).toString());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }

        switch (v.getId()){
            case R.id.l_whole:
                break;
            case R.id.iv_favorite:
                if( !((CardVO)adapter.getItem(pos)).isFavorite() && !TextUtils.isEmpty(((CardVO)adapter.getItem(pos)).getCardNo())){
                    mypViewModel.reqChangeFavoriteCard(((CardVO)adapter.getItem(pos)).getCardNo(), adapter.getItems());
                }

                break;

        }

    }
}
