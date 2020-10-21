package com.genesis.apps.ui.main.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.LGN_0002;
import com.genesis.apps.comm.model.gra.api.LGN_0003;
import com.genesis.apps.comm.model.vo.MainHistVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.FragmentHome2Binding;
import com.genesis.apps.databinding.ItemAsanListBinding;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class FragmentHome2 extends SubFragment<FragmentHome2Binding>{
    boolean isScrollTop=false;
    boolean isTopDirection=false;
    float beforeYPosition=0;
    private LGNViewModel lgnViewModel;
    private VehicleVO vehicleVO;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_home_2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setFragment(this);

        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
        lgnViewModel.getRES_LGN_0003().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case SUCCESS:
                    setViewAsanList(result.data.getAsnHistList());
                    setViewBtr(result.data.getButlSubsCd());
                    break;
            }
        });

        lgnViewModel.getRES_LGN_0002().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case SUCCESS:


                    break;
            }
        });

        initView();
     }

    private void setViewBtr(String butlSubsCd) {
        switch (butlSubsCd){
            case VariableType.BTR_APPLY_CODE_2000:
                me.tvBtrApply.setVisibility(View.GONE);
                me.tvBtrStatus.setVisibility(View.GONE);
                me.ivBtrArrow.setVisibility(View.VISIBLE);
                me.lBtr.setOnClickListener(view -> onSingleClickListener.onClick(view));
                break;
            case VariableType.BTR_APPLY_CODE_3000:
                me.tvBtrApply.setVisibility(View.GONE);
                me.tvBtrStatus.setVisibility(View.VISIBLE);
                me.ivBtrArrow.setVisibility(View.VISIBLE);
                me.lBtr.setOnClickListener(view -> onSingleClickListener.onClick(view));
                break;
            case VariableType.BTR_APPLY_CODE_1000:
            default:
                me.tvBtrApply.setVisibility(View.VISIBLE);
                me.tvBtrStatus.setVisibility(View.GONE);
                me.ivBtrArrow.setVisibility(View.GONE);
                me.lBtr.setOnClickListener(null);
                break;
        }
    }

    private void setViewAsanList(List<MainHistVO> list){

        initViewAsanList();

        if(list==null||list.size()==0){
            me.tvAsanEmpty.setVisibility(View.VISIBLE);
        }else{
            me.tvAsanEmpty.setVisibility(View.GONE);
            for(MainHistVO mainHistVO : list){
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                param.bottomMargin= (int)DeviceUtil.dip2Pixel(getContext(),4);
                final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final ItemAsanListBinding itemAsanListBinding = DataBindingUtil.inflate(inflater, R.layout.item_asan_list, null, false);
                final View view = itemAsanListBinding.getRoot();
                itemAsanListBinding.setData(mainHistVO);
                view.setLayoutParams(param);
                me.lAsan.addView(view);
            }
        }
    }

    private void initViewAsanList(){
        //empty뷰를 제외한 정비 리스트ㅡ 아이템 삭제
        while(me.lAsan.getChildCount()!=1){
            for(int i=0; i<me.lAsan.getChildCount(); i++){
                if(me.lAsan.getChildAt(i).getId()!=me.tvAsanEmpty.getId()) {
                    me.lAsan.removeView(me.lAsan.getChildAt(i));
                }
            }
        }
    }

    private void initView() {
        me.scroll.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
            if(!me.scroll.canScrollVertically(-1)){
                isScrollTop=true;//TOP
            }else if(!me.scroll.canScrollVertically(1)){
                //END
            }
        });
        me.scroll.setOnTouchListener((view, motionEvent) -> {
            int action = motionEvent.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    Log.v("scroll", "scroll:down:" + motionEvent.getY() + "   scrollbar position:" + me.scroll.getScrollY() + "isScrrlltop:"+isScrollTop);
                    beforeYPosition=motionEvent.getY();

                    if(!isScrollTop)
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                    else//POSITION이 TOP이 아니면 부모 VIEWPager의 터치를 막고 시작 (아래로 스크롤이 잘되게 하기 위해서)
                        view.getParent().requestDisallowInterceptTouchEvent(false);

                    break;

                case MotionEvent.ACTION_MOVE:
                    Log.v("scroll", "scroll:move:" + motionEvent.getY() + "   scrollbar position:" + me.scroll.getScrollY());

                    //사용자가 스크롤을 시작한 시점(ACTION_DOWN)부터 무빙하기 시작하면 최초1회만 (beforeYPosition!=0) 방향성을 확인한다.
                    if(beforeYPosition!=0&& beforeYPosition-motionEvent.getY()<0){ //스크롤 방향성이 위에일때
                        beforeYPosition=0;
                        isTopDirection = true;
                    }else if(beforeYPosition!=0&& beforeYPosition-motionEvent.getY()>0){ //스크롤 방향성이 아래일때
                        beforeYPosition=0;
                        isTopDirection = false;
                        view.getParent().requestDisallowInterceptTouchEvent(true); //부모의 ViewPager에서 스크롤이 되지 않도록 처리
                    }

                    if(isTopDirection&&me.scroll.getScrollY()==0) { //방향성이 위를 향하고 스크롤 Y 포지션이 가장 최상단이면
                        view.getParent().requestDisallowInterceptTouchEvent(false); //부모의 ViewPager가 다시 스크롤 가능하도록 처리
                    }

                    isScrollTop = false;
                    break;

                case MotionEvent.ACTION_UP:
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    Log.v("scroll", "scroll:up:" + motionEvent.getY() + "   scrollbar position:" + me.scroll.getScrollY()+ "   size:"+me.scroll.getScrollBarSize() );
                    break;
            }

            // Handle MapView's touch events.
            return false;
        });
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.tv_btr_apply:
                MiddleDialog.dialogBtrApply(getActivity(), () -> {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + getString(R.string.word_home_14))));
                }, () -> {

                });
                break;
            case R.id.tv_title_btr_term:
                String annMgmtCd;
                try{
                    annMgmtCd = "BTR_"+vehicleVO.getMdlNm();
                    ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), BtrServiceInfoActivity.class).putExtra(KeyNames.KEY_NAME_ADMIN_CODE, annMgmtCd), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_NONE);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.l_btr:
                ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), BtrBluehandsActivity.class).putExtra(KeyNames.KEY_NAME_VIN, vehicleVO.getVin()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
        }
    }


    @Override
    public void onRefresh() {
        Log.e("onResume","onReusme FragmentHome2");
        try {
            vehicleVO = lgnViewModel.getMainVehicleFromDB();
        }catch (Exception e){
            e.printStackTrace();;
        }

        lgnViewModel.reqLGN0002(new LGN_0002.Request(APPIAInfo.GM01.getId(), vehicleVO.getVin()));
        lgnViewModel.reqLGN0003(new LGN_0003.Request(APPIAInfo.GM01.getId(), vehicleVO.getVin()));
        ((MainActivity)getActivity()).setGNB(false, false, 1, View.GONE);
    }


    private void addAnimalsToList() {
//        testItemAdapter = new TestItemAdapter();
//        for (int i = 0; i < 30; i++) {
//            testItemAdapter.addRow(BaseRecyclerViewAdapter.Row.create("value:"+i,0));
//        }
//        me.firstRv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        me.firstRv.setAdapter(testItemAdapter);
    }





}
