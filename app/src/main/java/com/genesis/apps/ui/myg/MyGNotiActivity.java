package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.MYP_8005;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.databinding.ActivityNotiListBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.NotiAccodianRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjpark
 * @brief 공지사항
 */
public class MyGNotiActivity extends SubActivity<ActivityNotiListBinding> {
    private final String TAG = MyGNotiActivity.class.getSimpleName();
    private MYPViewModel mypViewModel;
    private NotiAccodianRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_list);
        ui.lTitle.title.setText(R.string.title_noti);
        ui.setLifecycleOwner(this);


        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
        mypViewModel.getRES_MYP_8005().observe(this, responseNetUIResponse -> {

            switch (responseNetUIResponse.status){
                case SUCCESS:
                     showProgressDialog(false);
                    //추가할 아이템이 있을 경우만 adaper 갱신
                    if(responseNetUIResponse.data!=null&&responseNetUIResponse.data.getNotiList()!=null&&responseNetUIResponse.data.getNotiList().size()>0){
                        int itemSizeBefore = adapter.getItemCount();
                        if (adapter.getPageNo() == 0) {
                            adapter.setRows(responseNetUIResponse.data.getNotiList());
//                            adapter.setRows(getListData());
                        } else {
                            adapter.addRows(responseNetUIResponse.data.getNotiList());
//                          adapter.addRows(getListData());
//                          Log.e(TAG, "itemSizeBefore:"+itemSizeBefore +"   currentSize:"+adapter.getItemCount());
                        }
                        adapter.setPageNo(adapter.getPageNo() + 1);
//                      adapter.notifyDataSetChanged();
                        adapter.notifyItemRangeInserted(itemSizeBefore, adapter.getItemCount());
                    }
                    break;
                case LOADING:
                    showProgressDialog(true);
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }

//                int itemSizeBefore = adapter.getItemCount();
//                if(adapter.getPageNo()==0) {
////                    adapter.setRows(responseNetUIResponse.data.getNotiList());
//                    adapter.setRows(getListData());
//                }else{
////                    adapter.addRows(responseNetUIResponse.data.getNotiList());
//                    adapter.addRows(getListData());
////                    Log.e(TAG, "itemSizeBefore:"+itemSizeBefore +"   currentSize:"+adapter.getItemCount());
//                }
//                adapter.setPageNo(adapter.getPageNo()+1);
////                adapter.notifyDataSetChanged();
//                adapter.notifyItemRangeInserted(itemSizeBefore, adapter.getItemCount());
        });


        adapter = new NotiAccodianRecyclerAdapter();
//        ((SimpleItemAnimator) ui.rvNoti.getItemAnimator()).setSupportsChangeAnimations(true);
        ui.rvNoti.setLayoutManager(new LinearLayoutManager(this));
        ui.rvNoti.setHasFixedSize(true);
        ui.rvNoti.setAdapter(adapter);

        ui.rvNoti.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!ui.rvNoti.canScrollVertically(-1)) {
                    //top
                } else if (!ui.rvNoti.canScrollVertically(1)) {
                    //end
                    mypViewModel.reqMYP8005(new MYP_8005.Request(APPIAInfo.MG_NOTICE01.getId(),(adapter.getPageNo()+1)+"","20"));
                } else {
                    //idle
                }

            }
        });

        mypViewModel.reqMYP8005(new MYP_8005.Request(APPIAInfo.MG_NOTICE01.getId(), "1","20"));
    }

    @Override
    public void onClickCommon(View v) {

    }

    private List<NotiVO> getListData(){
        List<NotiVO> list = new ArrayList<>();
        list.add(new NotiVO("","", "공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다.", "공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다. 공지사항 상세 내용 입니다.", "20200902110522"));
        for(int i=0; i<10; i++){
            list.add(new NotiVO("",i+"", "test"+i, "content"+i, "20200802180522"));
        }
        return list;
    }
}
