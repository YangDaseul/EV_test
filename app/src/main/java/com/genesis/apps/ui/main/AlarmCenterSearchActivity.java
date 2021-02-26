package com.genesis.apps.ui.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.NOT_0002;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.NotiInfoVO;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.databinding.ActivityAlarmCenterSearchBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AlarmCenterSearchActivity extends SubActivity<ActivityAlarmCenterSearchBinding> {

    private CMNViewModel cmnViewModel;
    private AlarmCenterRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_center_search);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        adapter = new AlarmCenterRecyclerAdapter(onSingleClickListener);
        ui.lSearchParent.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.lSearchParent.rv.setHasFixedSize(true);
        ui.lSearchParent.rv.setAdapter(adapter);

        ui.lSearchParent.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reqListData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        ui.lSearchParent.etSearch.setOnFocusChangeListener((view, hasFocus) -> {

            if (hasFocus) {
                reqListData(ui.lSearchParent.etSearch.getText().toString());
            } else {
                SoftKeyboardUtil.hideKeyboard(AlarmCenterSearchActivity.this, getWindow().getDecorView().getWindowToken());
            }

        });

        ui.lSearchParent.tvTitleSub.setText(R.string.mg00_word_3);
        ui.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
        //키보드에서 search 버튼 클릭할 경우 정의 스토리보드에 정의되어있지 않아 삭선처리
//        ui.etSearch.setOnEditorActionListener(editorActionListener);
    }

    @Override
    public void onClickCommon(View v) {
        NotiInfoVO item = null;
        switch (v.getId()) {
            case R.id.btn_detail:
                try {
                    item = (NotiInfoVO) v.getTag(R.id.noti_info);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (item != null && !TextUtils.isEmpty(item.getDtlLnkUri()) && !TextUtils.isEmpty(item.getDtlLnkCd())) {
                        moveToPage(item.getDtlLnkUri(), item.getDtlLnkCd(), false);
                    }
                }
                break;
            case R.id.l_title:
                int pos = 0;

                try {
                    item = (NotiInfoVO) v.getTag(R.id.noti_info);
                    pos = Integer.parseInt(v.getTag(R.id.position).toString());

                    //아이템을 선택했는데 읽음상태가 "읽지 않음인 경우"
                    if (item != null && item.getReadYn().equalsIgnoreCase(VariableType.COMMON_MEANS_NO)) {
                        //읽음상태 변경 요청
                        cmnViewModel.reqNOT0002(new NOT_0002.Request(APPIAInfo.ALRM01.getId(), item.getNotiNo()));
                        //일시적으로 해당페이지에서 읽음 상태로 처리하기 위해서 아래와 같이 데이터 변경
                        //읽음 요청 처리 응답이 성공이 아닐 경우 (서버에서 처리 못한 경우) 해당 페이지에 재 진입 시 새글알림 마크가 다시 보일 수 있음
                        ((NotiInfoVO) adapter.getItem(pos)).setReadYn(VariableType.COMMON_MEANS_YES);
                        cmnViewModel.updateNotiInfoReadYN(item);
                    }
                } catch (Exception e) {

                } finally {
                    if (item != null) {
                        switch (AlarmCenterRecyclerAdapter.getAccordionType(item)) {
                            case AlarmCenterRecyclerAdapter.ALARM_TYPE_NORMAL_NATIVE:
                            case AlarmCenterRecyclerAdapter.ALARM_TYPE_NORMAL_WEBVIEW:
                                adapter.notifyItemChanged(pos);
                                moveToPage(item.getMsgLnkUri(), item.getMsgLnkCd(), false);
                                break;
                            case AlarmCenterRecyclerAdapter.ALARM_TYPE_ACCORDION:
                            default:
                                adapter.eventAccordion(pos);
                                break;
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        cmnViewModel = new ViewModelProvider(this).get(CMNViewModel.class);
    }

    @Override
    public void setObserver() {
    }

    @Override
    public void getDataFromIntent() {

    }

    private void setListView(List<NotiInfoVO> list) {
        if (list == null || list.size() < 1) {
            ui.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            ui.lSearchParent.tvEmpty.setVisibility(View.GONE);
        }
        adapter.setRows(list);
        adapter.notifyDataSetChanged();
    }


    private void reqListData(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            ui.lSearchParent.etSearch.setBackgroundResource(R.drawable.bg_ffffff_stroke_e5e5e5);
            ui.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            ui.lSearchParent.etSearch.setBackgroundResource(R.drawable.bg_ffffff_stroke_000000);
            try {
                setListView(cmnViewModel.getNotiInfoFromDB("", "%" + keyword + "%"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

//    /**
//     * @brief 키보드에서 search 버튼 클릭할 경우 정의
//     * (스토리보드에 정의되어 있지 않아 제거)
//     *
//     */
//    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
//        if(actionId== EditorInfo.IME_ACTION_SEARCH){
//            String search = textView.getText().toString();
//            if (!TextUtils.isEmpty(search)) {
//                MenuVO menuVO = new MenuVO();
//                menuVO.setName(search);
//                menuViewModel.reqRecentlyMenuList(MenuRepository.ACTION_ADD_MENU, menuVO); //키보드 검색 버튼 누를 경우 최근 검색어 저장
//            }
//        }
//        return false;
//    };

}
